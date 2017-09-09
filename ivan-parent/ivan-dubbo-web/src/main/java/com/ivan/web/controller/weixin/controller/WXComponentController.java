package com.ivan.web.controller.weixin.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.ivan.api.weixin.WeChatThridService;
import org.ivan.api.weixin.WeixinAuthCodeService;
import org.ivan.api.weixin.WeixinAuthorizationInfoService;
import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.api.weixin.WeixinBusinessInfoService;
import org.ivan.entity.WeixinAuthCode;
import org.ivan.entity.WeixinAuthorizationInfo;
import org.ivan.entity.WeixinAuthorizationToken;
import org.ivan.entity.WeixinBusinessInfo;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.ivan.entity.weixin.dto.WeChatContants;
import org.ivan.entity.weixin.utils.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import weixin.popular.api.ComponentAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.component.ApiGetAuthorizerInfoResult;
import weixin.popular.bean.component.ApiGetAuthorizerInfoResult.Authorizer_info.Business_info;
import weixin.popular.bean.component.ApiQueryAuthResult;
import weixin.popular.bean.component.AuthorizerAccessToken;
import weixin.popular.bean.component.AuthorizerOption;
import weixin.popular.bean.component.ComponentAccessToken;
import weixin.popular.bean.component.FuncInfo;
import weixin.popular.bean.component.PreAuthCode;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
/**
 * 微信第三方平台:授权管理
 * @author 周立波
 *
 */
@Controller
@RequestMapping("/WeixinComponent")
public class WXComponentController {
	private static final Logger logger = LoggerFactory.getLogger(WXComponentController.class);
	/*微信公众号号授权给第三方平台*/
	@Reference
	private WeChatThridService weChatThridService;
	@Reference
	private WeixinAuthCodeService weixinAuthCodeService;
	@Reference
	private WeixinAuthorizationTokenService weixinAuthorizationTokenService;
	@Reference
	private WeixinAuthorizationInfoService weixinAuthorizationInfoService;
	@Reference
	private WeixinBusinessInfoService weixinBusinessInfoService;
	
	static String xmlFormat = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
	/**
	 * 微信授权事件的接受
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/authorize", method = { RequestMethod.GET,RequestMethod.POST })
	public void acceptAuthorizeEvent(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		logger.info("-----------》微信来推送XML了《-----------");
		try{
			//处理授权事件
			System.out.println(map);
//	        weChatThridService.handleAuthorize(request,response,map);
	        String timestamp=String.valueOf(map.get("timestamp"));
	        String encrypt_type=String.valueOf(map.get("encrypt_type"));
	        String nonce=String.valueOf(map.get("nonce"));
	        String msg_signature=String.valueOf(map.get("msg_signature"));
	        logger.info("timestamp:-----------》"+timestamp);
	        logger.info("encrypt_type:-----------》"+encrypt_type);
	        logger.info("nonce:-----------》-----------》"+nonce);
	        logger.info("msg_signature:-----------》"+msg_signature);
	        //验证通过后
	        StringBuilder sb = new StringBuilder();
	        BufferedReader in = request.getReader();
	        String line;
	        while ((line = in.readLine()) != null) {
	            sb.append(line);
	        }
	        String xml = sb.toString();
	        logger.info("微信推送的原生：-----------》"+xml);
	        String encodingAesKey =WeChatContants.encodingAesKey;// 第三方平台组件加密密钥
	        String appId=WeChatContants.appId;//从xml中解析
	        WXBizMsgCrypt pc = new WXBizMsgCrypt(WeChatContants.token, encodingAesKey,appId);
	        
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
//			NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

			String encrypt = nodelist1.item(0).getTextContent();
//			String msgSignature = nodelist2.item(0).getTextContent();
	        String fromXML = String.format(xmlFormat, encrypt);
	        xml = pc.decryptMsg(msg_signature, timestamp, nonce, fromXML);
	        logger.info("解密后的："+xml);
	        //获取component_verify_ticket（第三方验证的票据） 
	        //解析上述解密后的xml文件，获取节点component_verify_ticket内容，后续用来调用微信接口获取第三方通行token.
//	        Map<String, String> parseXml = WeChatUtils.parseXml(xml);
	        //修改为下
	        Map<String, String> parseXml=XMLUtil.doXMLParse(xml);
	        String component_verify_ticket=parseXml.get("ComponentVerifyTicket");
	        logger.info("component_verify_ticket为：-----------》"+component_verify_ticket);
	        /************调用接口获取component_access_token 获取自己的接口调用凭据***************/
	        ComponentAccessToken  componentAccessToken = ComponentAPI.api_component_token(WeChatContants.appId, WeChatContants.appSecret, component_verify_ticket);
	        logger.info("获取component_access_token：--------》"+componentAccessToken.getComponent_access_token());
	        
	        /*****************获取预授权码pre_auth_code*************************/
	        PreAuthCode preAuthCode= ComponentAPI.api_create_preauthcode(componentAccessToken.getComponent_access_token(), WeChatContants.appId);
	        logger.info("获取预授权码为：pre_auth_code：-----------》"+preAuthCode.getPre_auth_code());
	        logger.info("获取预授权码为：errcode：-----------》"+preAuthCode.getErrcode());
	        logger.info("获取预授权码为：errmsg：-----------》"+preAuthCode.getErrmsg());
	        logger.info("获取预授权码为：Expires_in：-----------》"+preAuthCode.getExpires_in());
	        //将预授权码存入数据库
	        //并将component_access_token存在数据库中，用来授权时使用。
	        if(preAuthCode.getPre_auth_code()!=null){
	        	WeixinAuthCode weixinAuthCode=new WeixinAuthCode();
	        	weixinAuthCode.setAppId(WeChatContants.appId);
	        	weixinAuthCode=weixinAuthCodeService.selectSingle(weixinAuthCode);
	        	if(weixinAuthCode!=null){
	        		weixinAuthCode.setAuthCode(preAuthCode.getPre_auth_code());
	        		weixinAuthCode.setComponentAccessToken(componentAccessToken.getComponent_access_token());
	        		weixinAuthCode.setTime(new Date());
	        		weixinAuthCodeService.updateByEntity(weixinAuthCode);
	        	}else{
	        		WeixinAuthCode weixinAuthCod1e=new WeixinAuthCode();
	        		weixinAuthCod1e.setAppId(WeChatContants.appId);
	        		weixinAuthCod1e.setAuthCode(preAuthCode.getPre_auth_code());
	        		weixinAuthCod1e.setComponentAccessToken(componentAccessToken.getComponent_access_token());
	        		weixinAuthCod1e.setTime(new Date());
	        		weixinAuthCodeService.insert(weixinAuthCod1e);
	        	}
	        }else{
	        	logger.error("获取预授权码pre_auth_code失败");
	        }
	        
	        PrintWriter pw = response.getWriter();
	        pw.write("success");
	        pw.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
     * 引导用户进入授权页面
     */
	@ResponseBody
    @RequestMapping(value="/goAuthPage",method={RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> goAuthPage( HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
        Map<String,Object> resultMap=new HashMap<String, Object>();
            try {
                request.getSession();
                //查找预授权码
                map.put("appId",WeChatContants.appId);
                WeixinAuthCode weixinAuthCode = weixinAuthCodeService.selectSingle(map);
               if(weixinAuthCode!=null){
            	   if(weixinAuthCode.getAuthCode().equals("")){
                       resultMap.put("errorMsg", "预授权码为空！");
                       resultMap.put("code", 10001);
                       return resultMap;
                   }
                   /**********************跳转到授权页面********************************/
            	   String authorizationUrl= ComponentAPI.componentloginpage(WeChatContants.appId, weixinAuthCode.getAuthCode(), WeChatContants.redirect_uri);
                   logger.info("跳转的URL："+authorizationUrl);
                   resultMap.put("authorizationUrl", authorizationUrl);
                   resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return resultMap;  
    }
    /**
     * 处理微信授权事件完成跳转URL
     * @return
     */
    @RequestMapping(value="/authInfo",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView successAuthorizeInfo(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	ModelAndView mv=new ModelAndView("weixin/licensor-list");
		String result="error";
        try {
            //获取authorization_code ：预授权码 
        	logger.info("微信返回的预授权码：-------------》"+map);
            String authorization_code=request.getParameter("pre_auth_code");
            authorization_code=request.getParameter("auth_code");
            /*if(authorization_code.equals("")||authorization_code==null){
            	authorization_code=String.valueOf(map.get("pre_auth_code"));
            	authorization_code=request.getParameter("auth_code");
            }*/
            //利用预授权码获取：component_access_token 数据库查询
            Map<String,Object> map1=new HashMap<String, Object>();
            map1.put("appId", WeChatContants.appId);
            
            WeixinAuthCode weixinAuthCode=   weixinAuthCodeService.selectSingle(map1);
            logger.info("auth_code:"+authorization_code);
            //换取authorizer_access_token
//            String authAccessToken = weChatThridService.getAuthAccessToken(authorization_code);
            map1.put("authCode",authorization_code);
            String componentAccessToken=weixinAuthCode.getComponentAccessToken();
            logger.info("从数据库获取componentAccessToken：----------》"+componentAccessToken);
            ApiQueryAuthResult apiQueryAuthResult= ComponentAPI.api_query_auth(componentAccessToken,WeChatContants.appId, authorization_code);
            if(apiQueryAuthResult.getErrcode()!=null){
            	logger.error("获取authorizer_access_token---------------》失败");
                request.getSession().setAttribute("errorMsg", "缺少配置，请联系管理员！");
                return mv;
            }else if(apiQueryAuthResult!=null){
            	/*
                 * 注意：获取了authorizer_access_token（令牌）与authorizer_refresh_token（接口调用凭据刷新令牌值）
                 * 注意：authorizer_access_token的有效期为2小时，所以需要存在数据库中。
                 * 上述接口也会返回：authorizer_refresh_token刷新token,当时间超过2个小时后，
                 * 开发者需要通过刷新token来更新authorizer_access_token，以此来确保token的长持久性。so,
                 * authorizer_refresh_token也需要存在数据库中。
                 */
            	//令牌
            	String	authorizer_access_token=apiQueryAuthResult.getAuthorization_info().getAuthorizer_access_token();
            	//授权方appId
            	String authorizer_appid=apiQueryAuthResult.getAuthorization_info().getAuthorizer_appid();
            	//刷新令牌token
            	String authorizer_refresh_token=	apiQueryAuthResult.getAuthorization_info().getAuthorizer_refresh_token();
            	//有效时间
            	int expires_in=apiQueryAuthResult.getAuthorization_info().getExpires_in();
            	logger.info("=======================以下为：授权时获取到的信息===========================");
            	logger.info("=令牌为：---------------》"+authorizer_access_token);
            	logger.info("=授权方appId:----------》"+authorizer_appid);
            	logger.info("=刷新令牌token:---------》"+authorizer_refresh_token);
            	logger.info("=有效时间:--------------》"+expires_in);
            	logger.info("=======================end===========================");
            	//插入数据库存储
            	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
            	weixinAuthorizationToken.setAppId(WeChatContants.appId);
            	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
            	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
            	if(weixinAuthorizationToken!=null){//去做修改
            		weixinAuthorizationToken.setAuthorizerAccessToken(authorizer_access_token);
            		weixinAuthorizationToken.setAuthorizerRefreshToken(authorizer_refresh_token);
            		weixinAuthorizationToken.setExpiresIn(expires_in);
            		weixinAuthorizationToken.setTime(new Date());
            		weixinAuthorizationTokenService.updateByEntity(weixinAuthorizationToken);
            		logger.info("授权信息更新数据成功！");
            	}else{//插入
            		WeixinAuthorizationToken weixinAuthorizationToken1=new WeixinAuthorizationToken();
            		weixinAuthorizationToken1.setAppId(WeChatContants.appId);
            		weixinAuthorizationToken1.setAuthorizerAppid(authorizer_appid);
            		weixinAuthorizationToken1.setAuthorizerAccessToken(authorizer_access_token);
            		weixinAuthorizationToken1.setAuthorizerRefreshToken(authorizer_refresh_token);
            		weixinAuthorizationToken1.setExpiresIn(expires_in);
            		weixinAuthorizationToken1.setTime(new Date());
            		weixinAuthorizationTokenService.insert(weixinAuthorizationToken1);
            		logger.info("授权信息存储数据成功！");
            	}
            	PageObject<WeixinAuthorizationToken>  pageObject=weixinAuthorizationTokenService.Pagequery(map);
            	mv.addObject("list", pageObject);
        		return mv;
            }
            
        }catch (Exception e) {
           logger.info(e.getMessage());
        }
        return mv;
    }
    /**
     * 获取（刷新）授权公众号的令牌:注意：此处token是2小时刷新一次，开发者需要自行进行token的缓存，避免token的获取次数达到每日的限定额度
     * @param response
     * @param request
     * @param map（authorizer_appid：授权方appId）
     * @return
     */
    @RequestMapping(value="/getApiAuthorizerToken",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> getApiAuthorizerToken(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	//获取授权方appId
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	try{
    		if(authorizer_appid!=null){
    			//获取：component_access_token
    			WeixinAuthCode weixinAuthCode=new WeixinAuthCode();
    			weixinAuthCode.setAppId(WeChatContants.appId);
    			weixinAuthCode=weixinAuthCodeService.selectSingle(weixinAuthCode);
    			//获取：authorizer_refresh_token
    			WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    			weixinAuthorizationToken.setAppId(WeChatContants.appId);
    			weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    			weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    			if(weixinAuthCode!=null&&weixinAuthorizationToken!=null){
    				//获取（刷新）令牌
    				AuthorizerAccessToken authorizerAccessToken =ComponentAPI.api_authorizer_token(weixinAuthCode.getComponentAccessToken(), WeChatContants.appId, authorizer_appid, weixinAuthorizationToken.getAuthorizerRefreshToken());
        			if(authorizerAccessToken.getErrcode()==null){
        				//更新数据库存储的令牌
        				weixinAuthorizationToken.setAuthorizerAccessToken(authorizerAccessToken.getAuthorizer_access_token());
        				weixinAuthorizationToken.setAuthorizerRefreshToken(authorizerAccessToken.getAuthorizer_refresh_token());
        				weixinAuthorizationToken.setExpiresIn(authorizerAccessToken.getExpires_in());
        				weixinAuthorizationTokenService.updateByEntity(weixinAuthorizationToken);
        				logger.info("获取（刷新）令牌成功！");
        				returnMap.put("errorMsg", "获取（刷新）令牌成功！");
        				returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
        			}else{
        				logger.error("刷新或者获取令牌失败！原因为微信返回错误码：------》"+authorizerAccessToken.getErrcode()+"==>微信返回错误信息----》"+authorizerAccessToken.getErrmsg());
        				returnMap.put("errorMsg", "刷新或者获取令牌失败！原因为微信返回错误码：------》"+authorizerAccessToken.getErrcode()+"==>微信返回错误信息----》"+authorizerAccessToken.getErrmsg());
        			}
    			}else{
    				logger.error("获取：component_access_token，authorizer_refresh_token失败");
            		returnMap.put("errorMsg", "获取：component_access_token，authorizer_refresh_token失败 ");
    			}
    			
        	}else{
        		logger.error("authorizer_appid：不能为空");
        		returnMap.put("errorMsg", "authorizer_appid：授权方APPID不能为空");
        	}
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("获取（刷新）授权公众号令牌失败");
    		returnMap.put("errorMsg", "获取（刷新）授权公众号令牌失败!");
    	}
		return returnMap;
    	
    }
    /**
     * 获取授权方信息
     * @param response
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value="/getAuthorizerInfo",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> getAuthorizerInfo(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	//查询授权方是否在我平台授权
    	final String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//authorizer_appid=request.getParameter("authorizer_appid");
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    	WeixinAuthCode weixinAuthCode=new WeixinAuthCode();
    	weixinAuthCode.setAppId(WeChatContants.appId);
    	weixinAuthCode=weixinAuthCodeService.selectSingle(weixinAuthCode);
    	if(weixinAuthorizationToken!=null&&weixinAuthCode!=null){
    		final ApiGetAuthorizerInfoResult apiGetAuthorizerInfoResult=ComponentAPI.api_get_authorizer_info(weixinAuthCode.getComponentAccessToken(), WeChatContants.appId, authorizer_appid);
    		if(apiGetAuthorizerInfoResult.getErrcode()==null){
    			new Thread(){
    				public void run() {
						WeixinAuthorizationInfo weixinAuthorizationInfo=new WeixinAuthorizationInfo();
						weixinAuthorizationInfo.setAuthorizerAppid(authorizer_appid);
						weixinAuthorizationInfo=weixinAuthorizationInfoService.selectSingle(weixinAuthorizationInfo);
						if(weixinAuthorizationInfo!=null){
							Integer weixinAuthorizationInfoId=weixinAuthorizationInfo.getId();
							Map<String, Object>  mapInfo=addinfo(weixinAuthorizationInfo, apiGetAuthorizerInfoResult);
							weixinAuthorizationInfo=(WeixinAuthorizationInfo) mapInfo.get("weixinAuthorizationInfo");
							WeixinBusinessInfo weixinBusinessInfo=(WeixinBusinessInfo) mapInfo.get("weixinBusinessInfo");
							weixinAuthorizationInfo.setId(weixinAuthorizationInfoId);
							weixinAuthorizationInfoService.updateByEntity(weixinAuthorizationInfo);
							Map<String,Object> infoMap=new HashMap<String, Object>();
							infoMap.put("authorizerAppid", authorizer_appid);
							WeixinBusinessInfo weixinBusinessInfo1=weixinBusinessInfoService.selectSingle(infoMap);
							if(weixinBusinessInfo1!=null){
								weixinBusinessInfo.setId(weixinBusinessInfo1.getId());
								weixinBusinessInfoService.updateByEntity(weixinBusinessInfo);
							}
							logger.info("更新授权方基础信息成功！");
						}else{
							Map<String, Object>  mapInfo=addinfo(weixinAuthorizationInfo, apiGetAuthorizerInfoResult);
							weixinAuthorizationInfo=(WeixinAuthorizationInfo) mapInfo.get("weixinAuthorizationInfo");
							weixinAuthorizationInfo.setAuthorizerAppid(authorizer_appid);
							WeixinBusinessInfo weixinBusinessInfo=(WeixinBusinessInfo) mapInfo.get("weixinBusinessInfo");
							weixinBusinessInfo.setAuthorizerAppid(authorizer_appid);
							weixinAuthorizationInfoService.insert(weixinAuthorizationInfo);
							weixinBusinessInfoService.insert(weixinBusinessInfo);
							logger.info("添加授权方基础信息成功！");
						}
					}
    			}.start();
//    			returnMap.put("apiGetAuthorizerInfoResult", apiGetAuthorizerInfoResult);
    			returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
    		}else{
    			logger.error("获取授权方信息失败");
    			returnMap.put("errorMsg", "获取授权方信息失败");
    		}
    	}
		return returnMap;
    }
    /**
     * 组装数据
     * @param weixinAuthorizationInfo
     * @param apiGetAuthorizerInfoResult
     * @return
     */
    public Map<String,Object> addinfo(WeixinAuthorizationInfo weixinAuthorizationInfo,ApiGetAuthorizerInfoResult apiGetAuthorizerInfoResult){
    	weixinAuthorizationInfo=new WeixinAuthorizationInfo();
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	//-----------------------------------------------------------------------------------------------
		//授权方公众号所设置的微信号，可能为空
    	if(apiGetAuthorizerInfoResult.getAuthorizer_info().getAlias()!=null&&!apiGetAuthorizerInfoResult.getAuthorizer_info().getAlias().equals("")){
    		weixinAuthorizationInfo.setAlias(apiGetAuthorizerInfoResult.getAuthorizer_info().getAlias());
    	}
		//公众号授权给开发者的权限集:ID为1到15:ID为1到15时分别代表：
		//消息管理权限  用户管理权限 帐号服务权限 网页服务权限 微信小店权限 微信多客服权限 群发与通知权限 微信卡券权限 微信扫一扫权限 
		//微信连WIFI权限 素材管理权限 微信摇周边权限 微信门店权限 微信支付权限 自定义菜单权限
		List<FuncInfo> funcInfo= apiGetAuthorizerInfoResult.getAuthorization_info().getFunc_info();
		StringBuffer buffer = new StringBuffer();
		for (FuncInfo funcInfo2 : funcInfo) {
			buffer.append(String.valueOf(funcInfo2.getFuncscope_category().getId())+"-");
		}
		if(buffer.length()>0){
			buffer=buffer.deleteCharAt(buffer.length()-1);
			String funcInfo1=buffer.toString();
			weixinAuthorizationInfo.setFuncInfo(funcInfo1);
		}
		weixinAuthorizationInfo.setHeadImg(apiGetAuthorizerInfoResult.getAuthorizer_info().getHead_img());
		weixinAuthorizationInfo.setNickName(apiGetAuthorizerInfoResult.getAuthorizer_info().getNick_name());
		weixinAuthorizationInfo.setPrincipalName(apiGetAuthorizerInfoResult.getAuthorizer_info().getPrincipal_name());
		weixinAuthorizationInfo.setQrcodeUrl(apiGetAuthorizerInfoResult.getAuthorizer_info().getQrcode_url());
		weixinAuthorizationInfo.setServiceTypeInfo(apiGetAuthorizerInfoResult.getAuthorizer_info().getService_type_info().getId());
		weixinAuthorizationInfo.setTime(new Date());
		weixinAuthorizationInfo.setUserName(apiGetAuthorizerInfoResult.getAuthorizer_info().getUser_name());
		weixinAuthorizationInfo.setVerifyTypeInfo(apiGetAuthorizerInfoResult.getAuthorizer_info().getVerify_type_info().getId());
		//-----------------------------------------------------------------------------------------------
		//权限
		Business_info Business_info =apiGetAuthorizerInfoResult.getAuthorizer_info().getBusiness_info();
		WeixinBusinessInfo weixinBusinessInfo=new WeixinBusinessInfo();
		weixinBusinessInfo.setOpenCard(Business_info.getOpen_card());
		weixinBusinessInfo.setOpenPay(Business_info.getOpen_pay());
		weixinBusinessInfo.setOpenScan(Business_info.getOpen_scan());
		weixinBusinessInfo.setOpenShake(Business_info.getOpen_shake());
		weixinBusinessInfo.setOpenStore(Business_info.getOpen_store());
		weixinBusinessInfo.setTime(new Date());
		returnMap.put("weixinBusinessInfo", weixinBusinessInfo);
		returnMap.put("weixinAuthorizationInfo", weixinAuthorizationInfo);
		return returnMap;
    }
    /**
     * 获取授权方的选项设置信息
     * @param response
     * @param request
     * @param map(authorizer_appid:授权方ID;option_name:选项名字:地理位置上报，语音识别开关，多客服开关)
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getauthorizerOption",method={RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> getauthorizerOption(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	String option_name=String.valueOf(map.get("option_name"));
    	if(authorizer_appid!=null&&option_name!=null){
    		//获取：component_access_token
			WeixinAuthCode weixinAuthCode=new WeixinAuthCode();
			weixinAuthCode.setAppId(WeChatContants.appId);
			weixinAuthCode=weixinAuthCodeService.selectSingle(weixinAuthCode);
			AuthorizerOption authorizerOption =	ComponentAPI.api_get_authorizer_option(weixinAuthCode.getComponentAccessToken(), WeChatContants.appId, authorizer_appid, option_name);
    		returnMap.put("authorizerOption", authorizerOption);
    	}else{
    		logger.error("参数不能为空！");
    		returnMap.put("errorMsg", "参数不能为空！");
    	}
		return returnMap;
    }
    /**
     * 设置授权方的选项信息
     * @param response
     * @param request
     * @param map(authorizer_appid:授权方APPID;option_name 选项名称<br>
	 * 	 					location_report (地理位置上报选项) 	0	无上报  			1	进入会话时上报  2	每5s上报<br>
	 *  					voice_recognize（语音识别开关选项）	0	关闭语音识别		1	开启语音识别<br>
	 *  					customer_service（多客服开关选项）	0	关闭多客服			1	开启多客服<br>
	 * ;option_value 设置的选项值
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/setAuthorizerOption",method={RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> setAuthorizerOption(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	String option_name=String.valueOf(map.get("option_name"));
    	String option_value=String.valueOf(map.get("option_value"));
    	//获取：component_access_token
		WeixinAuthCode weixinAuthCode=new WeixinAuthCode();
		weixinAuthCode.setAppId(WeChatContants.appId);
		weixinAuthCode=weixinAuthCodeService.selectSingle(weixinAuthCode);
		BaseResult BaseResult=	ComponentAPI.api_set_authorizer_option(weixinAuthCode.getComponentAccessToken(), WeChatContants.appId, authorizer_appid, option_name, option_value);
		returnMap.put("baseResult", BaseResult);
		return returnMap;
    }
    /**
     * 第三方平台对其所有API调用次数清零
     * @param response
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/clearQuota",method={RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> clearQuota(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	//获取：component_access_token
		WeixinAuthCode weixinAuthCode=new WeixinAuthCode();
		weixinAuthCode.setAppId(WeChatContants.appId);
		weixinAuthCode=weixinAuthCodeService.selectSingle(weixinAuthCode);
		BaseResult BaseResult=ComponentAPI.clear_quota(weixinAuthCode.getComponentAccessToken(), WeChatContants.appId);
    	returnMap.put("baseResult", BaseResult);
		return returnMap;
    }
    /**
     * 获取预授权码列表页面展示
     * @param response
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value="/getAccessToken",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView getAccessToken(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	ModelAndView mv=new ModelAndView("weixin/access_token-list");
    	PageObject<WeixinAuthCode>  pageObject=weixinAuthCodeService.Pagequery(map);
    	mv.addObject("list", pageObject);
		return mv;
    }
    /**
     * 获取授权方列表
     * @param response
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value="/getLicensorList",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView getLicensorList(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	ModelAndView mv=new ModelAndView("weixin/licensor-list");
    	PageObject<WeixinAuthorizationToken>  pageObject=weixinAuthorizationTokenService.Pagequery(map);
    	mv.addObject("list", pageObject);
		return mv;
    }
    /**
     * 获取某一个授权方信息
     * @param response
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value="/getLicensorInfo",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView getLicensorInfo(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	ModelAndView mv=new ModelAndView("weixin/licensor-info");
    	WeixinAuthorizationInfo weixinAuthorizationInfo=weixinAuthorizationInfoService.selectSingle(map);
    	if(weixinAuthorizationInfo!=null){
    		//获取授权方开通功能情况
    		WeixinBusinessInfo WeixinBusinessInfo=new WeixinBusinessInfo();
    		WeixinBusinessInfo.setAuthorizerAppid(weixinAuthorizationInfo.getAuthorizerAppid());
    		WeixinBusinessInfo=weixinBusinessInfoService.selectSingle(WeixinBusinessInfo);
    		//获取：公众号授权给开发者的权限集列表
    		String info=weixinAuthorizationInfo.getFuncInfo();
    		Map<String,Object> qMap=new HashMap<String, Object>();
			if (info != null) {
				String getinfo[] = info.split("-");
				for (int i = 0; i < getinfo.length; i++) {
					int qID = Integer.valueOf(getinfo[i]);
					if (qID == 1) {
						qMap.put("id1", "消息管理权限");
					}
					if (qID == 2) {
						qMap.put("id2", "用户管理权限");
					}
					if (qID == 3) {
						qMap.put("id3", "帐号服务权限");
					}
					if (qID == 4) {
						qMap.put("id4", "网页服务权限");
					}
					if (qID == 5) {
						qMap.put("id5", "微信小店权限");
					}
					if (qID == 6) {
						qMap.put("id6", "微信小店权限");
					}
					if (qID == 7) {
						qMap.put("id7", "微信多客服权限");
					}
					if (qID == 8) {
						qMap.put("id8", "群发与通知权限");
					}
					if (qID == 9) {
						qMap.put("id9", "微信卡券权限");
					}
					if (qID == 10) {
						qMap.put("id10", "微信扫一扫权限");
					}
					if (qID == 11) {
						qMap.put("id11", "微信连WIFI权限");
					}
					if (qID == 12) {
						qMap.put("id12", "素材管理权限");
					}
					if (qID == 13) {
						qMap.put("id13", "微信摇周边权限");
					}
					if (qID == 14) {
						qMap.put("id14", "微信门店权限");
					}
					if (qID == 15) {
						qMap.put("id15", "微信支付权限");
					}
					if (qID == 16) {
						qMap.put("id16", "自定义菜单权限");
					}
					if (qID == 22) {
						qMap.put("id22", "城市服务接口权限");
					}
					if (qID == 23) {
						qMap.put("id23", "广告管理权限");
					}
					if (qID == 26) {
						qMap.put("id26", "微信电子发票权限");
					}
				}
			}
    		mv.addObject("weixinBusinessInfo", WeixinBusinessInfo);
    		mv.addObject("weixinAuthorizationInfo", weixinAuthorizationInfo);
    		mv.addObject("qMap", qMap);
    	}
    	return mv;
    	
    }
}
