package com.ivan.web.interceptor;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * springMVC 防止表单重复提交（自定义注解+拦截器）
 * 使用： @RequestMapping("/xzhf/toXshf.do")
		@Token(save=true,key="1")
		public String toHfJSP(……){

    }

    	@RequestMapping("/xzhf/saveXzhf.do")
		@ResponseBody
		@Token(remove=true,key="1")
	public void saveZxhf(……){
        
    }
 * @author 周立波
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{
	private static final Logger LOG = Logger.getLogger(Token.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				String key = annotation.key();
				boolean needSaveSession = annotation.save();
				if (needSaveSession) {
					request.getSession(true).setAttribute("token" + key, UUID.randomUUID().toString());
				}
				boolean needRemoveSession = annotation.remove();
				if (needRemoveSession) {
					if (isRepeatSubmit(request, key)) {
						LOG.warn("please don't repeat submit,url:" + request.getServletPath());
						return false;
					}
					request.getSession(true).removeAttribute("token" + key);
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	private boolean isRepeatSubmit(HttpServletRequest request, String key) {
		String serverToken = (String) request.getSession(true).getAttribute("token" + key);
		if (serverToken == null) {
			return true;
		}
		String clinetToken = request.getParameter("token" + key);
		if (clinetToken == null) {
			return true;
		}
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}
}
