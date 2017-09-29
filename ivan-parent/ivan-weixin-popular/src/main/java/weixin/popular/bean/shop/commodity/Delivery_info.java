package weixin.popular.bean.shop.commodity;
/**
 * 	商品运费信息
 * 必选
 * @author 周立波
 *
 */
public class Delivery_info {
	//运费类型(0-使用下面express字段的默认模板, 1-使用template_id代表的邮费模板, 详见邮费模板相关API)
	private String delivery_type;
	//邮费模板ID
	private String template_id;
	//快递公司
	private express express;
	
	public class express{
		//快递ID
		private String id;
		//运费：分
		private String price;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		
	}

	public String getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public express getExpress() {
		return express;
	}

	public void setExpress(express express) {
		this.express = express;
	}
	
}
