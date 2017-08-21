package com.ivan.web.controller.weixin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.entity.WeixinAuthorizationToken;
import org.ivan.entity.weixin.dto.WeChatContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.menu.Button;
import weixin.popular.bean.menu.Matchrule;
import weixin.popular.bean.menu.MenuButtons;
import weixin.popular.bean.menu.selfmenu.CurrentSelfmenuInfo;
import weixin.popular.util.JsonUtil;
/**
 * 微信第三方平台:菜单管理
 * @author 周立波
 *
 */
@Controller
@RequestMapping("/WeiXinMenu")
public class WXMenuController {
	private static final Logger logger = LoggerFactory.getLogger(WXMenuController.class);
	@Reference
	private WeixinAuthorizationTokenService weixinAuthorizationTokenService;
	 /////////////////////////////////////////////////以下为自定义菜单操作/////////////////////////////////////////////////////////////
    /**
     * 添加菜单
     * @param response
     * @param request
     * @param map（authorizer_appid：授权方appid）
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/addCreate",method={RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> addCreate(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//获取授权方的access_token
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    	 //2级菜单：1-2
		Button button_2_2 = new Button();
		button_2_2.setName("全民金服");
		button_2_2.setType("view");
		button_2_2.setUrl("https://www.aijinfu.cn/");
		Button button_2_3 = new Button();
		button_2_3.setName("爱加密");
		button_2_3.setType("view");
		button_2_3.setUrl("http://www.ijiami.cn/");
		Button button_2_4 = new Button();
		button_2_4.setName("百度一下");
		button_2_4.setType("view");
		button_2_4.setUrl("http://www.baidu.com/");
		
		// 组装2级菜单
		List<Button> sub_button1 = new ArrayList<Button>();
		sub_button1.add(button_2_2);
		sub_button1.add(button_2_3);
		sub_button1.add(button_2_4);
		Button button1 = new Button();
		button1.setName("智游网安");
		button1.setSub_button(sub_button1); 
        
        MenuButtons menuButtons=new MenuButtons();
        menuButtons.setButton(new Button[]{button1});
    	//转换JSON
        String jsonMenu = JsonUtil.toJSONString(menuButtons);
        System.out.println(jsonMenu);
    	//请求创建自定义菜单接口
    	BaseResult BaseResult=MenuAPI.menuCreate(weixinAuthorizationToken.getAuthorizerAccessToken(), menuButtons);
    	returnMap.put("baseResult", BaseResult);
		return returnMap;
    }
    /**
     * 获取授权方自定义菜单
     * @param response
     * @param request
     * @param map(authorizer_appid:授权方appid)
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMenu")
    public Map<String,Object> getMenu(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//获取授权方的access_token
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    	weixin.popular.bean.menu.Menu menu=MenuAPI.menuGet(weixinAuthorizationToken.getAuthorizerAccessToken());
    	returnMap.put("menu", menu);
		return returnMap;
    }
    /**
     * 删除授权方的自定义菜单
     * @param response
     * @param request
     * @param map(authorizer_appid:授权方appid)
     * @return
     */
    @ResponseBody
    @RequestMapping("/delMenu")
    public Map<String,Object> delMenu(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//获取授权方的access_token
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    	BaseResult baseResult=MenuAPI.menuDelete(weixinAuthorizationToken.getAuthorizerAccessToken());
    	returnMap.put("baseResult", baseResult);
		return returnMap;
    }
    /**
     * 获取授权方自定义菜单配置
     * 本接口将会提供公众号当前使用的自定义菜单的配置，
	 * 如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
	 * 而如果公众号是在公众平台官网通过网站功能发布菜单，
	 * 则本接口返回运营者设置的菜单配置。
     * @param response
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCurrentSelfmenuInfo")
    public Map<String,Object> getCurrentSelfmenuInfo(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//获取授权方的access_token
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    	CurrentSelfmenuInfo currentSelfmenuInfo=MenuAPI.get_current_selfmenu_info(weixinAuthorizationToken.getAuthorizerAccessToken());
    	returnMap.put("currentSelfmenuInfo",currentSelfmenuInfo);
    	return returnMap;
    }
    /**
     * 创建个性化菜单=》区别在于：根据事先设置的权限，让公众号的不同用户群体看到不一样的自定义菜单
     * @param response
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/menuAddconditional")
    public Map<String,Object> menuAddconditional(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//获取授权方的access_token
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
	    //1-1  
        Button button_1_1=new Button();
        button_1_1.setName("查看天气");
        button_1_1.setType("view");
        button_1_1.setUrl("http://m.hao123.com/a/tianqi");
        
        Button button_1_2=new Button();
        button_1_2.setName("快递助手");
        button_1_2.setType("view");
        button_1_2.setUrl("http://m.kuaidihelp.com/");
        
        Button button_1_3=new Button();
        button_1_3.setName("航班助手");
        button_1_3.setType("view");
        button_1_3.setUrl("http://www.variflight.com/");
        
        //组装2级菜单
        List<Button> sub_button=new ArrayList<Button>();
        sub_button.add(button_1_1);
        sub_button.add(button_1_2);
        sub_button.add(button_1_3);
        Button button=new Button();
        button.setName("生活服务");
        button.setSub_button(sub_button);
        
        
        //3-3
//        Button button_3_1=new Button();
//        button_3_1.setName("男人福利");
//        button_3_1.setType("view");
//        button_3_1.setUrl("http://m.hao123.com/a/tianqi");
//        
//        List<Button> sub_button3=new ArrayList<Button>();
//        sub_button3.add(button_3_1);
//        Button button2=new Button();
//        button2.setName("福利");
//        button2.setSub_button(sub_button3);
        
        //个性化设置：
        Matchrule matchrule=new Matchrule();
//        matchrule.setTag_id("1");
        matchrule.setSex(1);
        
        MenuButtons menuButtons=new MenuButtons();
        menuButtons.setButton(new Button[]{button});
        menuButtons.setMatchrule(matchrule);
    	//转换JSON
        String jsonMenu = JsonUtil.toJSONString(menuButtons);
        System.out.println(jsonMenu);
    	
    	
        BaseResult baseResult=	MenuAPI.menuAddconditional(weixinAuthorizationToken.getAuthorizerAccessToken(), menuButtons);
        returnMap.put("baseResult", baseResult);
		return returnMap;
    }
    /**
     * 删除个性化菜单
     * @param response
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/menuDelconditional")
    public Map<String,Object> menuDelconditional(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String,Object> returnMap=new HashMap<String, Object>();
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	//menuid为菜单id，可以通过自定义菜单查询接口获取
    	String menuid=String.valueOf(map.get("menuid"));
    	//获取授权方的access_token
    	WeixinAuthorizationToken weixinAuthorizationToken=new WeixinAuthorizationToken();
    	weixinAuthorizationToken.setAppId(WeChatContants.appId);
    	weixinAuthorizationToken.setAuthorizerAppid(authorizer_appid);
    	weixinAuthorizationToken=weixinAuthorizationTokenService.selectSingle(weixinAuthorizationToken);
    	BaseResult baseResult =	MenuAPI.menuDelconditional(weixinAuthorizationToken.getAuthorizerAccessToken(), menuid);
    	 returnMap.put("baseResult", baseResult);
		return returnMap;
    }
}
