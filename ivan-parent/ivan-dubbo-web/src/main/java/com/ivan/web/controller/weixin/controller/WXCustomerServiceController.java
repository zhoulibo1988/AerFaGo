package com.ivan.web.controller.weixin.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.weixin.WeixinAuthorizationInfoService;
import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.ivan.entity.weixin.dto.WeChatContants;
import org.ivan.entity.wx.WeixinAuthorizationInfo;
import org.ivan.entity.wx.WeixinAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import weixin.popular.api.CustomserviceAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.customservice.KFAccount;
import weixin.popular.bean.customservice.KFCustomSession;
import weixin.popular.bean.customservice.KFMsgRecord;
import weixin.popular.bean.customservice.KFOnline;
import weixin.popular.bean.customservice.KFSession;
import weixin.popular.bean.customservice.KFWaitcase;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 新版客服功能管理
 * 
 * @author 周立波
 *
 */
@Controller
@RequestMapping("/CustomerService")
public class WXCustomerServiceController {
	private static final Logger logger = LoggerFactory.getLogger(WXCustomerServiceController.class);
	@Reference
	private WeixinAuthorizationTokenService weixinAuthorizationTokenService;
	@Reference
	private WeixinAuthorizationInfoService weixinAuthorizationInfoService;
	 /**
     * 获取授权方列表
     * @param response
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value="/getInfoList",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView getLicensorList(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	ModelAndView mv=new ModelAndView("weixin/service-list");
    	if(map.containsKey("type")&&Integer.valueOf(map.get("type").toString())==1){//菜单管理
    		mv=new ModelAndView("weixin/customMenu-service-list");
    	}
    	if(map.containsKey("type")&&Integer.valueOf(map.get("type").toString())==2){//用户管理
    		mv=new ModelAndView("weixin/user-service-list");
    	}
    	PageObject<WeixinAuthorizationInfo>  pageObject=weixinAuthorizationInfoService.Pagequery(map);
    	mv.addObject("list", pageObject);
		return mv;
    }
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
		returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, returnMap);
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
		returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, returnMap);
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
	@RequestMapping("/getkflistByAll")
	public ModelAndView getkflistByAll(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		ModelAndView mv=new ModelAndView("weixin/service-info-list");
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		KFAccount kFAccount = CustomserviceAPI.getkflist(weixinAuthorizationToken.getAuthorizerAccessToken());
		mv.addObject("kFAccount", kFAccount);
		mv.addObject("map", map);
		return mv;
	}

	/**
	 * 获取在线客服
	 * 
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/getOnlinekflist")
	public ModelAndView getOnlinekflist(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		ModelAndView mv=new ModelAndView("weixin/service-On-line-list");
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		KFOnline kFOnline = CustomserviceAPI.getOnlinekflist(weixinAuthorizationToken.getAuthorizerAccessToken());
		mv.addObject("kFOnline", kFOnline);
		mv.addObject("map", map);
		return mv;
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
		returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, returnMap);
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
		String filePath= String.valueOf(map.get("filePath"));
		File file = new File(filePath);
		// 获取授权方的access_token
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		BaseResult baseResult = CustomserviceAPI.kfaccountUploadHeadimg(weixinAuthorizationToken.getAuthorizerAccessToken(), kf_account, file);
		returnMap.put("baseResult", baseResult);
		returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, returnMap);
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
	// 会话管理
	/**
	 * 创建会话
	 * 注意：此接口在客服和用户之间创建一个会话，如果该客服和用户会话已存在，则直接返回0。指定的客服帐号必须已经绑定微信号且在线
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createSession")
	public  Map<String,Object>  createSession(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		//此处的opneid需要获取用户授权登录
		String openid=String.valueOf(map.get("openid"));
		String kf_account=String.valueOf(map.get("kf_account"));
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		BaseResult baseResult 	=CustomserviceAPI.kfsessionCreate(weixinAuthorizationToken.getAuthorizerAccessToken(), openid, kf_account, null);
		returnMap.put("baseResult", baseResult);
		return returnMap;
	}
	/**
	 * 关闭会话
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/closeSession")
	public Map<String,Object> closeSession(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		//此处的opneid需要获取用户授权登录
		String openid=String.valueOf(map.get("openid"));
		String kf_account=String.valueOf(map.get("kf_account"));
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		BaseResult baseResult 	=CustomserviceAPI.kfsessionClose(weixinAuthorizationToken.getAuthorizerAccessToken(), kf_account, openid, null);
		returnMap.put("baseResult", baseResult);
		return returnMap;
	}
	/**
	 * 获取客户会话状态
	 * 此接口获取一个客户的会话，如果不存在，则kf_account为空。
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSession")
	public Map<String, Object> getSession(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		// 此处的opneid需要获取用户授权登录
		String openid = String.valueOf(map.get("openid"));
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		KFCustomSession kFCustomSession=CustomserviceAPI.kfsessionGetsession(weixinAuthorizationToken.getAuthorizerAccessToken(), openid);
		returnMap.put("kFCustomSession", kFCustomSession);
		return returnMap;
	}
	/**
	 * 获取客服会话列表
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSessionList")
	public Map<String, Object> getSessionList(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		String kf_account = String.valueOf(map.get("kf_account"));
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		KFSession kFSession =	CustomserviceAPI.kfsessionGetsessionlist(weixinAuthorizationToken.getAuthorizerAccessToken(), kf_account);
		returnMap.put("kFSession", kFSession);
		return returnMap;
	}
	/**
	 * 获取未接入会话列表
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getwaitcase")
	public Map<String, Object> getwaitcase(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String authorizer_appid = String.valueOf(map.get("authorizer_appid"));
		WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
		weixinAuthorizationToken.setAppId(WeChatContants.appId);
		weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
		weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
		KFWaitcase kFWaitcase =CustomserviceAPI.kfsessionGetwaitcase(weixinAuthorizationToken.getAuthorizerAccessToken());
		returnMap.put("kFWaitcase", kFWaitcase);
		return returnMap;
	}
	/**
	 * 微信客服系统登录
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/serviceLogin")
	public ModelAndView serviceLogin(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		return new ModelAndView("redirect:https://mpkf.weixin.qq.com");
	}
	/**
	 * 跳转添加客服页面
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/addServiceTo",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView addServiceTo(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map){
		ModelAndView mv=new ModelAndView("weixin/add-service");
		mv.addObject("map",map);
		return mv;
	}
	
	/**
	 * 跳转绑定微信页面
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/addWeiXinTo",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView addWeiXinTo(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map){
		ModelAndView mv=new ModelAndView("weixin/add-weixin");
		mv.addObject("map",map);
		return mv;
	}
	/**
	 * 跳转绑定微信页面
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/updateNikeNameTo",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView updateNikeNameTo(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map){
		ModelAndView mv=new ModelAndView("weixin/update-service-nickname");
		mv.addObject("map",map);
		return mv;
	}
	/**
	 * 跳转绑定微信页面
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/uploadNameImgTo",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView uploadNameImgTo(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map){
		ModelAndView mv=new ModelAndView("weixin/upload-service-img");
		mv.addObject("map",map);
		return mv;
	}
}
