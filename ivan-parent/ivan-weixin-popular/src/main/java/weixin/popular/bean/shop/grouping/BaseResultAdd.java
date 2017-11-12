package weixin.popular.bean.shop.grouping;

/**
 * 微信请求状态数据
 *
 * @author zhoulibo
 */
public class BaseResultAdd {

	private static final String SUCCESS_CODE = "0";

	private String errcode;
	private String errmsg;
	private String group_id;

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
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
