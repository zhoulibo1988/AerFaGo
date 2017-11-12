package weixin.popular.bean.shop.postage;
/**
 * 具体运费计算类
 * @author 周立波
 *
 */
public class TopFee {
	// 例子解析：Type为10000027的默认邮费计算，第1件邮费2分，每增加3件邮费增加3分。Type为10000027的指定地区邮费计算，寄送到中国/广东省/广州市的商品，第一件邮费1元，每增加1件邮费增加3分。
//	快递类型ID(参见增加商品/快递列表)
	private String Type;
//	// 默认邮费计算方法
	private Normal Normal;
//	指定地区邮费计算方法
	private Custom Custom;
	// 默认邮费计算方法
	public class Normal {
		// 起始计费数量(比如计费单位是按件, 填2代表起始计费为2件)
		private String StartStandards;
		// 起始计费金额(单位: 分）
		private String StartFees;
		// 增计费数量
		private String AddStandards;
		// 递增计费金额(单位 : 分)
		private String AddFees;

		public String getStartStandards() {
			return StartStandards;
		}

		public void setStartStandards(String startStandards) {
			StartStandards = startStandards;
		}

		public String getStartFees() {
			return StartFees;
		}

		public void setStartFees(String startFees) {
			StartFees = startFees;
		}

		public String getAddStandards() {
			return AddStandards;
		}

		public void setAddStandards(String addStandards) {
			AddStandards = addStandards;
		}

		public String getAddFees() {
			return AddFees;
		}

		public void setAddFees(String addFees) {
			AddFees = addFees;
		}

	}

	// 指定地区邮费计算方法
	public class Custom {
		// 起始计费数量
		private String StartStandards;
		// 起始计费金额(单位: 分）
		private String StartFees;
		// 递增计费数量
		private String AddStandards;
		// 递增计费金额(单位 : 分)
		private String AddFees;
		// 指定国家(详见《地区列表》说明)
		private String DestCountry;
		// 指定省份(详见《地区列表》说明)
		private String DestProvince;
		// 指定城市(详见《地区列表》说明)
		private String DestCity;

		public String getStartStandards() {
			return StartStandards;
		}

		public void setStartStandards(String startStandards) {
			StartStandards = startStandards;
		}

		public String getStartFees() {
			return StartFees;
		}

		public void setStartFees(String startFees) {
			StartFees = startFees;
		}

		public String getAddStandards() {
			return AddStandards;
		}

		public void setAddStandards(String addStandards) {
			AddStandards = addStandards;
		}

		public String getAddFees() {
			return AddFees;
		}

		public void setAddFees(String addFees) {
			AddFees = addFees;
		}

		public String getDestCountry() {
			return DestCountry;
		}

		public void setDestCountry(String destCountry) {
			DestCountry = destCountry;
		}

		public String getDestProvince() {
			return DestProvince;
		}

		public void setDestProvince(String destProvince) {
			DestProvince = destProvince;
		}

		public String getDestCity() {
			return DestCity;
		}

		public void setDestCity(String destCity) {
			DestCity = destCity;
		}

	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public Normal getNormal() {
		return Normal;
	}

	public void setNormal(Normal normal) {
		Normal = normal;
	}

	public Custom getCustom() {
		return Custom;
	}

	public void setCustom(Custom custom) {
		Custom = custom;
	}

}
