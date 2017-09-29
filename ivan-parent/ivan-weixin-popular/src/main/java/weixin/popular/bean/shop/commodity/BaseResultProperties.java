package weixin.popular.bean.shop.commodity;

import java.util.List;


/**
 * 返回接收类:
 * 
 * @author 周立波
 *
 */
public class BaseResultProperties {
	private static final String SUCCESS_CODE = "0";
	private String errcode;
	private String errmsg;
	private List<properties> properties;
	
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public List<properties> getProperties() {
		return properties;
	}
	public void setProperties(List<properties> properties) {
		this.properties = properties;
	}
	public class properties{
		private String id;
		private String name;
		private List<property_value> property_value;
		public class property_value{
			private String id;
			private String name;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<property_value> getProperty_value() {
			return property_value;
		}
		public void setProperty_value(List<property_value> property_value) {
			this.property_value = property_value;
		}
		
	}
	public boolean isSuccess() {
		return errcode == null || errcode.isEmpty() || errcode.equals(SUCCESS_CODE);
	}
}
