package com.pay.business.util.wftpay;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.pay.business.util.DecimalUtil;
import com.pay.business.util.wxpay.WeChatConstant;
import com.pay.business.util.wxpay.WeChatUtil;
import com.pay.business.util.wxpay.XMLUtil;

/**
 * 威富通微信支付相关类
 * @author Administrator
 *
 */
public class WftWeChatPay {
	private static final String FAIL_CODE = "code";
	private static final String FAIL_CODE_VALUE = "500";//错误状态码
	
	private static final String ERROR_KEY = "msg";//错误消息的key
	private static final String WX_ERROR_CODE = "wx_error_code";//微信错误的代码
	
	private static Map<String, String> commonErrorMap = new HashMap<String, String>();
	
	static{
		commonErrorMap.put(FAIL_CODE, FAIL_CODE_VALUE);
		commonErrorMap.put(ERROR_KEY, "服务器异常");
		commonErrorMap.put(WX_ERROR_CODE, "");
	}
	/**
     * 威富通微信wap支付
     *
     * @param body       商品信息
     * @param orderid    唯一订单号
     * @param money      价格
     * @param ip         ip地址
     * @param openid     下单openid
     * @param notify_url 异步通知地址
     * @return
     */
    public static Map<String, String> pay(String body, String orderid, String money, String ip) {
        Map<String, String> returnMap = new ConcurrentHashMap<String, String>();
        try {
            // 构造下单所需要的参数
            SortedMap<String, String> map = new TreeMap<String, String>();
            Collections.synchronizedSortedMap(map);
           
            map.put("mch_id", WftWechatConfig.MCH_ID);
            
            //随机字符串
            //String nonce_str = create_nonce_str().replaceAll("-", "");
            map.put("nonce_str",create_nonce_str().replaceAll("-", ""));
            
            // 商品描述
            map.put("body", body);
            
            // 商户订单号
            map.put("out_trade_no", orderid);
            
            // 总金额，单位是分
            map.put("total_fee", money);
            
            //客户端ip
            map.put("mch_create_ip", ip);
            
            // 异步通知地址
            map.put("notify_url", WftWechatConfig.NOTIFY_URL);
            
            // 接口类型
            map.put("service", WftWechatConfig.PAY_SERVICE);
            
            
            Map<String,String> params = SignUtils.paraFilter(map);
            StringBuilder buf = new StringBuilder((params.size() +1) * 10);
            
            SignUtils.buildPayParams(buf,params,false);
            String preStr = buf.toString();
            String sign = WftMD5.sign(preStr, "&key=" + WftWechatConfig.KEY, "utf-8");
            map.put("sign", sign);
            String reqUrl = WftWechatConfig.REQ_URL;
            //System.out.println("reqUrl：" + reqUrl);
            
            //System.out.println("reqParams:" + XmlUtils.parseXML(map));
            Map<String,String> resultMap = XmlUtils.post(reqUrl, map);
            //String res = XmlUtils.toXml(resultMap);
            //System.out.println("请求结果：" + res);
            
            if(resultMap.containsKey("sign")){
                if(!SignUtils.checkParam(resultMap, WftWechatConfig.KEY)){
                    return commonErrorMap;
                }else{
                    if("0".equals(resultMap.get("status"))){
                        
                        
                        String token_id = resultMap.get("token_id");
                        String services = resultMap.get("services");
                        returnMap.put("token_id", token_id);
                        returnMap.put("services", services);
                        return returnMap;
                    }else{
                    	return commonErrorMap;
                    }
                }
            } else{
            	return commonErrorMap;
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        	return commonErrorMap;
        }
    }
    
    /**
     * 威富通微信支付订单查询
     * @param orderid
     * @return
     */
    public static Map<String,String> queryOrder(String orderid){
		Map<String, String> returnMap = new ConcurrentHashMap<String, String>();
        try {
            // 构造下单所需要的参数
            SortedMap<String, String> map = new TreeMap<String, String>();
            Collections.synchronizedSortedMap(map);
            map.put("mch_id", WftWechatConfig.MCH_ID);
           
            //随机字符串
            //String nonce_str = create_nonce_str().replaceAll("-", "");
            map.put("nonce_str",create_nonce_str().replaceAll("-", ""));
            
            // 商户订单号
            map.put("out_trade_no", orderid);
            
            // 接口类型
            map.put("service", WftWechatConfig.QUERY_SERVICE);
            
            Map<String,String> params = SignUtils.paraFilter(map);
            StringBuilder buf = new StringBuilder((params.size() +1) * 10);
            
            SignUtils.buildPayParams(buf,params,false);
            String preStr = buf.toString();
            String sign = WftMD5.sign(preStr, "&key=" + WftWechatConfig.KEY, "utf-8");
            map.put("sign", sign);
            String reqUrl = WftWechatConfig.REQ_URL;
            //System.out.println("reqUrl：" + reqUrl);
            
            //System.out.println("reqParams:" + XmlUtils.parseXML(map));
            Map<String,String> resultMap = XmlUtils.post(reqUrl, map);
            //String res = XmlUtils.toXml(resultMap);
            //System.out.println("请求结果：" + res);
            
            if(resultMap.containsKey("sign")){
                if(!SignUtils.checkParam(resultMap, WftWechatConfig.KEY)){
                    return commonErrorMap;
                }else{
                    if("0".equals(resultMap.get("status"))&&"0".equals(resultMap.get("result_code"))
                    		&&"success".equals(resultMap.get("trade_state"))){
                    	String out_trade_no = resultMap.get("out_trade_no");//商户订单号
						//String trade_status = wechatMap.get("trade_state");//交易状态
						String total_fee = resultMap.get("total_fee");//支付金额
						String transaction_id = resultMap.get("transaction_id");//支付金额
                        
                        returnMap.put("out_trade_no", out_trade_no);
                        returnMap.put("total_fee", total_fee);
                        returnMap.put("transaction_id", transaction_id);
                        return returnMap;
                    }else{
                    	return commonErrorMap;
                    }
                }
            } else{
            	return commonErrorMap;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	return commonErrorMap;
        }
	}
    
    /**
     * 
     *	威富通退款
     * @param orderid  (订单号)
     * @param refundOrderNum (退款订单号)
     * @param money	(总金额)
     * @param refundFee (退款金额)
     * @return
     */
    public static Map<String,String> refund(String orderid,String refundOrderNum, String money, String refundFee){
		Map<String, String> returnMap = new ConcurrentHashMap<String, String>();
        try {
            // 构造下单所需要的参数
            SortedMap<String, String> map = new TreeMap<String, String>();
            Collections.synchronizedSortedMap(map);
           
            map.put("mch_id", WftWechatConfig.MCH_ID);
           
            //随机字符串
            //String nonce_str = create_nonce_str().replaceAll("-", "");
            map.put("nonce_str",create_nonce_str().replaceAll("-", ""));
            
            // 商户订单号
            map.put("out_trade_no", orderid);
            
            // 退款訂單
            map.put("out_refund_no", refundOrderNum);
            
            // 总金额，单位是分
            map.put("total_fee", money);
            
            // 退款金额，单位是分
            map.put("refund_fee", refundFee);
            
            //操作员帐号, 默认为商户号
            map.put("op_user_id", WftWechatConfig.MCH_ID);
            
            // 接口类型
            map.put("service", WftWechatConfig.REFUND_SERVICE);
            
            Map<String,String> params = SignUtils.paraFilter(map);
            StringBuilder buf = new StringBuilder((params.size() +1) * 10);
            
            SignUtils.buildPayParams(buf,params,false);
            String preStr = buf.toString();
            String sign = WftMD5.sign(preStr, "&key=" + WftWechatConfig.KEY, "utf-8");
            map.put("sign", sign);
            String reqUrl = WftWechatConfig.REQ_URL;
            System.out.println("reqUrl：" + reqUrl);
            
            System.out.println("reqParams:" + XmlUtils.parseXML(map));
            Map<String,String> resultMap = XmlUtils.post(reqUrl, map);
            String res = XmlUtils.toXml(resultMap);
            System.out.println("请求结果：" + res);
            
            if(resultMap.containsKey("sign")){
                if(!SignUtils.checkParam(resultMap, WftWechatConfig.KEY)){
                    return commonErrorMap;
                }else{
                    if("0".equals(resultMap.get("status"))&&"0".equals(resultMap.get("result_code"))){
                    	String out_trade_no = resultMap.get("out_trade_no");//商户订单号
						//String trade_status = wechatMap.get("trade_state");//交易状态
						String transaction_id = resultMap.get("transaction_id");//支付金额
						String refund_fee = resultMap.get("refund_fee");//退款金额
                        
						returnMap.put("refund_fee", refund_fee);
                        returnMap.put("out_trade_no", out_trade_no);
                        returnMap.put("transaction_id", transaction_id);
                        return returnMap;
                    }else{
                    	return commonErrorMap;
                    }
                }
            } else{
            	return commonErrorMap;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	return commonErrorMap;
        }
	}
    
    /**
     * 
     *	威富通查询退款
     * @param orderid  (订单号)
     * @param refundOrderNum (退款订单号)
     * @param money	(总金额)
     * @param refundFee (退款金额)
     * @return
     */
    public static Map<String,String> queryRefund(String refundOrderNum){
		Map<String, String> returnMap = new ConcurrentHashMap<String, String>();
        try {
            // 构造下单所需要的参数
            SortedMap<String, String> map = new TreeMap<String, String>();
            Collections.synchronizedSortedMap(map);
           
            map.put("mch_id", WftWechatConfig.MCH_ID);
           
            //随机字符串
            //String nonce_str = create_nonce_str().replaceAll("-", "");
            map.put("nonce_str",create_nonce_str().replaceAll("-", ""));
            
            
            // 退款訂單
            map.put("out_refund_no", refundOrderNum);
            
            
            // 接口类型
            map.put("service", WftWechatConfig.QUERY_REFUND_SERVICE);
            
            Map<String,String> params = SignUtils.paraFilter(map);
            StringBuilder buf = new StringBuilder((params.size() +1) * 10);
            
            SignUtils.buildPayParams(buf,params,false);
            String preStr = buf.toString();
            String sign = WftMD5.sign(preStr, "&key=" + WftWechatConfig.KEY, "utf-8");
            map.put("sign", sign);
            String reqUrl = WftWechatConfig.REQ_URL;
            System.out.println("reqUrl：" + reqUrl);
            
            System.out.println("reqParams:" + XmlUtils.parseXML(map));
            Map<String,String> resultMap = XmlUtils.post(reqUrl, map);
            String res = XmlUtils.toXml(resultMap);
            System.out.println("请求结果：" + res);
            
            if(resultMap.containsKey("sign")){
                if(!SignUtils.checkParam(resultMap, WftWechatConfig.KEY)){
                    return commonErrorMap;
                }else{
                    if("0".equals(resultMap.get("status"))&&"0".equals(resultMap.get("result_code"))){
                    	String out_trade_no = resultMap.get("out_trade_no");//商户订单号
                        
                        returnMap.put("out_trade_no", out_trade_no);
                        return returnMap;
                    }else{
                    	return commonErrorMap;
                    }
                }
            } else{
            	return commonErrorMap;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	return commonErrorMap;
        }
	}
    
    /**
     * 随机算法
     * @return
     */
    protected static String create_nonce_str() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    public static void main(String[] args) throws Exception {
    	//pay("测试支付宝", "12546154636", ""+DecimalUtil.yuanToCents("0.01"), "192.168.1.172");
    	//queryOrder("DD2017030811533193727");
    	//refund("DD2017030811533193727", "56496156", ""+DecimalUtil.yuanToCents("0.02"), ""+DecimalUtil.yuanToCents("0.02"));
    	queryRefund("56496156");
    }
}
