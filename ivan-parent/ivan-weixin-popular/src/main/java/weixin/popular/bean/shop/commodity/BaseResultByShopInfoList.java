package weixin.popular.bean.shop.commodity;

import java.util.List;

/**
 * 返回接收类:List集合
 * @author 周立波
 *
 */
public class BaseResultByShopInfoList {
	private static final String SUCCESS_CODE = "0";
	private String errcode;
	private String errmsg;
	private List<Product_info> product_info;
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
	
	public List<Product_info> getProduct_info() {
		return product_info;
	}

	public void setProduct_info(List<Product_info> product_info) {
		this.product_info = product_info;
	}

	public boolean isSuccess() {
	    return errcode == null || errcode.isEmpty() || errcode.equals(SUCCESS_CODE);
	  }
}
