/**
 * 
 */
package com.ivan.config;


/**
 * 配置支付时需要的固定数据(支付宝网页支付和app支付配置)
 * @ClassName: PayConfig 
 * @Description: 
 * @author qiuguojie
 * @date 2016年11月21日 上午10:31:41
 */
public class AliPayConfig {

	
	
	public static final String APP_ID="2017030806105739";
	/** 商户私钥，PKCS8(2048)格式 */
	public static final String PKCS8_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDDcBgnjF1doUeKVvsIAsXjvHTGVLIwQv/aNOsckzhHgH4LccuJ6GbhsdJSaSYy0fFoU4K5evfR0hQEo5ZeV+j+7MaBn2BkKZXHNEYgvQ/RYPE6ehPTxMfZ+MoZav4q7ugs0UeIMhwC6J2L0AoQynNYtRexRzvzAPY82FM6O7vsSiTY2H4UBS/l2WPvEAee9yr/15hFKtAQw87qymp5LVkntrYgbnMkHjrabeAq/OzEig9lFSejBakhnDMEsJl69V5FK7RNGMOcp1V3FrCDqbW1JqBD1N380YVud9YwPJNxjuy9A9Cd2ZbbfjfWl/gGeRQI8SKTJptakuUICvzvaKKpAgMBAAECggEAWvgNNjNerhtI+nqcJFFuHR/2Vu7Ldi1pe/8PxOA93QJRKTCRpiJ36OvlUxjAf/062e2tNnqnaZHYYYRnlIhk2mwJxgISUu0MW4nYbpqyByqRfj+ehP7ea3YjVAt6PS+xAFN1cU6pvSzbUF3IoaIqaIr6eTkIDYdQFmg27S8qvMPBvOaKU6E4sHmKknsjc2IzJoVO7MtPN/bdKXTZx0CZeDRp7Rcqc1T0MIacDpuLzyzT3cZKlPtXrUpBz1PFrdQWtFereYZKblg6EL1lYoOCR+ZdDh17hR+jJsEpO4Im5y0OKqK+QqB3QysXAk+9Bui22kww8firt5jjpSTZTSZvEQKBgQDrWAvRD6jYJs1iomtsJkEX8zr3nYkfYnFnO2PY4Zi7DIXi7YPMFnMKmelS6I1mpPnlVwAFaD4Vn5KM3GzD6p3Bto4No6BxtOJdYmOhomaxRjeLAndCmZqCK0BiOkU5WlOxxqdL7NLgDHq2lnalIwpeXv0FGVbVBCRM2e1PvXW8TQKBgQDUl2Wi2lH7+SKFRdkMxt6WOl6y4atOrzpAaFhM3DdRIsUNj72Q2inHR6+wxd38Nd0pQLGb9ElwGtuXsV6jcP3NttX66IerXirlSpKYuGsiFD5sCJ9UDMZ5CrxB7hmBEbOLZP6pjgROdvpbJawO/+I8oAAOFVp5GmpN18p9moy9zQKBgBSYLNiLnUPlCV4TRoE9S77zyvo2L/SuIaxd9xvYigv8SY8VJ85pPIH1/AQEU7Nixnd6SIcwmxY7U5C+DUqLsmhsODwbUIQCxzhSztMrKf9jXKLugU8zr+CqNkUiQkKFlANfle4/+CghD3AA9Lr4uQSPHWcf04hqh16uR2UN+EqVAoGAYUoH0UdoTY/uJF+avSYlp0p/HA36f7/hQKiAAGynRBJNa69kaW3twK0l0hg7Rri/xak9nslk8lDYPcuGK/YBsKsDshfIPryRlO1TuWBsZuQszPQFNknCapJnJST5tv+K0zgc8yty/RWl7SCIZTTlLCls3QUYqmNUUtt2Z5Ta4+ECgYAZlnGCNMxHpKusP5T4GVuxrwLy44FrO0Y9SdKKevJx9fb10Q9M1HSnqG9UYpmyOukzIFhv8RYsN2xlgqb4Y+A82mBXcq2Dc4uKQ8vy3IPICzpu4OPxaDHYjgrRn3eqgUgGltRp9FniLQgbx/9G9lLtdM3DV7AWI0Ugf+Mc/5Bfcw==";
	/** 支付宝公钥(用于验签) */
	public static final String ALIPAY_RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw3AYJ4xdXaFHilb7CALF47x0xlSyMEL/2jTrHJM4R4B+C3HLiehm4bHSUmkmMtHxaFOCuXr30dIUBKOWXlfo/uzGgZ9gZCmVxzRGIL0P0WDxOnoT08TH2fjKGWr+Ku7oLNFHiDIcAuidi9AKEMpzWLUXsUc78wD2PNhTOju77Eok2Nh+FAUv5dlj7xAHnvcq/9eYRSrQEMPO6spqeS1ZJ7a2IG5zJB462m3gKvzsxIoPZRUnowWpIZwzBLCZevVeRSu0TRjDnKdVdxawg6m1tSagQ9Td/NGFbnfWMDyTcY7svQPQndmW23431pf4BnkUCPEikyabWpLlCAr872iiqQIDAQAB";
	
	/** 支付宝移动支付回调地址  */
	public static final String NOTIFY_URL = "http://shusheyu.oicp.net/pay/aliPayCallBack.do";
	/**
	 * 服务商ID,我183的
	 */
	public static final String SERVICE_PID = "2088522550158391";
	
	/** 手机支付服务名称 */
	public static final String REQUEST_ALIPAY_URL = "https://openapi.alipay.com/gateway.do";
	
	/** format **/
	public static final String FORMAT="json";
	
	/** 默认编码格式  */
	public static final String CHARSET = "utf-8";
	
	/** 接口名称固定值(PC支付) **/
	public static String PAY_METHOD = "alipay.trade.page.pay";
	
	/** 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2 **/
	public static String SIGN_TYPE = "RSA2";

	
	/**************************************************************************转账所需参数***************************************************************************************/
	
	/** 收款方账户类型。可取值：
		1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
		2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。 **/
	public static String PAYEE_TYPE = "ALIPAY_LOGONID";
	
	/** 付款方显示姓名（最长支持100个英文/50个汉字）。
		如果不传，则默认显示该账户在支付宝登记的实名。收款方可见。 **/
	public static String PAYER_SHOW_NAME = "RSA2";
	
	/** 接口名称固定值(转账) **/
	public static String TOACCOUNT_METHOD = "alipay.fund.trans.toaccount.transfer";
	

}
