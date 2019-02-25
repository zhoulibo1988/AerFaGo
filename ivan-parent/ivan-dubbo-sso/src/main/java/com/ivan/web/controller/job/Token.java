//package com.ivan.web.controller.job;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import org.ivan.api.weixin.WeixinAuthCodeService;
//import org.ivan.api.weixin.WeixinAuthorizationInfoService;
//import org.ivan.api.weixin.WeixinAuthorizationTokenService;
//import org.ivan.entity.weixin.dto.WeChatContants;
//import org.ivan.entity.wx.WeixinAuthCode;
//import org.ivan.entity.wx.WeixinAuthorizationInfo;
//import org.ivan.entity.wx.WeixinAuthorizationToken;
//
//import weixin.popular.api.ComponentAPI;
//import weixin.popular.bean.component.AuthorizerAccessToken;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//
///**
// * 定时：跑授权方的各个授权密钥
// * @author 周立波
// *
// */
//public class Token {
//	@Reference
//	private WeixinAuthorizationInfoService weixinAuthorizationInfoService;
//	@Reference
//	private WeixinAuthCodeService weixinAuthCodeService;
//	@Reference
//	private WeixinAuthorizationTokenService weixinAuthorizationTokenService;
//	/**
//	 * 刷新定时
//	 */
//	public void refreshKey() {
//		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println("刷新）授权公众号的令牌开始执行时间" + date.format(new Date()));
//		Map<String, Object> map=new HashMap<String, Object>();
//		List<WeixinAuthorizationInfo> listInfo = weixinAuthorizationInfoService.getListByMap(map);
//		for (WeixinAuthorizationInfo weixinAuthorizationInfo : listInfo) {
//			String authorizer_appid = weixinAuthorizationInfo.getAuthorizerAppid();
//			try {
//				if (authorizer_appid != null) {
//					// 获取：component_access_token
//					WeixinAuthCode weixinAuthCode = new WeixinAuthCode();
//					weixinAuthCode.setAppId(WeChatContants.appId);
//					weixinAuthCode = weixinAuthCodeService.selectSingle(weixinAuthCode);
//					// 获取：authorizer_refresh_token
//					WeixinAuthorizationToken weixinAuthorizationToken = new WeixinAuthorizationToken();
//					weixinAuthorizationToken.setAppId(WeChatContants.appId);
//					weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
//					weixinAuthorizationToken = weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
//					if (weixinAuthCode != null && weixinAuthorizationToken != null) {
//						// 获取（刷新）令牌
//						AuthorizerAccessToken authorizerAccessToken = ComponentAPI.api_authorizer_token(weixinAuthCode.getComponentAccessToken(), WeChatContants.appId, authorizer_appid, weixinAuthorizationToken.getAuthorizerRefreshToken());
//						if (authorizerAccessToken.getErrcode() == null) {
//							// 更新数据库存储的令牌
//							weixinAuthorizationToken.setAuthorizerAccessToken(authorizerAccessToken.getAuthorizer_access_token());
//							weixinAuthorizationToken.setAuthorizerRefreshToken(authorizerAccessToken.getAuthorizer_refresh_token());
//							weixinAuthorizationToken.setExpiresIn(authorizerAccessToken.getExpires_in());
//							weixinAuthorizationTokenService.updateByEntity(weixinAuthorizationToken);
//							System.out.println("获取（刷新）令牌成功！");
//						} else {
//							System.out.println("刷新或者获取令牌失败！原因为微信返回错误码：------》" + authorizerAccessToken.getErrcode() + "==>微信返回错误信息----》" + authorizerAccessToken.getErrmsg());
//						}
//					} else {
//						System.out.println("获取：component_access_token，authorizer_refresh_token失败");
//					}
//
//				} else {
//					System.out.println("authorizer_appid：不能为空");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("获取（刷新）授权公众号令牌失败");
//			}
//		}
//	}
//
//	public static void main(String[] args) {
//		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		String seconds = new SimpleDateFormat("HHmmss").format(new Date());
//		for (int i = 0; i < 10000; i++) {
//			System.out.println(date + "00001000" + getTwo() + "00" + seconds + getTwo());
//		}
//
//	}
//
//	public static String getTwo() {
//		Random rad = new Random();
//
//		String result = rad.nextInt(100) + "";
//
//		if (result.length() == 1) {
//			result = "0" + result;
//		}
//		return result;
//	} 
//}
