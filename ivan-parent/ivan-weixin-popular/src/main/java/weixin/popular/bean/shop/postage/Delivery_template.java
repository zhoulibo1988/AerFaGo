package weixin.popular.bean.shop.postage;

/**
 * 邮费
 * @author 周立波
 *
 */
public class Delivery_template {
	// 邮费模板名称
	private String Name;
	// 支付方式(0-买家承担运费, 1-卖家承担运费)
	private String Assumer;
	// 计费单位(0-按件计费, 1-按重量计费, 2-按体积计费，目前只支持按件计费，默认为0)
	private String Valuation;
	// 具体运费计算
	private TopFee TopFee;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAssumer() {
		return Assumer;
	}

	public void setAssumer(String assumer) {
		Assumer = assumer;
	}

	public String getValuation() {
		return Valuation;
	}

	public void setValuation(String valuation) {
		Valuation = valuation;
	}

	public TopFee getTopFee() {
		return TopFee;
	}

	public void setTopFee(TopFee topFee) {
		TopFee = topFee;
	}
}
