package org.ivan.api.ali;

import java.util.Map;
/**
 * 支付宝扫码支付
 * @author 周立波
 *
 */
public interface AliPayService {
	/**
	 * 支付宝扫码支付
	 * @param orderNum 商户订单号
	 * @param goodsName商品名字
	 * @param amount 金额
	 * @param timeOut 订单失效时间
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> alipay(String orderNum, String goodsName,double amount, int timeOut)throws Exception;
	/**
	 * 支付宝回调,验签
	 * @param map
	 * @return
	 */
	public  boolean verify(Map<String, String> map);
	
}
