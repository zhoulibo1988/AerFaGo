package weixin.popular.bean.shop.commodity;
/**
 * sku信息列表(可为多个)，每个sku信息串即为一个确定的商品，比如白色的37码的鞋子
 * 非必填项
 * @author 周立波
 *
 */
public class Sku_list {
	//sku信息, 参照上述sku_table的定义; 格式 : "id1:vid1;id2:vid2" 规则 : id_info的组合个数必须与sku_table个数一致(若商品无sku信息, 即商品为统一规格，则此处赋值为空字符串即可)
	private String sku_id;
	//原价：sku原价(单位 : 分)
	private String ori_price;
	//sku微信价(单位 : 分, 微信价必须比原价小, 否则添加商品失败)
	private String price;
	//sku iconurl(图片需调用图片上传接口获得图片Url)
	private String icon_url;
	//sku库存
	private String quantity;
	//商家商品编码
	private String product_code;
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getOri_price() {
		return ori_price;
	}
	public void setOri_price(String ori_price) {
		this.ori_price = ori_price;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	
}
