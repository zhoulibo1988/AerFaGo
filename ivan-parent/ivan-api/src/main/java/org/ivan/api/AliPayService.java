package org.ivan.api;

import java.util.Map;

public interface AliPayService {
	/**
	 * 支付宝扫码支付
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> alipay(Map<String,Object> map)throws Exception;
	
}
