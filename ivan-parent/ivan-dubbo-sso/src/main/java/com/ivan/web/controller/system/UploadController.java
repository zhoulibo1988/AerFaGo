package com.ivan.web.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.ivan.entity.utils.FileConstants;
import org.ivan.entity.utils.FtpUploadClient;
import org.ivan.entity.utils.Icon;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.ivan.entity.utils.ReadPro;
import org.ivan.entity.utils.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/api/upload2")
@Api(value = "上传", description = "上传")
public class UploadController {

	private static final String FTP_ADDRESS = ReadPro.getValue("ftp.upload.address");
	private static final String FTP_PORT = ReadPro.getValue("ftp.upload.port");
	private static final String FTP_NAME = ReadPro.getValue("ftp.upload.name");
	private static final String FTP_PWD = ReadPro.getValue("ftp.upload.pwd");
	private static final String FTP_PATH = ReadPro.getValue("ftp.upload.path");
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
	@ResponseBody
	@ApiOperation(value = "添加单文件带Map", notes = "添加单文件带Map", httpMethod = "POST")
	@RequestMapping(value = "/addFileWithMap", method = RequestMethod.POST)
	public Map<String, Object> addFileWithMap(
			@RequestParam(value = "fileinfo", required = false) MultipartFile fileinfo,
			@RequestParam Map<String, Object> map, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		if (fileinfo != null) {
			try {
				synchronized (fileinfo) {
					String path = request.getSession().getServletContext().getRealPath("/");
					String fileName = System.currentTimeMillis() + ".png";
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					// 保存
					fileinfo.transferTo(targetFile);
					String original = fileinfo.getOriginalFilename();
					if (StringUtils.isEmpty(original) || "nofilename".equals(original) || original.indexOf(".") == -1) {
						original = "AAA.png";
					}
					int index = original.lastIndexOf(".");
					String indexStr = original.substring(index);
					String url = FtpUploadClient.getInstance().uploadFile(UUID.randomUUID().toString() + indexStr,
							targetFile);
					resultMap.put("url", url);
					resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
					if (null != targetFile && targetFile.exists()) {
						targetFile.delete();
					}
				}
			} catch (Exception e) {
				LOGGER.error("上传失败 …… ", e);
				resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "上传失败");
			}
		} else {
			resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "文件不存在");
		}
		return resultMap;
	}

	/**
	 * 单文件上传
	 * 
	 * @param file
	 * @throws Exception
	 */
	private String common(MultipartFile file, HttpServletRequest request) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");
		LOGGER.info(" 正在上传文件中.... ");
		String fileName = file.getOriginalFilename();
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		file.transferTo(targetFile);
		String original = file.getOriginalFilename();
		int index = original.lastIndexOf(".");
		String indexStr = original.substring(index);
		String url = FtpUploadClient.getInstance().uploadFile(UUID.randomUUID().toString() + indexStr, targetFile);
		if (!StringUtils.isEmpty(url)) {
			LOGGER.info("上传成功  文件路径为 ：" + url);
		} else {
			LOGGER.info("上传失败 ！ 文件路径为空！ ");
		}
		return url;
	}

	@ResponseBody
	@ApiOperation(value = "添加单文件", notes = "添加单文件", httpMethod = "POST")
	@RequestMapping(value = "/addFile", method = RequestMethod.POST)
	public Map<String, Object> addFile(@RequestParam(value = "file", required = false) MultipartFile fileinfo,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (fileinfo != null) {
			try {
				String name = fileinfo.getOriginalFilename();
				String ext = name.substring(name.indexOf("."));
				UUIDGenerator uuidGenerator = new UUIDGenerator();
				String fileName = uuidGenerator.generate().toString() + ext;
				String fileDir = FileConstants.getInstance().getFileDir(FileConstants.NFS_SHARE_TYPE_20001);
				String fileTo = FileConstants.getNfsShareRoot() + fileDir;

				// 执行上传到NFS目录操作
				File sdkFile = getFile(fileinfo.getInputStream(), fileTo, fileName);

				Long fileSize = fileinfo.getSize();
				// 执行上传操作
				Map<String, Object> fileMap = new HashMap<String, Object>();
				// 执行上传到Ftp操作
				FtpUploadClient ftp = new FtpUploadClient();
				String ftpDir = File.separator + fileDir;
				ftpDir = ftpDir.replace("\\", "/");
				Boolean flag = ftp.upGetReady(ftpDir, sdkFile.getPath());

				if (flag) {
					String url = File.separator + fileDir + fileName;
					url = url.replace("\\", "/");
					fileMap.put("url", ReadPro.getValue("ftp.visit.path") + ReadPro.getValue("ftp.upload.path") + url);
					fileMap.put("fileSize", fileSize);
					fileMap.put("fileName", name);
					resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
				} else {
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "图片已存在");
				}
				if (null != sdkFile && sdkFile.exists()) {
					sdkFile.delete();
				}
			} catch (Exception e) {
				LOGGER.error("上传失败 …… ", e);
				resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "上传失败 …… ");
			}

		} else {
			resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "文件不存在");
		}
		return resultMap;
	}

	/**
	 * 根据byte数组，生成文件
	 */
	private static File getFile(InputStream in, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			if (!filePath.endsWith(File.separator)) {
				filePath = filePath + File.separator;
			}
			File dir = new File(filePath);
			if (!dir.exists()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = in.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	@ResponseBody
	@ApiOperation(value = "uploadMultipleImg", notes = "uploadMultipleImg", httpMethod = "POST")
	@RequestMapping(value = "/uploadMultipleImg", method = RequestMethod.POST)
	public void uploadMultipleImg(@RequestParam(value = "multipleImg", required = false) MultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response /* , ModelMap model */) throws Exception {
		common(files, response, request);
	}

	@ResponseBody
	@ApiOperation(value = "uploadImgs", notes = "uploadImgs", httpMethod = "POST")
	@RequestMapping(value = "/uploadImgs", method = RequestMethod.POST)
	public void uploadImgs(@RequestParam(value = "Imgs", required = false) MultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response /* , ModelMap model */) throws Exception {
		common(files, response, request);
	}

	/**
	 * 多文件上传
	 * 
	 * @param files    文件
	 * @param response 内置对象
	 * @param request  内置对象
	 * @throws Exception 异常
	 */
	private void common(MultipartFile[] files, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = null;
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder();
			if (null != files && files.length > 0) {
				for (MultipartFile file : files) {
					String path = request.getSession().getServletContext().getRealPath("/");
					String fileName = file.getOriginalFilename();
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					// 保存
					file.transferTo(targetFile);
					String original = file.getOriginalFilename();
					int index = original.lastIndexOf(".");
					String indexStr = original.substring(index);
					String url = FtpUploadClient.getInstance().uploadFile(UUID.randomUUID().toString() + indexStr,
							targetFile);
					sb.append(url + ",");
					// 删除临时生成的文件
					if (null != targetFile && targetFile.exists()) {
						targetFile.delete();
					}
				}
			}

			String url = sb.toString();
			if (StringUtils.isNoneBlank(url) && url.lastIndexOf(",") > 0) {
				// 去掉最后一个逗号
				resultMap.put("url", url.substring(0, url.length() - 1));
			} else {
				resultMap.put("url", url);
			}
			resultMap.put("status", "success");
			String jsonText = JSON.toJSONString(resultMap, true);
			pw = response.getWriter();
			pw.write(jsonText);
			pw.flush();
		} finally {
			if (null != pw) {
				pw.close();
			}
		}

	}

	/**
	 * 根据byte数组，生成文件
	 */
	private static File getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 单文件上传(上传完成后不需要删除临时文件)
	 * 
	 * @throws Exception
	 */
	private String common2(File f) throws Exception {
		FtpUploadClient ftp = new FtpUploadClient();

		String ftpDir = System.currentTimeMillis() + File.separator;
		String url = ReadPro.getValue("ftp.visit.path") + ReadPro.getValue("ftp.upload.path") + ftpDir + f.getName();
		return url;
	}

	/**
	 * 上传APK与IPA
	 * 
	 * @param fileinfo 文件
	 * @param request  内置对象
	 * @return BaseResponse<Object>
	 * @throws Exception 异常
	 */
	@ResponseBody
    @ApiOperation(value = "上传APK与IPA", notes = "上传APK与IPA", httpMethod = "POST")
    @RequestMapping(value = "/uploadApkIpa", method = RequestMethod.POST)
	public Map<String, Object> uploadApkIpa(@RequestParam(value = "file", required = false) MultipartFile fileinfo,HttpServletRequest request) throws Exception {
		Map<String, Object> response = new HashMap<>();
		if (fileinfo != null) {
			try {
				String fileName = fileinfo.getOriginalFilename();
				Map<String, Object> obj = new HashMap<>();
				obj.put("fileName", fileName);
				Long fileSize = fileinfo.getSize();
				byte[] arrays = fileinfo.getBytes();
				int index = fileName.lastIndexOf(".");
				String indexStr = fileName.substring(index);
				// 后缀
				obj.put("ext", indexStr.substring(1, indexStr.length()));
				String appKey = UUID.randomUUID().toString().replaceAll("-", "");
				obj.put("appKey", appKey);
				File f = getFile(arrays, request.getSession().getServletContext().getRealPath("/"), fileName);

				InputStream inputStream = fileinfo.getInputStream();
				byte[] bytes = IOUtils.toByteArray(inputStream);
				// 校验md5
				String md5 = DigestUtils.md5Hex(bytes);
				// 执行上传操作
				// 本地环境下 固定返回值
				// String url = "apk 或者 ipa 的 URL，部署公网修正生效";
				// 公网情况下 逻辑如下
				String url = getUrlByFile(fileinfo, request).get("fileUrl");
				LOGGER.info("url = " + url);
				if (StringUtils.isNoneBlank(url)) {
					obj.put("url", url);// 文件地址
					obj.put("md5", md5);
					obj.put("fileSize", String.valueOf(fileSize.intValue()));// 文件大小

					// 进行ipa和apk文件解析，提取app信息(获取包名和版本信息等)
					Map<String, String> appInfoMap = Icon.getParams(f.getAbsolutePath());
					// 检查APP及版本
					if (appInfoMap != null && appInfoMap.size() > 0 && appInfoMap.get("appType") != null
							&& !String.valueOf(appInfoMap.get("appType")).equals("")) {
						obj.put("package", appInfoMap.get("package")); // 包名
						obj.put("versionName", appInfoMap.get("versionName")); // 主版本
						obj.put("versionCode", appInfoMap.get("versionCode")); // 子版本
						obj.put("appName", appInfoMap.get("appName"));
						String appIconString = appInfoMap.get("appIcon");
						LOGGER.info("appIconString = " + appIconString);
						String appIconUrl = "";
						if (StringUtils.isNotEmpty(appIconString)) {
							int iconIndex = appIconString.lastIndexOf(".");
							String iconExt = appIconString.substring(iconIndex);
							File appIconFile = new File(appIconString);
							String returnAppIconUrl = FtpUploadClient.getInstance().uploadFile(appKey + iconExt,
									appIconFile);
							if (StringUtils.isNotEmpty(returnAppIconUrl)) {
								appIconUrl = returnAppIconUrl;
							}
						}
						obj.put("appIcon", appIconUrl);
					}
					if (f.exists()) {
						f.delete();
					}
					Map<String, Object> fileInfo = new HashMap<>();
					fileInfo.put("fileInfo", obj);
					response = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, fileInfo);
				} else {
					response = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "服务器异常");
				}
			} catch (Exception e) {
				LOGGER.error("上传失败 …… ", e);
				response = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "上传失败");
			}

		} else {
			response = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "文件丢了");
		}
		return response;
	}

	/**
	 * 根据文件获得 FTP 的 URL 和 MD5
	 *
	 * @param file    file
	 * @param request request
	 * @return URL
	 * @throws Exception
	 */
	public Map<String, String> getUrlByFile(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) throws Exception {
		FtpUploadClient ftp = new FtpUploadClient(FTP_ADDRESS, Integer.valueOf(FTP_PORT), FTP_NAME, FTP_PWD, FTP_PATH);
		String path = request.getSession().getServletContext().getRealPath("/");
		LOGGER.info(" 正在上传文件中.... ");
		String fileName = file.getOriginalFilename();
		File targetFile = new File(path);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		targetFile = new File(path, fileName);
		// 保存
		byte[] bytes = file.getBytes();
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(targetFile));
		stream.write(bytes);
		stream.close();
		// 主动模式：如果是被动模式 直接注释掉下面这段代码
		// file.transferTo(targetFile);
		String original = file.getOriginalFilename();
		int index = original.lastIndexOf(".");
		String indexStr = original.substring(index);
		// 本地环境下 固定返回值
		// return "http://success!!!!!!!!!";
		// 公网情况下 逻辑如下
		String url = ftp.uploadFile(UUID.randomUUID().toString() + indexStr, targetFile);
		if (!StringUtils.isEmpty(url)) {
			LOGGER.info("上传成功  文件路径为 ：" + url);
		} else {
			LOGGER.info("上传失败 ！ 文件路径为空！ ");
		}

		Map<String, String> map = new HashMap<>();
		String fileMD5 = getFileMD5(path + fileName);
		map.put("fileMd5", fileMD5);
		map.put("fileUrl", url);
		return map;
	}

	/**
	 * 和安卓统一 ： 获取文件的MD5值
	 *
	 * @param filePath filePath
	 * @return md5
	 */
	private static String getFileMD5(String filePath) {
		String md5 = "";

		if (filePath != null && filePath.length() != 0) {
			File file = new File(filePath);
			if (!file.exists()) {
				return md5;
			}

			byte[] bytes = new byte[10240];
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis);

				int len;
				while ((len = bis.read(bytes, 0, 10240)) != -1) {
					md.update(bytes, 0, len);
				}

				bis.close();
				fis.close();

				byte[] digest = md.digest();
				StringBuilder sb = new StringBuilder();

				int index = 0;
				while (index < digest.length) {
					sb.append(Integer.toString((digest[index] & 255) + 256, 16).substring(1));
					++index;
				}

				md5 = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return md5;
	}
	@ResponseBody
    @ApiOperation(value = "上传电影", notes = "uploadFilm", httpMethod = "POST")
    @RequestMapping(value = "/uploadFilm", method = RequestMethod.POST)
	public Map<String, Object> uploadFilm(@RequestParam(value = "file", required = false) MultipartFile fileinfo,HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<>();
		if (fileinfo != null) {
			try {
				String fileName = fileinfo.getOriginalFilename();
				obj.put("fileName", fileName);
				Long fileSize = fileinfo.getSize();
				byte[] arrays = fileinfo.getBytes();
				int index = fileName.lastIndexOf(".");
				String indexStr = fileName.substring(index);
				// 后缀
				obj.put("ext", indexStr.substring(1, indexStr.length()));
				String appKey = UUID.randomUUID().toString().replaceAll("-", "");
				obj.put("appKey", appKey);
				File f = getFile(arrays, request.getSession().getServletContext().getRealPath("/"), fileName);

				InputStream inputStream = fileinfo.getInputStream();
				byte[] bytes = IOUtils.toByteArray(inputStream);
				// 校验md5
				String md5 = DigestUtils.md5Hex(bytes);
				// 执行上传操作
				// 本地环境下 固定返回值
				// String url = "apk 或者 ipa 的 URL，部署公网修正生效";
				// 公网情况下 逻辑如下
				String url = getUrlByFile(fileinfo, request).get("fileUrl");
				LOGGER.info("url = " + url);
				if (StringUtils.isNoneBlank(url)) {
					obj.put("url", url);// 文件地址
					obj.put("md5", md5);
					obj.put("fileSize", String.valueOf(fileSize.intValue()));// 文件大小
					if (f.exists()) {
						f.delete();
					}
					obj = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, obj);
				} else {
					obj = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "服务器异常");
				}
			} catch (Exception e) {
				LOGGER.error("上传失败 …… ", e);
				obj = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "上传失败");
			}

		} else {
			obj = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "文件丢了");
		}
		return obj;
	}
}
