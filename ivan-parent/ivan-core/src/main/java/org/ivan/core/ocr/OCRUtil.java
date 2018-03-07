package org.ivan.core.ocr;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.util.OS;
public class OCRUtil {
	private final String LANG_OPTION = "-l"; // 英文字母小写l，并非数字1
	private final String EOL = System.getProperty("line.separator");
	private String tessPath = "C://Program Files (x86)//Tesseract-OCR";// ocr默认安装路径
	private String transname = "chi_sim";// 默认中文语言包，识别中文

	/**
	 * 从图片中识别文字
	 * @param imageFile
	 * @param imageFormat
	 * @return text recognized in image
	 * @throws Exception
	 */
	public String recognizeText(File imageFile) throws Exception {
		File tempImage = new ImageIOHelper().createImage(imageFile);
		return ocrImages(tempImage, imageFile);
	}



	/**
	 * 识别图片中的文字
	 * @param tempImage
	 * @param imageFile
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private String ocrImages(File tempImage, File imageFile) throws IOException, InterruptedException {
		File outputFile = new File(imageFile.getParentFile(), "output");
		Runtime.getRuntime().exec("attrib " + "\"" + outputFile.getAbsolutePath() + "\"" + " +H"); // 设置文件隐藏
		StringBuffer strB = new StringBuffer();
		List<String> cmd = new ArrayList<String>();
		if (OS.isWindowsXP()) {
			cmd.add(tessPath + "//tesseract");
		} else if (OS.isLinux()) {
			cmd.add("tesseract");
		} else {
			cmd.add(tessPath + "//tesseract");
		}
		cmd.add("");
		cmd.add(outputFile.getName());
		cmd.add(LANG_OPTION);
		cmd.add(transname);
		
		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(imageFile.getParentFile());
		cmd.set(1, tempImage.getName());
		pb.command(cmd);
		pb.redirectErrorStream(true);
		Process process = pb.start();
		int w = process.waitFor();
		
		tempImage.delete();// 删除临时正在工作文件
		if (w == 0) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath() + ".txt"), "UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				strB.append(str).append(EOL);
			}
			in.close();
		} else {
			String msg;
			switch (w) {
			case 1:
				msg = "Errors accessing files.There may be spaces in your image's filename.";
				break;
			case 29:
				msg = "Cannot recongnize the image or its selected region.";
				break;
			case 31:
				msg = "Unsupported image format.";
				break;
			default:
				msg = "Errors occurred.";
			}
			tempImage.delete();
			throw new RuntimeException(msg);
		}
		new File(outputFile.getAbsolutePath() + ".txt").delete();
		return strB.toString();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("begin");
		String path = "F:/orc/1512203246864.png";
		String valCode = new OCRUtil().recognizeText(new File(path));
		System.out.println(valCode);
		System.out.println("end");
	}
}
