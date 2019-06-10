package cj.netos.bondbank.bs;

import java.math.BigDecimal;

import cj.netos.bondbank.args.EInvesterType;

public interface IBDBankTransactionBS {
	static String TABLE_Invests = "invests";
	static String TABLE_Exchanges = "exchanges";
	void cashoutBill(String key, String balanceType, String cashoutor, String identity,
			BigDecimal reqAmount, String memo, String informAddress);

	void  investBill(String key, String invester, BigDecimal amount, EInvesterType type,
			String informAddress);

	void  exchangeBill(String key, String exchanger, BigDecimal bondQuantities, String informAddress);

	void  issueStockBill(String key, String issuer, BigDecimal bondQuantities, String informAddress);

}
