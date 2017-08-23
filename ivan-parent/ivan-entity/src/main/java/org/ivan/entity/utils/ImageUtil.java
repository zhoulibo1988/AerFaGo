package org.ivan.entity.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class ImageUtil {
	public static void main(String[] args) throws Exception {
		// 先模拟一个图形byte[]
		byte[] b1 = image2Bytes("d:\\1.jpg");
		// 存为文件
		
		buff2Image(b1, "d:\\test.jpg");

		System.out.println("Hello World!");
	}

	public static void buff2Image(byte[] b, String tagSrc) throws Exception {
		FileOutputStream fout = new FileOutputStream(tagSrc);
		// 将字节写入文件
		fout.write(b);
		fout.close();
	}

	public static byte[] image2Bytes(String imgSrc) throws Exception {
		FileInputStream fin = new FileInputStream(new File(imgSrc));
		// 可能溢出,简单起见就不考虑太多,如果太大就要另外想办法，比如一次传入固定长度byte[]
		byte[] bytes = new byte[fin.available()];
		// 将文件内容写入字节数组，提供测试的case
		fin.read(bytes);
		fin.close();
		return bytes;
	}
	private static Map<String,Object> MIME=null;
	static {  
	    MIME = new Hashtable<String,Object>();  
	    MIME.put("jpeg", "image/jpeg");  
	    MIME.put("jpg", "image/jpeg");  
	    MIME.put("jfif", "image/jpeg");  
	    MIME.put("jfif-tbnl", "image/jpeg");  
	    MIME.put("jpe", "image/jpeg");  
	    MIME.put("jfif", "image/jpeg");  
	    MIME.put("tiff", "image/tiff");  
	    MIME.put("tif", "image/tiff");  
	    MIME.put("gif", "image/gif");  
	    MIME.put("xls", "application/x-msexcel");  
	    MIME.put("doc", "application/msword");  
	    MIME.put("ppt", "application/x-mspowerpoint");  
	    MIME.put("zip", "application/x-zip-compressed");  
	    MIME.put("pdf", "application/pdf");  
	}  
	public static void StreamOper(HttpServletResponse response, String fileName, byte bytes[], boolean down)throws IOException {  
		        int index = 0;  
		        String ext = "";  
		        if ((index = fileName.indexOf('.')) > 0)  
		            ext = fileName.substring(index + 1);  
		        //通过文件名的后缀判断文件的格式  
		        String mime = (String) MIME.get(ext);  
		        if (mime == null)  
		            mime = "application/x-msdownload";  
		  
		                       response.setContentType(mime);  
		        //是否需要提供下载  
		        if (down)  
		            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
		        OutputStream outStream = response.getOutputStream();  
		        outStream.write(bytes, 0, bytes.length);  
		        outStream.flush();  
		        outStream.close();  
		    }  
}
