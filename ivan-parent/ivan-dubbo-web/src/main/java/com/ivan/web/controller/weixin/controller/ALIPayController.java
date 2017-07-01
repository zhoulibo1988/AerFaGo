package com.ivan.web.controller.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付宝-扫码支付
 * @author 周立波
 *
 */
@Controller
@RequestMapping("/ALIPay")
public class ALIPayController {
	/**
	 * 支付宝扫码支付
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("GOaLIPay")
	public Map<String,Object> aliPay(HttpServletResponse response,HttpServletRequest request,@RequestParam Map<String,Object> map){
		Map<String,Object> returnMap=new HashMap<String, Object>();
		
		
		
		return returnMap;
	}
}
