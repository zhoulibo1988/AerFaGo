package weixin.popular.api;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

import weixin.popular.bean.BaseResult;
import weixin.popular.bean.shop.commodity.Attrext;
import weixin.popular.bean.shop.commodity.BaseResultByShop;
import weixin.popular.bean.shop.commodity.BaseResultByShopInfo;
import weixin.popular.bean.shop.commodity.BaseResultByShopInfoList;
import weixin.popular.bean.shop.commodity.BaseResultClass;
import weixin.popular.bean.shop.commodity.BaseResultProperties;
import weixin.popular.bean.shop.commodity.BaseResultSuk;
import weixin.popular.bean.shop.commodity.Base_attr;
import weixin.popular.bean.shop.commodity.Delivery_info;
import weixin.popular.bean.shop.commodity.ShopInfo;
import weixin.popular.bean.shop.commodity.Sku_list;
import weixin.popular.bean.shop.grouping.BaseResultAdd;
import weixin.popular.bean.shop.grouping.BaseResultGroupID;
import weixin.popular.bean.shop.grouping.BaseResultGroupInfo;
import weixin.popular.bean.shop.grouping.Group_detail;
import weixin.popular.bean.shop.grouping.Product;
import weixin.popular.bean.shop.postage.BaseResultInfo;
import weixin.popular.bean.shop.postage.BaseResultInfoAll;
import weixin.popular.bean.shop.postage.BaseResultPostage;
import weixin.popular.bean.shop.postage.Delivery_template;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.util.JsonUtil;

/**
 * 微信小店
 * @author 周立波
 *
 */
public class MerchantAPI extends BaseAPI{
	/**
	 * ********************************************************
	 *	1.商品管理接口
	 * ********************************************************
	 */
	/**
	 * 添加商品
	 * @param access_token
	 * @param shopInfo
	 * @return
	 */
	public static BaseResultByShop addShop(String access_token,ShopInfo shopInfo){
		String str = JsonUtil.toJSONString(shopInfo);
		return addShop(access_token,str);
	}
	/**
	 * 添加商品
	 * @param access_token
	 * @param shopInfoJson
	 * @return
	 */
	public static BaseResultByShop addShop(String access_token,String shopInfoJson){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/create")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(shopInfoJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultByShop.class);
	}
	/**
	 * 删除商品
	 * @param access_token
	 * @param product_id
	 * @return
	 */
	public static BaseResult delShop(String access_token,String product_id){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/del")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity("{\"product_id\":\""+product_id+"\"}",Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}
	/**
	 * 修改商品
	 * @param access_token
	 * @param product_id
	 * @param base_attr
	 * @param delivery_info
	 * @param sku_list
	 * @param attrext
	 * @return
	 */
	public static BaseResult updateShop(String access_token,String product_id,Base_attr base_attr,Delivery_info delivery_info,Sku_list sku_list,Attrext attrext){
		String product_base = JsonUtil.toJSONString(base_attr);
		String delivery_infos = JsonUtil.toJSONString(delivery_info);
		String sku_lists = JsonUtil.toJSONString(sku_list);
		String attrexts = JsonUtil.toJSONString(attrext);
		String messageJson = "{\"product_id\":\""+product_id+"\",\"product_base\":"+product_base+",\"delivery_info\":"+delivery_infos+",\"sku_list\":"+sku_lists+",\"attrext\":"+attrexts+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
										.setHeader(jsonHeader)
										.setUri(BASE_URI+"/merchant/update")
										.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
										.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
										.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}
	/**
	 * 查询商品
	 * @param access_token
	 * @param product_id	商品ID
	 * @return
	 */
	public static BaseResultByShopInfo getShop(String access_token,String product_id){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/get")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity("{\"product_id\":\""+product_id+"\"}",Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultByShopInfo.class);
	}
	/**
	 * 根据状态来查询商品
	 * @param access_token
	 * @param status		商品状态(0-全部, 1-上架, 2-下架)
	 * @return
	 */
	public static BaseResultByShopInfoList getShopByStatus(String access_token,String status){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/getbystatus")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity("{\"status\":\""+status+"\"}",Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultByShopInfoList.class);
	}
	/**
	 * 商品上下架
	 * @param access_token
	 * @param status		商品上下架标识(0-下架, 1-上架)
	 * @param product_id	商品ID
	 * @return
	 */
	public static BaseResult modproductstatus(String access_token,String status,String product_id){
		String messageJson = "{\"product_id\":\""+product_id+"\",\"status\":"+status+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/modproductstatus")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}
	/**
	 * 获取指定分类的所有子分类
	 * @param access_token
	 * @param cate_id		大分类ID(根节点分类id为1)
	 * @return
	 */
	public static BaseResultClass getClassById(String access_token,String cate_id){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/category/getsub")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity("{\"cate_id\":\""+cate_id+"\"}",Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultClass.class);
	}
	/**
	 * 获取指定子分类的所有SKU
	 * @param access_token
	 * @param cate_id		分类ID
	 * @return
	 */
	public static BaseResultSuk getSku(String access_token,String cate_id){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/category/getsku")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity("{\"cate_id\":\""+cate_id+"\"}",Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultSuk.class);
	}
	/**
	 * 获取指定分类的所有属性
	 * @param access_token
	 * @param cate_id		分类ID
	 * @return
	 */
	public static BaseResultProperties getProperty(String access_token,String cate_id){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/category/getproperty")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity("{\"cate_id\":\""+cate_id+"\"}",Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultProperties.class);
	}
	/**
	 * ********************************************************
	 *	2.库存管理接口											  *
	 * ********************************************************
	 */
	
	/**
	 * 增加库存
	 * @param access_token
	 * @param quantity		库存数量
	 * @param sku_info		sku信息,格式"id1:vid1;id2:vid2",如商品为统一规格，则此处赋值为空字符串即可
	 * @return
	 */
	public static BaseResult addStock(String access_token,String quantity,String sku_info,String product_id){
		String messageJson = "{\"quantity\":\""+quantity+"\",\"sku_info\":"+sku_info+"\",\"product_id\":"+product_id+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/stock/add")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}
	/**
	 * 减少库存
	 * @param access_token
	 * @param quantity		减少的库存数量
	 * @param sku_info		sku信息,格式"id1:vid1;id2:vid2"
	 * @return
	 */
	public static BaseResult reduceStock(String access_token,String quantity,String sku_info,String product_id){
		String messageJson = "{\"quantity\":\""+quantity+"\",\"sku_info\":"+sku_info+"\",\"product_id\":"+product_id+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/stock/reduce")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}
	
	/**
	 * ********************************************************
	 *	3.邮费模板管理接口										  *
	 * ********************************************************
	 */
	
	/**
	 * 增加邮费模板
	 * @param access_token
	 * @param delivery_template
	 * @return
	 */
	public static BaseResultPostage addExpress(String access_token,Delivery_template delivery_template){
		String str = JsonUtil.toJSONString(delivery_template);
		String messageJson = "{\"delivery_template\":\""+str+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/express/add")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultPostage.class);
	}
	/**
	 * 删除邮件模板
	 * @param access_token
	 * @param template_id	邮件模板ID
	 * @return
	 */
	public static BaseResult delExpress(String access_token,String template_id){
		String messageJson = "{\"template_id\":\""+template_id+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/express/del")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}
	/**
	 * 修改邮件模板
	 * @param access_token
	 * @param template_id		邮件模板ID
	 * @param delivery_template	邮费模板信息(字段说明详见增加邮费模板)
	 * @return
	 */
	public static BaseResult updateExpress(String access_token,String template_id,Delivery_template delivery_template){
		String str = JsonUtil.toJSONString(delivery_template);
		String messageJson = "{\"template_id\":\""+template_id+"\",\"delivery_template\":"+str+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/express/update")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
		
	}
	/**
	 * 获取指定ID的邮费模板
	 * @param access_token
	 * @param template_id
	 * @return
	 */
	public static BaseResultInfo getExpressById(String access_token,String template_id){
		String messageJson = "{\"template_id\":\""+template_id+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/express/getbyid")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultInfo.class);
	}
	/**
	 * 获取所有邮费模板
	 * @param access_token
	 * @return
	 */
	public static BaseResultInfoAll getExpressAll(String access_token){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setHeader(jsonHeader)
				.setUri(BASE_URI + "/merchant/express/getall")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, BaseResultInfoAll.class);
	}
	/**
	 * ********************************************************
	 *	4.分组管理接口											  *
	 * ********************************************************
	 */
	
	/**
	 * 增加分组
	 * @param access_token
	 * @param group_detail	分组信息
	 * @return
	 */
	public static BaseResultAdd addGroup(String access_token,Group_detail group_detail){
		String str=JsonUtil.toJSONString(group_detail);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/group/add")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(str,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultAdd.class);
	}
	/**
	 * 删除分组
	 * @param access_token
	 * @param group_id		分组ID
	 * @return
	 */
	public static BaseResult delGroup(String access_token,String  group_id){
		String messageJson = "{\"group_id\":\""+group_id+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/group/del")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
		
	}
	/**
	 * 修改分组属性
	 * @param access_token
	 * @param group_id
	 * @return
	 */
	public static BaseResult updateGroup(String access_token,String  group_id,String group_name){
		String messageJson = "{\"group_id\":\""+group_id+"\",\"group_name\":"+group_name+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/group/propertymod")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}
	/**
	 * 修改分组商品
	 * @param access_token
	 * @param group_id
	 * @param product
	 * @return
	 */
	public static BaseResult updateProduct(String access_token,String  group_id,List<Product> product){
		String str=JsonUtil.toJSONString(product);
		String messageJson = "{\"group_id\":\""+group_id+"\",\"product\":"+str+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/group/productmod")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}
	/**
	 * 获取所以分组
	 * @param access_token
	 * @return
	 */
	public static BaseResultGroupInfo getGroupAll(String access_token){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setHeader(jsonHeader)
				.setUri(BASE_URI + "/merchant/group/getall")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, BaseResultGroupInfo.class);
	}
	/**
	 * 根据ID获取分组信息
	 * @param access_token
	 * @param group_id
	 * @return
	 */
	public static BaseResultGroupID getGroupByiId(String access_token,String  group_id){
		String messageJson = "{\"group_id\":\""+group_id+"}";
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/merchant/group/getbyid")
				.addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
				.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResultGroupID.class);
	}
	
	/**
	 * ********************************************************
	 *	5.货架管理接口											  *
	 * ********************************************************
	 */
	/**
	 * ********************************************************
	 *	6.订单管理接口											  *
	 * ********************************************************
	 */
	/**
	 * ********************************************************
	 *	7.功能接口										  *
	 * ********************************************************
	 */
}
