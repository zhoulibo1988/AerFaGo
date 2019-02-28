package com.ivan.web.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.core.redis.RedisDBUtil;
import org.ivan.entity.file.FilmUser;
import org.ivan.entity.utils.CookieUtils;

import com.alibaba.fastjson.JSON;

public class BaseManagerController<T> {
	 /**
	 * @Title: getAdmin 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param request
	 * @param @param response
	 * @param @return    设定文件 
	 * @return FilmUser    返回类型 
	 * @throws
	 */
	public FilmUser getAdmin(HttpServletRequest request, HttpServletResponse response) {
		FilmUser filmUser = null;
		// 1.从cookie中取token
		String token = CookieUtils.getCookieValue(request, "userToken");
		// 2.从redis获取用户信息
		if (token != null) {
			String userInfo = RedisDBUtil.redisDao.getString(token);
			if (userInfo != null) {
				filmUser = JSON.parseObject(userInfo, FilmUser.class);

			}
		}
		if (filmUser != null) {
			return filmUser;
		}
		return null;
	}
	/**
	 * 
	* @Title: loginOut 
	* @Description: 登出
	* @param @param request
	* @param @param response    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void loginOut(HttpServletRequest request, HttpServletResponse response) {
		// 1.从cookie中取token
		String token = CookieUtils.getCookieValue(request, "userToken");
		// 2.从redis获取用户信息
		if (token != null) {
			String userInfo = RedisDBUtil.redisDao.getString(token);
			if (userInfo != null) {
				RedisDBUtil.redisDao.delete(token);
				System.out.println("退出成功");
			}
		}
	}
}
