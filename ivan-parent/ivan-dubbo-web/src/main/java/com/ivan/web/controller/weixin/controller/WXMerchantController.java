package com.ivan.web.controller.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.weixin.WeixinAuthCodeService;
import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.entity.weixin.dto.WeChatContants;
import org.ivan.entity.wx.WeixinAuthCode;
import org.ivan.entity.wx.WeixinAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import weixin.popular.api.MerchantAPI;
import weixin.popular.bean.shop.commodity.BaseResultClass;
import weixin.popular.bean.shop.commodity.Base_attr;
import weixin.popular.bean.shop.commodity.ShopInfo;
import weixin.popular.util.JsonUtil;
/**
 * 微信小店
 * @author 周立波
 *
 */
@Controller
@RequestMapping("/merchant")
public class WXMerchantController {
	
	private static final Logger logger = LoggerFactory.getLogger(WXMerchantController.class);
	
	@Reference
	private WeixinAuthorizationTokenService weixinAuthorizationTokenService;
	@Reference
	private WeixinAuthCodeService weixinAuthCodeService;
	/**
	 * 添加商品
	 * @param response
	 * @param request
	 * @param map
	 */
	@ResponseBody
	@RequestMapping(value="/addShop",method={RequestMethod.GET,RequestMethod.POST})
	public void  addShop(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
		Map<String,Object> returnMap=new HashMap<String, Object>();
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//获取授权方的access_token
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    	ShopInfo shopInfo=new ShopInfo();
    	//基础信息
    	Base_attr base_attr=new Base_attr();
    	base_attr.setBuy_limit("10");
    	base_attr.setCategory("");
		MerchantAPI.addShop(weixinAuthorizationToken.getAuthorizerAccessToken(), shopInfo);
	}
	/**
	 * 获取指定分类的所有子分类
	 * @param response
	 * @param request
	 * @param map
	 */
	@ResponseBody
	@RequestMapping(value="/getClassById",method={RequestMethod.GET,RequestMethod.POST})
	public BaseResultClass getClassById(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
		String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//获取授权方的access_token
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    	BaseResultClass baseResultClass=MerchantAPI.getClassById(weixinAuthorizationToken.getAuthorizerAccessToken(), "1");
    	System.out.println(JsonUtil.toJSONString(baseResultClass));
		return baseResultClass;
	}
}
