package weixin.popular.bean.shop.grouping;

import java.util.List;

/**
 * 微信请求状态数据
 *
 * @author zhoulibo
 */
public class BaseResultGroupInfo {

	private static final String SUCCESS_CODE = "0";

	private String errcode;
	private String errmsg;
	private List<Groups_detail> groups_detail;
	

	public List<Groups_detail> getGroups_detail() {
		return groups_detail;
	}

	public void setGroups_detail(List<Groups_detail> groups_detail) {
		this.groups_detail = groups_detail;
	}

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

	public boolean isSuccess() {
		return errcode == null || errcode.isEmpty() || errcode.equals(SUCCESS_CODE);
	}

}
