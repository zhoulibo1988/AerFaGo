package com.pay.business.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
/**
 * 
 * @author zlb
 *
 */
public class ImgUtil {
	/**
	 * 获取图片尺寸大小
	 * @param file
	 * @return Map（imgWidth 宽,imgHeight 高）
	 */
	public static Map<String, Object> getImgSize(File file) {
		Map<String, Object> returnMap=new HashMap<String, Object>();
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedImage bufferedImg = ImageIO.read(fis);
			int imgWidth = bufferedImg.getWidth();
			int imgHeight = bufferedImg.getHeight();
			returnMap.put("imgWidth", imgWidth);
			returnMap.put("imgHeight", imgHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
	public static void main(String[] args) {
		File file=new File("C://Users//Administrator//Pictures//1//160725065623201.png");
		Map<String, Object> map=getImgSize(file);
		System.out.println(map);
	}
}
