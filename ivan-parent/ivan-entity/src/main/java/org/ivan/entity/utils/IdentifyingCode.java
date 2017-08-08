package org.ivan.entity.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * 
 * @author 周立波
 *	验证码生成工具类
 */
public class IdentifyingCode {
	/**
	 * 验证码图片的宽度。
	 */
	private int width = 80;

	/**
	 * 验证码图片的高度。
	 */
	private int height = 40;

	/**
	 * 验证码的数量。
	 */
	private Random random = new Random();

	public IdentifyingCode() {
	}

	/**
	 * 生成随机颜色
	 * 
	 * @param fc
	 *            前景色
	 * @param bc
	 *            背景色
	 * @return Color对象，此Color对象是RGB形式的。
	 */
	public Color getRandomColor(int fc, int bc) {
		if (fc > 255)
			fc = 200;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 绘制干扰线
	 * 
	 * @param g
	 *            Graphics2D对象，用来绘制图像
	 * @param nums
	 *            干扰线的条数
	 */
	public void drawRandomLines(Graphics2D g, int nums) {
		g.setColor(this.getRandomColor(160, 200));
		for (int i = 0; i < nums; i++) {
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(12);
			int y2 = random.nextInt(12);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/**
	 * 获取随机字符串， 此函数可以产生由大小写字母，汉字，数字组成的字符串
	 * 
	 * @param length
	 *            随机字符串的长度
	 * @return 随机字符串
	 */
	public String drawRandomString(int length,Graphics g) {
		StringBuffer strbuf = new StringBuffer();
		String temp = "";
		int itmp = 0;
		for (int i = 0; i < length; i++) {
			switch (random.nextInt(5)) {
			case 1: // 生成A～Z的字母
				itmp = random.nextInt(26) + 65;
				temp = String.valueOf((char) itmp);
				break;
			case 2:
				itmp = random.nextInt(26) + 97;
				temp = String.valueOf((char) itmp);
			default:
				itmp = random.nextInt(10) + 48;
				temp = String.valueOf((char) itmp);
				break;
			}
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(temp, 13 * i + 26, 25);
			strbuf.append(temp);
		}
		return strbuf.toString();
	}

	public String drawGraphic(BufferedImage image) {
		// 获取图形上下文
		Graphics g = image.createGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandomColor(200, 250));
		g.fillRect(0, 0, getWidth(), getHeight());
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 22));

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandomColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(getWidth());
			int y = random.nextInt(getHeight());
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(4位数字)
		String sRand = drawRandomString(4, g);

		// 图象生效
		g.dispose();

		return sRand;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
