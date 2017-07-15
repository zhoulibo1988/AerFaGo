package com.ivan.dubbo.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ivan.api.AliPayService;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.ivan.config.AliPayConfig;

/**
 * 支付宝扫码支付实现类
 * 
 * @author 周立波
 *
 */
@Service
public class AliPayServiceImpl implements AliPayService {
	/**
	 * 获取支付宝扫码支付相关的参数
	 */
	public Map<String, Object> alipay(String orderNum, String goodsName, double amount, int timeOut) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 业务实现
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.REQUEST_ALIPAY_URL, AliPayConfig.APP_ID, AliPayConfig.PKCS8_PRIVATE, AliPayConfig.FORMAT, AliPayConfig.CHARSET, AliPayConfig.ALIPAY_RSA_PUBLIC, AliPayConfig.SIGN_TYPE);
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setNotifyUrl(AliPayConfig.NOTIFY_URL);
		request.setBizContent("{" + "    \"out_trade_no\":\"" + orderNum + "\"," + "    \"total_amount\":" + amount + "," + "    \"subject\":\"" + goodsName + "\"," + "    \"extend_params\":{" + "      \"sys_service_provider_id\":\"" + AliPayConfig.SERVICE_PID + "\"" + "    }," + "    \"timeout_express\":\"" + timeOut + "m\"" + "  }");

		AlipayTradePrecreateResponse response = null;
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
			map.put("code", 10001);
			map.put("msg", "调用支付宝发生错误,可能因为key错误");
			return map;

		}
		JSONObject jsonObject = JSON.parseObject(response.getBody());
		JSONObject json = jsonObject.getJSONObject("alipay_trade_precreate_response");
		Set<String> it = json.keySet();
		for (String string : it) {
			map.put(string, json.getString(string));
		}
		return map;
	}

	/**
	 * 转账到支付宝账户
	 * 
	 * @param toAccountOrderNum
	 *            商户转账唯一订单号。发起转账来源方定义的转账单据ID，用于将转账回执通知给来源方。不同来源方给出的ID可以重复，
	 *            同一个来源方必须保证其ID的唯一性。只支持半角英文、数字，及“-”、“_”。
	 * @param payeeRealName
	 *            收款方真实姓名（最长支持100个英文/50个汉字）。如果本参数不为空，
	 *            则会校验该账户在支付宝登记的实名是否与收款方真实姓名一致。
	 * @param payeeAccount
	 *            收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
	 * @param amount
	 *            转账金额，单位：元。只支持2位小数，小数点前最大支持13位，金额必须大于等于0.1元。
	 * @param remark
	 *            转账备注（支持200个英文/100个汉字）。当付款方为企业账户，且转账金额达到（大于等于）50000元，remark不能为空
	 *            。收款方可见，会展示在收款用户的账单中。
	 * @throws Exception
	 */
	public static void toAliAccount(String toAccountOrderNum, String payeeRealName, String payeeAccount, double amount, String remark) throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient(
					AliPayConfig.REQUEST_ALIPAY_URL, 
					AliPayConfig.APP_ID, 
					AliPayConfig.PKCS8_PRIVATE, 
					AliPayConfig.FORMAT, 
					AliPayConfig.CHARSET, 
					AliPayConfig.ALIPAY_RSA_PUBLIC, 
					AliPayConfig.SIGN_TYPE);
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		request.setBizContent("{" + "    \"out_biz_no\":\"" 
									+ toAccountOrderNum + "\"," + "    \"payee_type\":\"" 
									+ AliPayConfig.PAYEE_TYPE + "\"," + "    \"payee_account\":\"" 
									+ payeeAccount + "\"," + "    \"amount\":\"" + amount + "\"," 
									+ "    \"payer_show_name\":\"" + AliPayConfig.PAYER_SHOW_NAME + "\"," 
									+ "    \"payee_real_name\":\"" + payeeRealName + "\"," + "    \"remark\":\"" 
									+ remark + "\"" + "  }");
		AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			System.out.println("调用成功");
			System.out.println(response.getBody());
		} else {
			System.out.println("调用失败");
			System.out.println(response.getBody());
		}
	}

	/**
	 * 支付宝回调,验签
	 * 
	 * @param map
	 *            支付宝回调参数
	 * @return
	 */
	public  boolean verify(Map<String, String> map) {
		try {
			return AlipaySignature.rsaCheckV1(map, AliPayConfig.ALIPAY_RSA_PUBLIC, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args) {
		AliPayServiceImpl a=new AliPayServiceImpl();
		try {
		Map<String,Object> map=	a.alipay("ZLB348112823754171227", "测试扫码支付", 0.01, 120);
		System.out.println(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
