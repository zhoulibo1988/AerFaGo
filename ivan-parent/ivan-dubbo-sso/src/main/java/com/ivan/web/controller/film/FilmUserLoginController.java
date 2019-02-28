package com.ivan.web.controller.film;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.file.FilmCommentService;
import org.ivan.api.file.FilmLoginService;
import org.ivan.api.file.FilmUserService;
import org.ivan.core.redis.RedisDBUtil;
import org.ivan.entity.file.FilmLogin;
import org.ivan.entity.file.FilmUser;
import org.ivan.entity.utils.CookieUtils;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.ivan.entity.weixin.utils.MD5Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ivan.web.controller.admin.BaseManagerController;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: FilmUserLoginController
 * @Description: 电影用户操作相关类
 * @author Mr.zhou
 * @date 2019年2月26日 上午9:23:25
 *
 */
@Controller
@RequestMapping("filmUser")
public class FilmUserLoginController extends BaseManagerController<FilmUser>{
	private static final String secretkey="47bce5c74f589f4867dbd57e9ca9f808";
	@Reference
	private FilmLoginService filmLoginService;
	@Reference
	private FilmUserService filmUserService;
	@Reference
	private FilmCommentService filmCommentService;
	@ApiOperation("用户登录")
	@RequestMapping(value="/userLogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userLogin(@RequestParam String userName,String password,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//密码进行MD5加密
		password=MD5Tools.encode(password+secretkey);
		FilmUser filmUser=new FilmUser();
		filmUser.setUserName(userName);
		filmUser.setUserPassword(password);
		filmUser.setIsDelete(2);
		filmUser=filmUserService.selectSingle(filmUser);
		if(filmUser!=null) {
			//登录成功，将信息存入redis里面
			//生成token规则 userName+password+secretkey;
			String token=MD5Tools.encode(userName+password+secretkey);
			//将token存入redis里面去 一天失效 
			String redisJson=JSONObject.toJSONString(filmUser);
			RedisDBUtil.redisDao.setString(token, redisJson, 86400);
			//将token写入cookie 
			CookieUtils.setCookie(request, response, "userToken", token, 86400);
			//记录登录日志
			FilmLogin filmLogin=new FilmLogin();
			filmLogin.setCreationTime(new Date());
			filmLogin.setLoginTime(new Date());
			filmLogin.setUserId(filmUser.getId());
			filmLoginService.insert(filmLogin);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, "filmUser/menu.do");
		}else {
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_PARAMS_CODE, "请输入正确账号和密码!");
		}
		return resultMap;
		
	}
	
	/**
	 * 
	* @Title: menu_admin 
	* @Description: 跳转VIP电影列表
	* @param @param map
	* @param @param modelAndView
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@ApiOperation("登出")
	@RequestMapping("/loginOut")
	public ModelAndView loginOut(@RequestParam Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("film/film/login");
		loginOut(request, response);
		return mv;
	}
	
	@ApiOperation("跳转登录页面")
	@RequestMapping("/loginHtml")
	public ModelAndView loginHtml(@RequestParam Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("film/film/login");
		return mv;
	}
	
	@ApiOperation("跳转注册页面")
	@RequestMapping("/register")
	public ModelAndView register(@RequestParam Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("film/film/register");
		return mv;
	}
	@ApiOperation("注册页面数据提交")
	@RequestMapping("/addRegister")
	@ResponseBody
	public Map<String,Object> addRegister(@RequestParam Map<String, Object> map) {
		Map<String,Object> returnMap=new HashMap<>();
		FilmUser filmUser=new FilmUser();
		filmUser.setIsDelete(2);
		filmUser.setUserEmil(map.get("userEmil").toString());
		FilmUser filmUser1=filmUserService.selectSingle(filmUser);
		if(filmUser1==null) {
			filmUser.setLoginNumber(0);
			filmUser.setIsVip(2);
			filmUser.setUserName( map.get("userEmil").toString());
			//密码进行MD5加密
			String password=MD5Tools.encode(map.get("password").toString()+secretkey);
			filmUser.setUserPassword(password);
			filmUser.setUserPhone( map.get("userPhone").toString());
			filmUser.setCreationTime(new Date());
			filmUser.setUserEmil( map.get("userName").toString());
			filmUserService.insert(filmUser);
			returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		}else {
			returnMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "邮箱已经被使用");
		}
		return returnMap;
		
	}
	
	@ApiOperation("/跳转用户中心页面")
	@RequestMapping("/getUserInfo")
	public ModelAndView getUserInfo(@RequestParam Map<String, Object> map,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("film/film/user");
		FilmUser user=getAdmin(request, response);
		mv.addObject("filmUser", user);
		return mv;
		
	}
	@ApiOperation("修改密码页面")
	@RequestMapping("/updatePassword")
	public ModelAndView updatePassword(@RequestParam Map<String, Object> map,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("film/film/pwd");
		FilmUser user=getAdmin(request, response);
		mv.addObject("filmUser", user);
		return mv;
	}
	@ApiOperation("提交修改密码数据")
	@RequestMapping("/addupdatePassword")
	@ResponseBody
	public Map<String,Object>  addupdatePassword(@RequestParam Map<String, Object> map,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> returnMap=new HashMap<>();
		FilmUser user=getAdmin(request, response);
		if(user!=null) {
			//旧密码
			String oldPassword=map.get("oldPassword").toString();
			oldPassword=MD5Tools.encode(oldPassword+secretkey);
			if(oldPassword.equals(user.getUserPassword())) {
				String newPassword=map.get("newPassword").toString();
				newPassword=MD5Tools.encode(newPassword+secretkey);
				user.setUserPassword(newPassword);
				filmUserService.updateByEntity(user);
				returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
				System.out.println("修改密码成功");
			}else {
				returnMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, "旧密码错误");
			}
		}else {
			returnMap = ReMessage.resultBack(ParameterEunm.ERROR_LOGIN_CODE, "用户没有登录");
		}
		return returnMap;
	}
	public static void main(String[] args) {
		//密码进行MD5加密
		System.out.println(MD5Tools.encode("123456"+secretkey));
	}
}
