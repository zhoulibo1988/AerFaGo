package weixin.popular.bean.shop.commodity;
/**
 * 返回接收类
 * @author 周立波
 *
 */
public class BaseResultByShop {
	private static final String SUCCESS_CODE = "0";

	private String errcode;
	private String errmsg;
	//商品ID
	private String product_id;

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

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public boolean isSuccess() {
	    return errcode == null || errcode.isEmpty() || errcode.equals(SUCCESS_CODE);
	  }
}
