package com.pay.manger.controller.qunar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.Charsets;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class QunarController {
	public static void main(String[] args) {
		String url = "http://qae.qunar.com/api/router";
		String trg = "flight.national.supply.sl.searchflight";
		String key = "a03f6df6c9f9fd663bbf99c931e2d676";
		String token = "7a41fb953c144dc2ffb5f9894178c8ce";
		Long createTime = new Date().getTime();
//		String sign = "";
		// A B C D E F G H I J K L M N
		// o p q r s t u v w x y z
		String dpt = "SZX";
		String arr = "CSX";
		String date = "2017-04-27";
		String ex_track = "youxuan/tehui";
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("arr", arr);
		jsonMap.put("dpt", dpt);
		jsonMap.put("date", date);
		jsonMap.put("ex_track", ex_track);
		JSONObject json = JSONObject.fromObject(jsonMap);
		System.out.println(json);
		String result = "createTime=" + String.valueOf(createTime) + "key=" + key + "params=" +String.valueOf(json)+ "trg=" + trg + "token=" + token;
		String plainText=MD5Util.MD5(result);
		HashFunction function = Hashing.md5();
	    System.out.println(plainText.equals(function.hashString(result, Charsets.UTF_8).toString()));
		String param = "trg=" + trg + "&key=" + key + "&token=" + token + "&createTime=" + String.valueOf(createTime) + "&sign=" + plainText + "&params="
				+String.valueOf(json);
		String info = sendPost(url, param);
		System.out.println(info);
	}

	/*
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url 发送请求的 URL
	 * 
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流finally{
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
