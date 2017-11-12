package com.pay.business.util.wftpay;

import com.core.teamwork.base.util.ReadPro;

public class WftWechatConfig {
	 /**
     * 威富通交易密钥
     */
    public static String KEY = "11f4aca52cf400263fdd8faf7a69e007";
    
    /**
     * 威富通商户号
     */
    public static String MCH_ID = "7552900037";
    
    /**
     * 威富通请求url
     */
    public static String REQ_URL = "https://pay.swiftpass.cn/pay/gateway";
    
    /**
     * 支付接口类型
     */
    public static String PAY_SERVICE = "unified.trade.pay";
    
    /**
     * 查询订单接口类型
     */
    public static String QUERY_SERVICE = "unified.trade.query";
    
    /**
     * 退款接口类型
     */
    public static String REFUND_SERVICE = "unified.trade.refund";
    
    /**
     * 查询退款接口类型
     */
    public static String QUERY_REFUND_SERVICE = "unified.trade.refundquery";
    
    
    /** 通知url  */
	public static final String NOTIFY_URL = ReadPro.getValue("wft_wx_notify_url");
}
