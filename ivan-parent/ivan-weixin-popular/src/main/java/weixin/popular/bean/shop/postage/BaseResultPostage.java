package weixin.popular.bean.shop.postage;

/**
 * 微信请求状态数据
 *
 * @author LiYi
 */
public class BaseResultPostage {

  private static final String SUCCESS_CODE = "0";

  private String errcode;
  private String errmsg;
  private String template_id;
  
  public String getTemplate_id() {
	return template_id;
}

public void setTemplate_id(String template_id) {
	this.template_id = template_id;
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
