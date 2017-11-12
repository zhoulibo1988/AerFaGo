/**
 * 
 */
package com.pay.business.util.alipay;

import com.core.teamwork.base.util.ReadPro;

/**
 * 配置支付时需要的固定数据(支付宝网页支付和app支付配置)
 * @ClassName: PayConfig 
 * @Description: 
 * @author qiuguojie
 * @date 2016年11月21日 上午10:31:41
 */
public class PayConfigApi {
	
	public static final String SELLER = "3533209431@qq.com";
	
	/** 商户私钥，pkcs8格式 */
	public static final String PKCS8_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKwdNbS2ATiCB+gtc1DvqK2bT9j8irBt2sHNFfsZGskq4KxbftuFRMWTby/ZYGhD0W0UpNQNmmTDPQW4/WaCFqGBDMf4GvQ2jp8/JNloyac90QVX6yjKttLHeGyLdKJ3qht3ltjpCr380kbg+xklyttF70Uu0pt6nlT51ldLwTPFAgMBAAECgYAQ9G+t9Z/5KRYOUSvi9TjwoTQkc/xVlPzPypw3uCljV836LKk6hs5XiEaTuB4/Jy8iR75VBWIIOxNv1OFhj78a0FOENB5fKWwIXypQi47Jm0APMdEkOUTNEyGmIZwMnrfv8+Sh+AsQBaVAlpJvc04476V+ubF5N1GsYsKHVPPlAQJBAOQwIl4Sq7LIQlEa/YMgzW9eBV62qozeORM75HYcAzc4W9C1Gktlpn5ID18B1fpu2RTYfXQvZFcr3pIws8xFU2kCQQDBF3iR/+fE/xM0/X+rUReVmZq0WqNC4YxiID+ghWvAbBdfAd54VIOjw2MiDVNv+N3Lo7E9AkRuMo20JCDSd/39AkEA4c2P9K9dIKlylney7hODtvS/M55m3sb4i8P0q7vEotwuXzUXNAz+2G/OZiGW8R7Hyg0A9/v9uxU6RkfTZAwmIQJBAI2IzMIH2DVX2xZOclR6/lST4QguH9mYRjRu+vSIl7DWClODpTSCjnNtdq6xIeXDf6AS/ol/rfUwbRoMGVrsxWkCQCxZpEL2CgSWcMefStUmKGzpw7CdrxLnQFkzbOwIuOX1oJ/L11SE6wArBXEmN0rafxg+hmB+3wVxxN7Q+RD9Z+8=";
	
	/** 商户公钥，rsa格式 */
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsHTW0tgE4ggfoLXNQ76itm0/Y/IqwbdrBzRX7GRrJKuCsW37bhUTFk28v2WBoQ9FtFKTUDZpkwz0FuP1mghahgQzH+Br0No6fPyTZaMmnPdEFV+soyrbSx3hsi3Sid6obd5bY6Qq9/NJG4PsZJcrbRe9FLtKbep5U+dZXS8EzxQIDAQAB";
	
	/** 支付宝公钥(用于验签) */
	public static final String ALIPAY_RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	
	/** 支付宝移动支付回调地址  */
	public static final String NOTIFY_URL = ReadPro.getValue("alipay_notify_url");
	
	/** 支付宝H5回调地址  */
	public static final String H5_NOTIFY_URL = ReadPro.getValue("h5_alipay_notify_url");
	
	/** 支付宝H5回调地址  */
	public static final String H5_RETURN_URL = ReadPro.getValue("h5_alipay_return_url");
	
	/** 手机支付服务名称 */
	public static final String MOBILEPAY_SERVICE = "https://openapi.alipay.com/gateway.do";
	
	/** app_id **/
	public static final String APP_ID="2016110202497535";
	
	/** format **/
	public static final String FORMAT="json";
	
	/** 默认编码格式  */
	public static final String CHARSET = "utf-8";
	
	/**
	 * 接口名称固定值(app支付)
	 */
	public static String method = "alipay.trade.app.pay";

}
