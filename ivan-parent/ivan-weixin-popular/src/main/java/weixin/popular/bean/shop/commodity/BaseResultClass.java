package weixin.popular.bean.shop.commodity;

import java.util.List;

/**
 * 返回接收类
 * 
 * @author 周立波
 *
 */
public class BaseResultClass {
	private static final String SUCCESS_CODE = "0";
	private String errcode;
	private String errmsg;
	private List<Classification> cate_list;

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

	public List<Classification> getCate_list() {
		return cate_list;
	}

	public void setCate_list(List<Classification> cate_list) {
		this.cate_list = cate_list;
	}

	public boolean isSuccess() {
		return errcode == null || errcode.isEmpty() || errcode.equals(SUCCESS_CODE);
	}
}
