package com.ivan.web.controller.weixin.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.entity.WeixinAuthorizationToken;
import org.ivan.entity.weixin.dto.WeChatContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import weixin.popular.api.CustomserviceAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.customservice.KFAccount;
import weixin.popular.bean.customservice.KFMsgRecord;
import weixin.popular.bean.customservice.KFOnline;

/**
 * 新版客服功能管理
 * 
 * @author 周立波
 *
 */
public class WXCustomerServiceController {
	private static final Logger logger = LoggerFactory.getLogger(WXCustomerServiceController.class);
	@Reference
	private WeixinAuthorizationTokenService weixinAuthorizationTokenService;

	// /////////////////////////////////消息管理-客服消息////////////////////////////////////////
	// 微信客服说明文档地址：https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&key=1464266075&version=12&lang=zh_CN
	/**
	 * 添加客服账号
	 * 
	 * @param response
	 * @param request
	 * @param map
	 *            (参数：kf_account，nickname，authorizer_appid)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kfaccountAdd")
	public Map<String, Object> kfaccountAdd(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		String kf_account = String.valueOf(map.get("kf_account"));
		String nickname = String.valueOf(map.get("nickname"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		BaseResult baseResult = CustomserviceAPI.newkfaccountAdd(weixinAuthorizationToken.getAuthorizerAccessToken(), kf_account, nickname);
		returnMap.put("baseResult", baseResult);
		return returnMap;
	}

	/**
	 * 邀请客服人员绑定微信号进行客服服务
	 * 
	 * @param response
	 * @param request
	 * @param map
	 *            （kf_account：完整客服帐号，格式为：帐号前缀@公众号微信号，invite_wx：接收绑定邀请的客服微信号，
	 *            authorizer_appid：授权方appid）
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bindingWeChat")
	public Map<String, Object> bindingWeChat(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		String kf_account = String.valueOf(map.get("kf_account"));
		String invite_wx = String.valueOf(map.get("invite_wx"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		BaseResult baseResult = CustomserviceAPI.bindingWeChat(weixinAuthorizationToken.getAuthorizerAccessToken(), kf_account, invite_wx);
		returnMap.put("baseResult", baseResult);
		return returnMap;
	}

	/**
	 * 获取授权方的所有客服信息
	 * 
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getkflistByAll")
	public Map<String, Object> getkflistByAll(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		KFAccount kFAccount = CustomserviceAPI.getkflist(weixinAuthorizationToken.getAuthorizerAccessToken());
		returnMap.put("kFAccount", kFAccount);
		return returnMap;
	}

	/**
	 * 获取在线客服
	 * 
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOnlinekflist")
	public Map<String, Object> getOnlinekflist(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		KFOnline kFOnline = CustomserviceAPI.getOnlinekflist(weixinAuthorizationToken.getAuthorizerAccessToken());
		returnMap.put("kFOnline", kFOnline);
		return returnMap;
	}

	/**
	 * 设置客服信息
	 * 
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/newkfaccountUpdate")
	public Map<String, Object> newkfaccountUpdate(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		String kf_account = String.valueOf(map.get("kf_account"));
		String nickname = String.valueOf(map.get("nickname"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		BaseResult baseResult = CustomserviceAPI.newkfaccountUpdate(weixinAuthorizationToken.getAuthorizerAccessToken(), kf_account, nickname);
		returnMap.put("baseResult", baseResult);
		return returnMap;
	}

	/**
	 * 上传客服图像
	 * 
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kfaccountUploadHeadimg")
	public Map<String, Object> kfaccountUploadHeadimg(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		String kf_account = String.valueOf(map.get("kf_account"));
		// File file=new File("‪d:/AAAAA.jpg");
		File file = new File("d:/QQ图片20170629173126.jpg");
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		BaseResult baseResult = CustomserviceAPI.kfaccountUploadHeadimg(weixinAuthorizationToken.getAuthorizerAccessToken(), kf_account, file);
		returnMap.put("baseResult", baseResult);
		return returnMap;
	}

	/**
	 * 删除客服人员
	 * 
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kfaccountDel")
	public Map<String, Object> kfaccountDel(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		String kf_account = String.valueOf(map.get("kf_account"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		BaseResult baseResult = CustomserviceAPI.kfaccountDel(weixinAuthorizationToken.getAuthorizerAccessToken(), kf_account);
		returnMap.put("baseResult", baseResult);
		return returnMap;
	}

	/**
	 * 获取聊天记录
	 * 
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/msgrecordGetrecord")
	public Map<String, Object> msgrecordGetrecord(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		int endtime = Integer.valueOf(map.get("endtime").toString());
		int pageindex = Integer.valueOf(map.get("pageindex").toString());
		int pagesize = Integer.valueOf(map.get("pagesize").toString());
		int starttime = Integer.valueOf(map.get("starttime").toString());
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		KFMsgRecord kFMsgRecord = CustomserviceAPI.msgrecordGetrecord(weixinAuthorizationToken.getAuthorizerAccessToken(), endtime, pageindex, pagesize, starttime);
		returnMap.put("kFMsgRecord", kFMsgRecord);
		return returnMap;
	}
	// 会话管理未实现
}
