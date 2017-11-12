package weixin.popular.bean.shop.commodity;
/**
 * 商品基本属性
 * @author 周立波
 *
 */
public class Base_attr {
	
	private String name;// 商品名字
	
	private String category;// 商品分类id
	
	private String main_img;// 商品主图：图片需调用图片上传接口获得图片Url填写至此，否则无法添加商品。图片分辨率推荐尺寸为640×600)
	
	private String img;// 商品图片列表(图片需调用图片上传接口获得图片Url填写至此，否则无法添加商品。图片分辨率推荐尺寸为640×600)
	
	private detail detail;//商品详情列表，显示在客户端的商品详情页内
	
	private property property;//商品属性列表，属性列表请通过《获取指定分类的所有属性》获取
	
	private sku_info sku_info;//商品sku定义，SKU列表请通过《获取指定子分类的所有SKU》获取
	
	private String buy_limit;//用户商品限购数量
	
	public class detail{
		private String text;//文字描述
		private String img;//图片(图片需调用图片上传接口获得图片Url填写至此，否则无法添加商品)
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
		
	}
	public class property{
		private String id;//属性ID
		private String vid;//属性值ID
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getVid() {
			return vid;
		}
		public void setVid(String vid) {
			this.vid = vid;
		}
		
	}
	public class sku_info{
		private String id;//ku属性(SKU列表中id, 支持自定义SKU，格式为"$xxx"，xxx即为显示在客户端中的字符串)
		private String vid;//sku值(SKU列表中vid, 如需自定义SKU，格式为"$xxx"，xxx即为显示在客户端中的字符串)
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getVid() {
			return vid;
		}
		public void setVid(String vid) {
			this.vid = vid;
		}
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMain_img() {
		return main_img;
	}
	public void setMain_img(String main_img) {
		this.main_img = main_img;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public detail getDetail() {
		return detail;
	}
	public void setDetail(detail detail) {
		this.detail = detail;
	}
	public property getProperty() {
		return property;
	}
	public void setProperty(property property) {
		this.property = property;
	}
	public sku_info getSku_info() {
		return sku_info;
	}
	public void setSku_info(sku_info sku_info) {
		this.sku_info = sku_info;
	}
	public String getBuy_limit() {
		return buy_limit;
	}
	public void setBuy_limit(String buy_limit) {
		this.buy_limit = buy_limit;
	}
	
}
