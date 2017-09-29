package weixin.popular.bean.shop.postage;

import java.util.List;

/**
 * 微信请求状态数据
 * @author 周立波
 *
 */
public class BaseResultInfoAll {
	private static final String SUCCESS_CODE = "0";
	private String errcode;
	private String errmsg;
	private List<Template_info> template_info;
	public List<Template_info> getTemplate_info() {
		return template_info;
	}

	public void setTemplate_info(List<Template_info> template_info) {
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
