package com.pay.business.util.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.core.teamwork.base.util.IdUtils;
import com.core.teamwork.base.util.ReadPro;
import com.core.teamwork.base.util.pay.alipay.config.AlipayConfig;
import com.core.teamwork.base.util.pay.alipay.sign.RSA;
import com.core.teamwork.base.util.pay.alipay.util.AlipayCore;

/**
 * 
 * @ClassName: AliPay 
 * @Description: 支付宝 用于调用支付(移动支付)
 * @author yangyu
 * @date 2016年11月11日 下午5:05:34
 */
public class AliPay {

	private static final String QUOTES = "\""; //引号 应对参数值的
	
	public static String sign(final String orderNo,final String price,final String subject) throws UnsupportedEncodingException{
		return sign(orderNo, price, subject, null,null);
	}
	
	public static String sign(final String orderNo,final String price,final String subject,final String notify_url) throws UnsupportedEncodingException{
		return sign(orderNo, price, subject, null,notify_url);
	}
	
	/**
	 * 支付宝签名
	 * @param orderNo 订单号
	 * @param price 金额(以元为单位)
	 * @param subject 商品简介
	 * @param body 商品详情 (可空)
	 * @param notify_url 回调地址 (可空,默认调用常量类的)
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String sign(final String orderNo,final String price,final String subject,final String body,final String notify_url) throws UnsupportedEncodingException{
		if(StringUtils.isBlank(subject)){
			throw new NullPointerException("支付宝支付不允许商品简介为空");
		}
		Map<String, String> params = createParam(orderNo, price, body, subject,notify_url); 
		String goods = AlipayCore.createLinkString(params);
		System.out.println(AlipayConfig.private_key);
		String sign = RSA.sign(goods, AlipayConfig.private_key,"UTF-8");
		String signs = URLEncoder.encode(sign, "UTF-8");
		return createPayInfo(goods, signs);
	}
	
	private static String createPayInfo(String goods,String sign){
		String payInfo = goods
				+ "&sign=" + QUOTES + sign + QUOTES + "&sign_type=" + QUOTES + "RSA" + QUOTES;
		return payInfo;
	}
	
	private static Map<String,String> createParam(String orderNo,String price,String body,String subject,String notify_url){
		
		Map<String,String> param = new ConcurrentHashMap<String, String>();
		// 签约合作者身份ID
		param.put("partner", QUOTES + AlipayConfig.partner + QUOTES);
		// 签约卖家支付宝账号
		param.put("seller_id", QUOTES + AlipayConfig.seller_id + QUOTES);
		// 商户网站唯一订单号
		param.put("out_trade_no",QUOTES + orderNo + QUOTES);
		// 商品名称
		param.put("subject", QUOTES + subject + QUOTES);
		
		// 商品详情
		if(StringUtils.isNotBlank(body)){
			param.put("body", QUOTES + body + QUOTES);
		}
		
		// 商品金额	
		param.put("total_fee",QUOTES + price + QUOTES);
		
		// 服务器异步通知页面路径
		if(StringUtils.isNotBlank(notify_url)){
			param.put("notify_url",QUOTES + notify_url + QUOTES);
		}else{
			param.put("notify_url",QUOTES + ReadPro.getValue("alipay_notify_url") + QUOTES);
		}
		
		// 服务接口名称， 固定值
		param.put("service",QUOTES + AlipayConfig.service + QUOTES);
		// 支付类型， 固定值
		param.put("payment_type",QUOTES + "1" + QUOTES);
		// 参数编码， 固定值
		param.put("_input_charset",QUOTES + "utf-8" + QUOTES);
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		//param.put("it_b_pay", "30m"); 
		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		//param.put("return_url", "m.alipay.com");
		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		//param.put("paymethod", "expressGateway");
		return param;
	}
	
	/**
	 * 支付宝查询接口
	 * @param orderNum
	 * @return
	 * @throws AlipayApiException
	 */
	public static JSONObject alipayQuery(String orderNum) throws AlipayApiException{
		AlipayClient alipayClient = new DefaultAlipayClient(PayConfigApi.MOBILEPAY_SERVICE
				, PayConfigApi.APP_ID, PayConfigApi.PKCS8_PRIVATE, PayConfigApi.FORMAT, PayConfigApi.CHARSET
				, PayConfigApi.ALIPAY_RSA_PUBLIC);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent("{" +
		"\"out_trade_no\":\""+orderNum+"\"" +
//		"\"out_trade_no\":\"1481706155863\"" +
		"}");
		AlipayTradeQueryResponse response1 = alipayClient.execute(request);
		String ss = response1.getBody();
		JSONObject json = (JSONObject) JSONObject.parse(ss);
		
		System.out.println(json.get("sign"));
		System.out.println(json.get("alipay_trade_query_response"));
		
		JSONObject result = (JSONObject)json.get("alipay_trade_query_response");
		return result;
	}
	
	/**
	 * h5支付  获取表单
	 * @return
	 * @throws AlipayApiException 
	 */
	public static String alipayH5(String returnUrl,String orderNum,String orderName,Double payMoeny) throws AlipayApiException{
		AlipayClient alipayClient = new DefaultAlipayClient(PayConfigApi.MOBILEPAY_SERVICE
				, PayConfigApi.APP_ID, PayConfigApi.PKCS8_PRIVATE, PayConfigApi.FORMAT, PayConfigApi.CHARSET
				, PayConfigApi.RSA_PUBLIC);
			    AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
		alipayRequest.setNotifyUrl(PayConfigApi.H5_NOTIFY_URL);//在公共参数中设置回跳和通知地址
		if(returnUrl!=null){
			alipayRequest.setReturnUrl(returnUrl);	 
		}
		alipayRequest.setBizContent("{" +
		        "\"out_trade_no\":\""+orderNum+"\"," +
		        "\"total_amount\":\""+payMoeny+"\"," +
		        "\"subject\":\""+orderName+"\"," +
		        "\"product_code\":\"QUICK_WAP_PAY\"" +
		        "}");//填充业务参数
		String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
		return form;
	}
	
	/**
	 * 支付退款
	 * @param orderNum
	 * @param refundMoney
	 * @param map
	 * @return
	 * @throws AlipayApiException
	 */
	public static AlipayTradeRefundResponse alipayRefund(String orderNum,Double refundMoney,Map<String,Object> map) throws AlipayApiException{
		AlipayClient alipayClient = new DefaultAlipayClient(PayConfigApi.MOBILEPAY_SERVICE,
				PayConfigApi.APP_ID,PayConfigApi.PKCS8_PRIVATE,PayConfigApi.FORMAT,PayConfigApi.CHARSET,
				PayConfigApi.ALIPAY_RSA_PUBLIC);
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent(alipayBizContent(orderNum, refundMoney, map));
		AlipayTradeRefundResponse response = alipayClient.execute(request);
		return response;
	}
	
	/**
	 * 支付宝条码支付
	 * @param orderNum
	 * @param authCode
	 * @param orderName
	 * @param payMoney
	 * @return
	 * @throws AlipayApiException
	 */
	public static AlipayTradePayResponse scanPay(String orderNum,String authCode,String orderName,Double payMoney) throws AlipayApiException{
		//条码支付
		AlipayClient alipayClient = new DefaultAlipayClient(PayConfigApi.MOBILEPAY_SERVICE, PayConfigApi.APP_ID,
				PayConfigApi.PKCS8_PRIVATE, PayConfigApi.FORMAT, PayConfigApi.CHARSET, PayConfigApi.ALIPAY_RSA_PUBLIC); //获得初始化的AlipayClient
		AlipayTradePayRequest request = new AlipayTradePayRequest(); //创建API对应的request类
		request.setBizContent("{" +
		"    \"out_trade_no\":\""+orderNum+"\"," +
		"    \"scene\":\"bar_code\"," +
		"    \"auth_code\":\""+authCode+"\"," +
		"    \"subject\":\""+orderName+"\"," +
		"    \"total_amount\":\""+payMoney+"\"" +
		"  }"); //设置业务参数
		AlipayTradePayResponse response = alipayClient.execute(request); //通过alipayClient调用API，获得对应的response类
		System.out.print(response.getBody());
		return response;
	}
	
	/**
	 * 退款参数拼接
	 * @param orderNum
	 * @param refundMoney
	 * @param map
	 * @return
	 */
	private static String alipayBizContent(String orderNum,Double refundMoney,Map<String,Object> map){
		String str = "";
		String refundReason = map.get("refundReason")==null?"正常退款":map.get("refundReason").toString();
		if(map.get("refundType").equals("Y")){
			str = "{" +
					"\"out_trade_no\":\""+orderNum+"\"," +
					"\"refund_amount\":"+refundMoney+"," +
					"\"refund_reason\":\""+refundReason+"\"" +
					"}";
		}else{
			//部分退款out_request_no每次的值都不一样，保证唯一
			str = "{" +
					"\"out_trade_no\":\""+orderNum+"\"," +
					"\"refund_amount\":"+refundMoney+"," +
					"\"refund_reason\":\""+refundReason+"\"," +
					"\"out_request_no\":\""+IdUtils.createRandomString(9)+"\"" +
					"}";
		}
		
		return str;
	}
}
