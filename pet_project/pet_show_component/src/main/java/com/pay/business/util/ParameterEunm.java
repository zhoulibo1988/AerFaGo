package com.pay.business.util;

import com.core.teamwork.base.util.returnback.BaseParameterEunm;


public class ParameterEunm extends BaseParameterEunm{
	
	public static final String PAY_FAILED_CODE = "2011=支付失败";
	public static final String NOTPAY_FAILED_CODE = "2012=订单未支付";
	public static final String PARA_FAILED_CODE = "2013=参数不能为空或者有误";
	public static final String PAY_FAILED_SUCCESS = "2014=支付已完成";
	public static final String PAY_FAILED_KEY_FAIL = "2015=渠道key与appKey不匹配";
	
	public static final String SIGNATURE_ERROR = "100=商户签名错误";
	
	public static final String PARAM_ERROR_MONEY = "101=缺少参数或者参数格式错误，请检查传递的必要参数";
	
	public static final String APP_KEY_INVALID = "501=appKey无效";
	
	public static final String TRANSACTION_NOT_EXIST = "502=交易不存在";
	
	public static final String ORDER_ERROR = "1011=订单错误,请联系技术支持";
	
	public static final String REFUND_NOT_EXIST = "1012=退款单号不存在";
	
	public static final String SHOP_KEY_INVALID = "503=shopKey或者手机类型无效";
	
	public static final String SHOP_NOT_EXIST = "504=店铺信息不存在";
	
	public static final String PAY_WAY_NOT_EXIST = "505=钱包信息不存在";
	
	public static final String PAY_WAY_SDK_VERSION_NOT_EXIST = "506=钱包版本信息不存在";
	
	public static final String PAY_WAY_DISCOUNT_NOT_EXIST = "507=钱包优惠信息不存在";
	
	public static final String SHOP_KEY_MONEY_INVALID = "508=shopKey或者订单金额参数无效";
	
	public static final String SHOP_ERROR = "1013=店铺信息查询错误,请联系技术支持";
	
	public static final String SHOP_DISCOUNT_ERROR = "1014=店铺优惠查询错误,请联系技术支持";
	
	public static final String SHOP_KEY_MONEY_DISCOUNT_PAYTYPE_INVALID = "509=shopKey或者订单金额或者折扣或者支付方式参数无效";
	
	public static final String SHOP_BUILD_ORDER_ERROR = "1015=根据shopKey、订单金额、优惠金额、支付方式创建订单错误,请联系技术支持";
	
	public static final String COMPANY_NOT_EXIST = "5010=商户信息不存在";
	
	public static final String USER_EXIST = "1004=用户已经存在";
	
	public static final String USER_NO_EXIST = "1005=用户不存在";
	
	public static final String USER_AND_PASSWORD_MISMATCH = "1006=帐号和密码不匹配";
}
