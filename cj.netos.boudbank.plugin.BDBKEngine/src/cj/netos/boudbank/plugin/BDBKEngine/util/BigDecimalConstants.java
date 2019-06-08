package cj.netos.boudbank.plugin.BDBKEngine.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import cj.netos.boudbank.bs.IBDBankPropertiesBS;
import cj.netos.boudbank.plugin.BDBKEngine.args.BankProperty;
import cj.ultimate.util.StringUtil;

public interface BigDecimalConstants {
	static int scale = 16;// 小数位数为16
	static RoundingMode roundingMode = RoundingMode.FLOOR;
	static float default_merchantBondRate = 0.2F;
	static float default_customerBondRate = 1.0F;
	static float default_feeRate = 0.05F;
	
	default BigDecimal merchantBondRate(IBDBankPropertiesBS bdBankPropertiesBS, String bank) {
		String strbondRate = bdBankPropertiesBS.get(bank, BankProperty.CONSTANS_KEY_MERCHANTBONDRATE);
		if (StringUtil.isEmpty(strbondRate)) {
			strbondRate = default_merchantBondRate + "";
		}
		return new BigDecimal(strbondRate).setScale(scale, roundingMode);
	}
	default BigDecimal customerBondRate(IBDBankPropertiesBS bdBankPropertiesBS, String bank) {
		String strbondRate = bdBankPropertiesBS.get(bank, BankProperty.CONSTANS_KEY_CUSTOMERBONDRATE);
		if (StringUtil.isEmpty(strbondRate)) {
			strbondRate = default_customerBondRate + "";
		}
		return new BigDecimal(strbondRate).setScale(scale, roundingMode);
	}
	default BigDecimal feeRate(IBDBankPropertiesBS bdBankPropertiesBS, String bank) {
		String strbondRate = bdBankPropertiesBS.get(bank, BankProperty.CONSTANS_KEY_FEERATE);
		if (StringUtil.isEmpty(strbondRate)) {
			strbondRate = default_feeRate + "";
		}
		return new BigDecimal(strbondRate).setScale(scale, roundingMode);
	}
}
