package cj.netos.bondbank.bs;

import java.math.BigDecimal;

import cj.netos.bondbank.args.InvestBill;

public interface IBDBankInvestBillBS {

	InvestBill getBill(String bank, String billno);

	void updateBondQuantities(String bank,String billno,BigDecimal bondQuantities);

	void updateBondFaceValue(String bank,String billno,BigDecimal bondFaceValue);

	void updateMerchantBondQuantities(String bank,String billno,BigDecimal merchantBondQuantities);

	void updateCustomerBondQuantities(String bank,String billno,BigDecimal customerBondQuantities);

	void updateBondPrice(String bank, String billno, BigDecimal bondPrice);

}
