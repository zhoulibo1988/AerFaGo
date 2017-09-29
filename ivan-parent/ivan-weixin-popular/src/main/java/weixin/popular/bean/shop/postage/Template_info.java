package weixin.popular.bean.shop.postage;

import java.util.List;

/**
 * 邮件模板详情
 * 
 * @author 周立波
 *
 */
public class Template_info {
	private String Id;
	// 邮费模板名称
	private String Name;
	// 支付方式(0-买家承担运费, 1-卖家承担运费)
	private String Assumer;
	// 计费单位(0-按件计费, 1-按重量计费, 2-按体积计费，目前只支持按件计费，默认为0)
	private String Valuation;
	// 具体运费计算
	private List<TopFee> TopFee;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

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

	public List<TopFee> getTopFee() {
		return TopFee;
	}

	public void setTopFee(List<TopFee> topFee) {
		TopFee = topFee;
	}
}
