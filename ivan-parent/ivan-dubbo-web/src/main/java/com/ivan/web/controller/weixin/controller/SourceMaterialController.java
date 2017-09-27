package com.ivan.web.controller.weixin.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.entity.utils.ImageUtil;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.ivan.entity.weixin.dto.WeChatContants;
import org.ivan.entity.wx.WeixinAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import weixin.popular.api.MediaAPI;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.MediaGetResult;
import weixin.popular.bean.media.MediaType;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 素材管理
 * @author 周立波
 *
 */
@Controller
@RequestMapping("/SourceMaterial")
public class SourceMaterialController{
	private static final Logger logger = LoggerFactory.getLogger(SourceMaterialController.class);
	@Reference
	private WeixinAuthorizationTokenService weixinAuthorizationTokenService;
	/**
	 * 添加临时素材
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addMedia")
	public Map<String, Object> addMedia(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map,@RequestParam("file") MultipartFile file1) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		//素材类型：
		int type=Integer.valueOf(map.get("type").toString());
		MediaType mediaType=null;
		if(type==1){
			mediaType=MediaType.image;
		}
		if(type==2){
			mediaType=MediaType.voice_mp3;
		}
		if(type==3){
			mediaType=MediaType.voice_arm;
		}
		if(type==4){
			mediaType=MediaType.video;
		}
		if(type==5){
			mediaType=MediaType.thumb;
		}
		File file=new File("D://1.jpg");
		Media  media =MediaAPI.mediaUpload(weixinAuthorizationToken.getAuthorizerAccessToken(),mediaType, file);
		System.out.println(media.getErrcode());
		System.out.println(media.getErrmsg());
		System.out.println(media.getUrl());
		System.out.println(media.getMedia_id());
		//存入数据库
		returnMap.put("media", media);
		returnMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, returnMap);
		return returnMap;
	}
	/**
	 * 获取临时素材（视频与其他不同）
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMediaById")
	public Map<String,Object> getMediaById(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String media_id=map.get("media_id").toString();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		MediaGetResult mediaGetResult=	MediaAPI.mediaGet(weixinAuthorizationToken.getAuthorizerAccessToken(), media_id);
		try {
			//下载
			ImageUtil.StreamOper(response, mediaGetResult.getFilename(), mediaGetResult.getBytes(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		returnMap.put("mediaGetResult", mediaGetResult);
		returnMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, returnMap);
		return returnMap;
	}
}
