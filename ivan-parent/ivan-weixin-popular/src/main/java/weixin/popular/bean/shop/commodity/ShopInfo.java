package weixin.popular.bean.shop.commodity;

public class ShopInfo {
	//基本属性 必填
	private Base_attr base_attr;
	//sku信息列表(可为多个) 非必填
	private Sku_list sku_list;
	//商品其他属性 非必填
	private Attrext attrext;
	//商品运费信息 必填
	private Delivery_info delivery_info;
	public Base_attr getBase_attr() {
		return base_attr;
	}
	public void setBase_attr(Base_attr base_attr) {
		this.base_attr = base_attr;
	}
	public Sku_list getSku_list() {
		return sku_list;
	}
	public void setSku_list(Sku_list sku_list) {
		this.sku_list = sku_list;
	}
	public Attrext getAttrext() {
		return attrext;
	}
	public void setAttrext(Attrext attrext) {
		this.attrext = attrext;
	}
	public Delivery_info getDelivery_info() {
		return delivery_info;
	}
	public void setDelivery_info(Delivery_info delivery_info) {
		this.delivery_info = delivery_info;
	}
	
}
