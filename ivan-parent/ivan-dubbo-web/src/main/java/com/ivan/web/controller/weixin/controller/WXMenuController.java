package com.ivan.web.controller.weixin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.api.weixin.WeixinOneMenuService;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.ivan.entity.weixin.dto.WeChatContants;
import org.ivan.entity.wx.WeixinAuthorizationToken;
import org.ivan.entity.wx.WeixinOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.menu.Button;
import weixin.popular.bean.menu.Matchrule;
import weixin.popular.bean.menu.Menu;
import weixin.popular.bean.menu.MenuButtons;
import weixin.popular.bean.menu.selfmenu.CurrentSelfmenuInfo;
import weixin.popular.util.JsonUtil;

import com.alibaba.dubbo.config.annotation.Reference;
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
	@Reference
	private WeixinOneMenuService weixinOneMenuService;
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

    	Map<String,Object> wMap=new HashMap<String, Object>();
    	wMap.put("authorizerAppid", authorizer_appid);
    	List<WeixinOneMenu> appidList=weixinOneMenuService.getList(wMap);
    	int sum=0;
    	for (WeixinOneMenu weixinOneMenu : appidList) {
    		sum=sum+weixinOneMenu.getMenuType();
		}
    	if(sum==0){
    		returnMap = ReMessage.resultBack(ParameterEunm.ERROR_403_CODE, null);
    		logger.error("无菜单可以发布");
    		return returnMap;
    	}
    	wMap.put("fid", -1);
    	List<WeixinOneMenu> mList=weixinOneMenuService.getList(wMap);
    	Map<String,Object> buttonMap=new HashMap<String, Object>();
    	for (WeixinOneMenu weixinOneMenu : mList) {
    		//1级
    		Button button = new Button();
    		button.setName(weixinOneMenu.getName());
    		if(weixinOneMenu.getTykey()!=null&&!weixinOneMenu.getTykey().equals("")){
    			button.setKey(weixinOneMenu.getTykey());
    		}
    		if(weixinOneMenu.getType()!=null&&!weixinOneMenu.getType().equals("")){
    			button.setType(weixinOneMenu.getType());
    		}
    		if(weixinOneMenu.getUrl()!=null&&!weixinOneMenu.getUrl().equals("")){
    			button.setUrl(weixinOneMenu.getUrl());
    		}
    		//获取2级
    		Map<String,Object> wMap2=new HashMap<String, Object>();
    		wMap2.put("fid", weixinOneMenu.getId());
    		List<WeixinOneMenu> mList2=weixinOneMenuService.getList(wMap2);
    		// 组装2级菜单
    		List<Button> sub_button = new ArrayList<Button>();
    		for (WeixinOneMenu weixinOneMenu2 : mList2) {
    			Button button2 = new Button();
    			button2.setName(weixinOneMenu2.getName());
        		if(weixinOneMenu2.getTykey()!=null&&!weixinOneMenu2.getTykey().equals("")){
        			button2.setKey(weixinOneMenu2.getTykey());
        		}
        		if(weixinOneMenu2.getType()!=null&&!weixinOneMenu2.getType().equals("")){
        			button2.setType(weixinOneMenu2.getType());
        		}
        		if(weixinOneMenu2.getUrl()!=null&&!weixinOneMenu2.getUrl().equals("")){
        			button2.setUrl(weixinOneMenu2.getUrl());
        		}
        		sub_button.add(button2);
			}
    		button.setSub_button(sub_button);
    		buttonMap.put(String.valueOf(weixinOneMenu.getId().toString()), button);
		}
    	MenuButtons menuButtons=new MenuButtons();
    	Button[] a=new Button[mList.size()];
    	for (int i = 0; i < mList.size(); i++) {
			a[i]=(Button) buttonMap.get(String.valueOf(mList.get(i).getId()));
		}
    	menuButtons.setButton(a);
    	//转换JSON
        String jsonMenu = JsonUtil.toJSONString(menuButtons);
        System.out.println(jsonMenu);
    	//请求创建自定义菜单接口
    	BaseResult BaseResult=MenuAPI.menuCreate(weixinAuthorizationToken.getAuthorizerAccessToken(), menuButtons);
    	if(BaseResult.getErrcode().equals("0")){
    		//发布成功后 改变数据菜单状态
    		for (WeixinOneMenu weixinOneMenu : appidList) {
    			//改为发布状态
    			weixinOneMenu.setMenuType(0);
    			weixinOneMenuService.updateByEntity(weixinOneMenu);
			}
    		returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
    	}else{
    		returnMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
    		logger.error("失败原因:"+BaseResult.getErrcode()+"---"+BaseResult.getErrmsg());
    	}
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
    	Menu menu=MenuAPI.menuGet(weixinAuthorizationToken.getAuthorizerAccessToken());
    	JSONObject json = JSONObject.fromObject(menu);//将java对象转换为json对象  
        String str = json.toString();//将json对象转换为字符串  
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
    	if(baseResult.getErrcode().equals("0")){//微信公众号成功删除；再删除本地菜单
    		Map<String, Object> delMap=new HashMap<String, Object>();
    		delMap.put("authorizerAppid", authorizer_appid);
    		weixinOneMenuService.del(returnMap);
    	}
    	returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
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
    	JSONObject json = JSONObject.fromObject(currentSelfmenuInfo);//将java对象转换为json对象  
        String str = json.toString();//将json对象转换为字符串  
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
    /**
     * 跳转添加自定义菜单页面
     * @param response
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/addmenuTo")
    public ModelAndView addmenuTo(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	ModelAndView mv=new ModelAndView("weixin/weixin-menu-add");
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	map=new HashMap<String, Object>();
    	map.put("authorizerAppid", authorizer_appid);
    	map.put("fid", -1);
    	List<WeixinOneMenu> funList=weixinOneMenuService.getList(map);
    	mv.addObject("funList", funList);
    	map.remove("fid");
    	mv.addObject("map", map);
		return mv;
    }
    
    /**
     * 添加菜单
     * @param response
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/addMenu")
    public Map<String,Object> addMenu(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	WeixinOneMenu weixinOneMenu=new WeixinOneMenu();
    	weixinOneMenu.setFid(Integer.valueOf(map.get("fid").toString()));
    	weixinOneMenu.setAuthorizerAppid(map.get("authorizer_appid").toString());
    	weixinOneMenu.setName(map.get("name").toString());
    	if(map.containsKey("tykey")){
    		weixinOneMenu.setTykey(map.get("tykey").toString());
    	}
    	if(map.containsKey("type")){
    		weixinOneMenu.setType(map.get("type").toString());
    	}
    	if(map.containsKey("url")){
    		weixinOneMenu.setUrl(map.get("url").toString());
    	}
    	weixinOneMenuService.insert(weixinOneMenu);
    	returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		return returnMap;
    }
    /**
     * 跳转菜单列表页面
     * @param response
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/getMenuToList")
    public ModelAndView getMenuToList(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	ModelAndView mv=new ModelAndView("weixin/weixin-menu-list");
    	String authorizer_appid=String.valueOf(map.get("authorizer_appid"));
    	map=new HashMap<String, Object>();
    	map.put("authorizerAppid", authorizer_appid);
    	map.put("fid", -1);
    	List<WeixinOneMenu> funList=weixinOneMenuService.getList(map);
    	List<WeixinOneMenu> fun2List=weixinOneMenuService.getListBy2Menu(map);
    	mv.addObject("funList", funList);
    	mv.addObject("fun2List", fun2List);
    	map.remove("fid");
    	mv.addObject("map", map);
		return mv;
    }
    /**
     * 跳转修改页面
     * @param response
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/updateMenuTo")
    public ModelAndView updateMenuTo(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	ModelAndView mv=new ModelAndView("weixin/weixin-menu-update");
    	WeixinOneMenu  weixinOneMenu =	weixinOneMenuService.selectSingle(map);
    	mv.addObject("weixinOneMenu", weixinOneMenu);
    	mv.addObject("map", map);
		return mv;
    }
    /**
     * 修改菜单
     * @param response
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMenuInfo")
    public Map<String,Object> updateMenuInfo(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	map.put("menuType", 1);
    	weixinOneMenuService.updateByEntity(map);
    	returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		return returnMap;
    }
}
