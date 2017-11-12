package weixin.popular.bean.shop.postage;

/**
 * 微信请求状态数据
 * @author 周立波
 *
 */
public class BaseResultInfo {
	private static final String SUCCESS_CODE = "0";
	private String errcode;
	private String errmsg;
	private Template_info template_info;

	public Template_info getTemplate_info() {
		return template_info;
	}

	public void setTemplate_info(Template_info template_info) {
		this.template_info = template_info;
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
