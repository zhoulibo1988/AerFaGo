package com.ivan.web.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ActionReporter implements HandlerInterceptor{

//	private Logger logger = Logger.getLogger(ActionReporter.class);
	private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	private static Boolean bool =true;
//	private static ThreadLocal<Map<String, Long>> paramsThread = new ThreadLocal<Map<String, Long>>();

	/**
	 * Report action before action invoking when the common request coming
	 */
	static final boolean reportCommonRequest(HttpServletRequest request) {
		String content_type = request.getContentType();
		if (content_type == null || content_type.toLowerCase().indexOf("multipart") == -1) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private static final void doReport(HttpServletRequest request, Object handler) {
		StringBuilder sb = new StringBuilder("\naction report -------- ").append(sdf.get().format(new Date())).append(" ------------------------------\n");
		Class<? extends Object> cc = handler.getClass();
		sb.append("Controller  : ").append(cc.getName()).append(".(").append(cc.getSimpleName()).append(".java:1)");
		sb.append("\nMethod      : ").append(request.getServletPath()).append("\n");
		// print all parameters
		Enumeration<String> e = request.getParameterNames();
		if (e.hasMoreElements()) {
			sb.append("Parameter   : ");
			while (e.hasMoreElements()) {
				String name = e.nextElement();
				String[] values = request.getParameterValues(name);
				if (values.length == 1) {
					sb.append(name).append("=").append(values[0]);
				} else {
					sb.append(name).append("[]={");
					for (int i = 0; i < values.length; i++) {
						if (i > 0)
							sb.append(",");
						sb.append(values[i]);
					}
					sb.append("}");
				}
				sb.append("  ");
			}
			sb.append("\n");
		}
		sb.append("--------------------------------------------------------------------------------\n");
		System.out.print(sb.toString());
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		if(request.getServletPath().contains("/GsGame/getClassifyGamePage")){
//			try {
//				String servletPath = request.getServletPath();
//				setTime(servletPath);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//		if(request.getServletPath().contains("/GsGame/getClassifyGamePage")){
//			try {
//				double timeLength = (System.currentTimeMillis() - getTime(request.getServletPath())) / 1000.0;
//				StringBuilder sb = new StringBuilder();
//				sb.append(request.getServletPath() + "?");
//				Enumeration<String> e = request.getParameterNames();
//				if (e.hasMoreElements()) {
//					while (e.hasMoreElements()) {
//						String name = e.nextElement();
//						String[] values = request.getParameterValues(name);
//						if (values.length == 1) {
//							sb.append(name).append("=").append(values[0]);
//						} else {
//							sb.append(name).append("[]={");
//							for (int i = 0; i < values.length; i++) {
//								if (i > 0)
//									sb.append(",");
//								sb.append(values[i]);
//							}
//							sb.append("}");
//						}
//						sb.append("&");
//					}
//					sb.deleteCharAt(sb.length() - 1);
//					sb.append("\n");
//				}
//				sb.append("【耗时:");
//				sb.append(String.valueOf(timeLength));
//				sb.append("s】");
//				logger.info(sb.toString());
//				paramsThread.remove();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		if (bool) {
			boolean isMultipartRequest = ActionReporter.reportCommonRequest(request);
			if (!isMultipartRequest) {
				doReport(request, handler);
			}
		}
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if (bool) {
			boolean isMultipartRequest = ActionReporter.reportCommonRequest(request);
			if (isMultipartRequest) {
				doReport(request, handler);
			}
		}
	}

//	private static Long getTime(String servletPath) {
//		Map<String, Long> timeMap = paramsThread.get();
//		if (null == timeMap) {
//			return (long) 0;
//		}
//		return timeMap.get(servletPath);
//	}

//	private static void setTime(String servletPath) {
//		Map<String, Long> timeMap = paramsThread.get();
//		if (null == timeMap) {
//			timeMap = new HashMap<String, Long>();
//		}
//		timeMap.put(servletPath, System.currentTimeMillis());
//		paramsThread.set(timeMap);
//	}
}
