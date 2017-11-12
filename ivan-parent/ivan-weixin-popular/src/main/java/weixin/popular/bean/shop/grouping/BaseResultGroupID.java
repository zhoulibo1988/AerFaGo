package weixin.popular.bean.shop.grouping;


/**
 * 微信请求状态数据
 *
 * @author zhoulibo
 */
public class BaseResultGroupID {

	private static final String SUCCESS_CODE = "0";

	private String errcode;
	private String errmsg;
	private Group_detail group_detail;
	

	public Group_detail getGroup_detail() {
		return group_detail;
	}

	public void setGroup_detail(Group_detail group_detail) {
		this.group_detail = group_detail;
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
