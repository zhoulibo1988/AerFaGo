package com.ivan.web.controller.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.AliPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 支付宝-扫码支付
 * @author 周立波
 *
 */
@Controller
@RequestMapping("/ALIPay")
public class ALIPayController {
	private static final Logger logger = LoggerFactory.getLogger(ALIPayController.class);
	@Reference
	private AliPayService aliPayService;
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
	/**
	* @Title: aliPayCallBack 
	* @Description: 支付宝支付扫码回调
	* @param @param map
	* @param @param request
	* @param @param response    设定文件 
	* @return void    返回类型 
	* @throws
	*/
	@RequestMapping(value = "/aliPayCallBack")
	public void aliPayCallBack(@RequestParam Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		logger.info("支付宝扫码支付回调开始传参为："+ map);
		long time1 = System.currentTimeMillis();
		boolean veryfy = aliPayService.verify(map);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (veryfy) {
			// 为真验证没问题,接下来修改数据库状态
			boolean con = false;
			try {
				// 支付宝回调有三种类型 , 正在支付、支付成功、交易取消 , 此处只处理支付成功的逻辑 ,
				// 其他两种类型,直接out.write("success");给支付宝
				if (map.get("trade_status").toString().equals("TRADE_SUCCESS")) {
					// 这里的逻辑应该处理:解析支付宝字段、更新数据库订单状态、回调商户(如果回调商户失败,则不要返回支付宝success,等待支付宝继续回调)
//					con = payv2PayOrderService.aliPayCallBack(map);
				}else{
					con = true;
				}
			} catch (Exception e) {
				logger.info("服务器错误：======》"+e);
			}
			if (con) {
				logger.info("支付宝回调,验签,更改订单状态:======》"+con);
				out.write("success");
			} else {
				logger.info("支付宝回调,更改订单状态:======》"+con);
				out.write("error");
			}
		} else {
			logger.error("支付宝回调,验签失败");
			out.write("error");
		}
		if (out != null) {
			out.close();
		}
		logger.info("回调请求耗时:" + (System.currentTimeMillis() - time1));
	}
}
