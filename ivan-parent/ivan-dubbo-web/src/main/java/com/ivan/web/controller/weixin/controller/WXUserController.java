package com.ivan.web.controller.weixin.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.entity.weixin.dto.WeChatContants;
import org.ivan.entity.wx.WeixinAuthorizationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;

import weixin.popular.api.UserAPI;
import weixin.popular.bean.user.FollowResult;
import weixin.popular.bean.user.FollowResult.Data;
import weixin.popular.bean.user.UserInfoList;

/**
 * 用户管理
 * @author 周立波
 *
 */
@Controller
@RequestMapping("/wxUser")
public class WXUserController {
	@Reference
	private WeixinAuthorizationTokenService weixinAuthorizationTokenService;
	/**
	 * 获取公众号关注用户列表
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/getUserInfoList")
	public ModelAndView getUserInfoList(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
		ModelAndView mv=new ModelAndView("weixin/user-info-list");
		String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//获取授权方的access_token
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    	FollowResult followResult=UserAPI.userGet(weixinAuthorizationToken.getAuthorizerAccessToken(), null);
    	if(followResult!=null&&followResult.getTotal()!=null){
    		Data data=followResult.getData();
    		String	openid[]=data.getOpenid();
    		List<String> openids=Arrays.asList(openid);
    		//批量获取用户信息
    		UserInfoList userInfoList=UserAPI.userInfoBatchget(weixinAuthorizationToken.getAuthorizerAccessToken(), "zh-CN", openids, 1);
    		mv.addObject("userInfoList", userInfoList);
    	}else{
    		mv.addObject("userInfoList", null);
    	}
		return mv;
	}
}
