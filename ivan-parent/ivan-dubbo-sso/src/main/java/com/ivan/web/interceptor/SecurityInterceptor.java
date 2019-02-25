package com.ivan.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ivan.entity.admin.SysUcenterAdmin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecurityInterceptor implements HandlerInterceptor{
	    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
	        HttpSession session = req.getSession();
	        SysUcenterAdmin user = null;
	        if (session != null) {
	            // 从session 里面获取用户名的信息
	            user = (SysUcenterAdmin) session.getAttribute("admin");
	        }
	        // 判断如果没有取到用户信息，就跳转到登陆页面，提示用户进行登陆
	        if (user == null || "".equals(user.toString())) {
	            // res.sendRedirect(LOGIN_URL);
	            // res.setCharacterEncoding("UTF-8");
	            // res.setContentType("application/json; charset=utf-8");
	            // res.sendRedirect("/page/login.do");
	            String scheme = req.getScheme();
	            String serverName = req.getServerName();
	            int port = req.getServerPort();
	            String path = req.getContextPath();
	            String basePath = scheme + "://" + serverName + ":" + port + path;
	            PrintWriter pw = null;
	            String jsScript = "<html><script type=\"text/javascript\">\n window.top.location.href='"+basePath+"/sys/login.do';\n </script></html>";
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
