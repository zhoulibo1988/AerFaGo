package com.ivan.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.core.redis.RedisDBUtil;
import org.ivan.entity.file.FilmUser;
import org.ivan.entity.utils.CookieUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

public class SecurityInterceptor implements HandlerInterceptor{
	    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
	        FilmUser filmUser=null;
	        //1.从cookie中取token
	       String token= CookieUtils.getCookieValue(req, "userToken");
	       //2.从redis获取用户信息
	       if(token!=null) {
	    	  String userInfo= RedisDBUtil.redisDao.getString(token);
	    	  if(userInfo!=null) {
	    		  filmUser= JSON.parseObject(userInfo, FilmUser.class);
	    		  //更新redis失效时间
	    		  RedisDBUtil.redisDao.setString(token, userInfo, 86400);
	    	  }
	       }
	        // 判断如果没有取到用户信息，就跳转到登陆页面，提示用户进行登陆
	        if (filmUser == null || "".equals(filmUser.toString())) {
	            String scheme = req.getScheme();
	            String serverName = req.getServerName();
	            int port = req.getServerPort();
	            String path = req.getContextPath();
	            String basePath = scheme + "://" + serverName + ":" + port + path;
	            PrintWriter pw = null;
	            String jsScript = "<html><script type=\"text/javascript\">\n window.top.location.href='"+basePath+"/filmUser/loginHtml.do';\n </script></html>";
	            try {
	                pw = res.getWriter();
	                pw.println(jsScript);
	                pw.flush();
	                return false;
	            } finally {
	                if (null != pw) {
	                    pw.close();
	                }
	            }
	        }
	        return true;
	    }

	    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3) throws Exception {
	    }

	    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3) throws Exception {
	    }

}
