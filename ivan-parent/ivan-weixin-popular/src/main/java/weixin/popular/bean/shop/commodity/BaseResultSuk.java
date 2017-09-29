package weixin.popular.bean.shop.commodity;

import java.util.List;

/**
 * 返回接收类:SUK
 * 
 * @author 周立波
 *
 */
public class BaseResultSuk {
	private static final String SUCCESS_CODE = "0";
	private String errcode;
	private String errmsg;
	private sku_table sku_table;
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

	public sku_table getSku_table() {
		return sku_table;
	}

	public void setSku_table(sku_table sku_table) {
		this.sku_table = sku_table;
	}

	public class sku_table{
		private String id;
		private String name;
		private List<sku_table> value_list;
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
		public List<sku_table> getValue_list() {
			return value_list;
		}
		public void setValue_list(List<sku_table> value_list) {
			this.value_list = value_list;
		}
		
	}

	public boolean isSuccess() {
		return errcode == null || errcode.isEmpty() || errcode.equals(SUCCESS_CODE);
	}
}
