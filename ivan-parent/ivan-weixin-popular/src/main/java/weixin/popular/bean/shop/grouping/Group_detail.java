package weixin.popular.bean.shop.grouping;

import java.util.List;

/**
 * 分组
 * @author 周立波
 *
 */
public class Group_detail {
	//id
	private String group_id;
	//分组名字
	private String group_name;
	//分组ID
	private List<String> product_list;
	
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public List<String> getProduct_list() {
		return product_list;
	}
	public void setProduct_list(List<String> product_list) {
		this.product_list = product_list;
	}
	
}
