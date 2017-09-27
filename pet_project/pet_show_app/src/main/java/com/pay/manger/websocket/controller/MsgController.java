package com.pay.manger.websocket.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;
import com.pay.business.admin.entity.SysUcenterAdmin;
import com.pay.business.admin.service.SysUcenterAdminService;
import com.pay.manger.controller.base.InterfaceBaseController;
import com.pay.manger.websocket.util.Message;
import com.pay.manger.websocket.websocket.MyWebSocketHandler;

/**
 * @Title: MsgController.java
 * @Package org.xdemo.example.websocket.controller
 * @Description:在线聊天入口
 * @author ZHOULIBO
 * @date 2017年3月10日 上午11:35:34
 * @version V1.0
 */
@Controller
@RequestMapping("/msg")
public class MsgController extends InterfaceBaseController {
	private static final Logger logger = Logger.getLogger(MsgController.class);
	@Resource
	MyWebSocketHandler handler;
	@Autowired
	private SysUcenterAdminService sysUcenterAdminService;

	/**
	 * @Title: doLogin
	 * @Description:用户登录
	 * @param @param user
	 * @param @param request
	 * @param @param map
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping(value = "login")
	public ModelAndView doLogin(SysUcenterAdmin user, HttpServletRequest request, @RequestParam Map<String, Object> map) {
		user = sysUcenterAdminService.detail(map);
		ModelAndView v = new ModelAndView();
		if (user != null) {
			request.getSession().setAttribute("uid", user.getId());
			request.getSession().setAttribute("name", user.getAdmName());
			logger.info("用户ID[" + user.getId() + "]" + "登录成功!");
			Map<String, Object> maps = new HashMap<String, Object>();
			List<SysUcenterAdmin> userList = sysUcenterAdminService.query(maps);
			//删除自己
			for (SysUcenterAdmin sysUcenterAdmin : userList) {
				if(sysUcenterAdmin.getId().longValue()==user.getId().longValue()){
					userList.remove(sysUcenterAdmin);
					break;
				}
			}
			request.getSession().setAttribute("userList", userList);
			v.setViewName("redirect:talk");
		}else{
			v.setViewName("400");
		}
		return v;
	}

	/**
	 * @Title: talk
	 * @Description:跳转到交谈聊天页面
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping(value = "talk", method = RequestMethod.GET)
	public ModelAndView talk() {
		return new ModelAndView("talk");
	}

	/**
	* @Title: broadcast 
	* @Description:跳转到发布广播页面
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	*/
	@RequestMapping(value = "broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		return new ModelAndView("broadcast");
	}
	/**
	* @Title: broadcast 
	* @Description:发布系统广播（群发）
	* @param @param text
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	*/
	@ResponseBody
	@RequestMapping(value = "broadcast", method = RequestMethod.POST)
	public void broadcast(String text) throws IOException {
		Message msg = new Message();
		msg.setDate(new Date());
		msg.setFrom(-1L);
		msg.setFromName("系统广播");
		msg.setTo(0L);
		msg.setText(text);
		handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
	}
	/**
	* @Title: broadcastFriends 
	* @Description:跳转给某个好友发送消息
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	*/
	@RequestMapping(value = "broadcastFriends", method = RequestMethod.GET)
	public ModelAndView broadcastFriends() {
		return new ModelAndView("friends");
	}
	/**
	* @Title: sentFriends 
	* @Description:给某一个好友发送消息
	* @param @param map
	* @param @param request
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	*/
	@ResponseBody
	@RequestMapping(value = "sentFriends", method = RequestMethod.POST)
	public void sentFriends(@RequestParam Map<String, Object> map, HttpServletRequest request) throws IOException {
		Message msg = new Message();
		msg.setDate(new Date());
		msg.setFrom(Long.valueOf(request.getSession().getAttribute("uid").toString()));
		msg.setFromName(request.getSession().getAttribute("name").toString());
		msg.setTo(Long.valueOf(map.get("id").toString()));
		msg.setText(map.get("msg").toString());
		// handler.broadcast(new TextMessage(new
		// GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
		handler.sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
	}
}