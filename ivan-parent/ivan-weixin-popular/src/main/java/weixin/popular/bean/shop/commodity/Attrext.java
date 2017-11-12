package weixin.popular.bean.shop.commodity;
/**
 * 商品其他属性
 * 非必填项
 * @author 周立波
 *
 */
public class Attrext {
	//是否包邮(0-否, 1-是), 如果包邮delivery_info字段可省略
	private String isPostFree;
	//是否提供发票(0-否, 1-是)
	private String isHasReceipt;
	//是否保修(0-否, 1-是)
	private String isUnderGuaranty;
	//是否支持退换货(0-否, 1-是)
	private String isSupportReplace;
	//商品所在地地址
	private location location;
	
	public class location{
		//国家(详见《地区列表》说明)
		private String country;
		//省份(详见《地区列表》说明)
		private String province;
		//城市(详见《地区列表》说明)
		private String city;
		//地址
		private String address;
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		
	}

	public String getIsPostFree() {
		return isPostFree;
	}

	public void setIsPostFree(String isPostFree) {
		this.isPostFree = isPostFree;
	}

	public String getIsHasReceipt() {
		return isHasReceipt;
	}

	public void setIsHasReceipt(String isHasReceipt) {
		this.isHasReceipt = isHasReceipt;
	}

	public String getIsUnderGuaranty() {
		return isUnderGuaranty;
	}

	public void setIsUnderGuaranty(String isUnderGuaranty) {
		this.isUnderGuaranty = isUnderGuaranty;
	}

	public String getIsSupportReplace() {
		return isSupportReplace;
	}

	public void setIsSupportReplace(String isSupportReplace) {
		this.isSupportReplace = isSupportReplace;
	}

	public location getLocation() {
		return location;
	}

	public void setLocation(location location) {
		this.location = location;
	}
	
}
