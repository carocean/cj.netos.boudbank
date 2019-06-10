package cj.netos.bondbank.bs;

import java.math.BigDecimal;
import java.util.Map;

import cj.netos.bondbank.args.EInvesterType;

public interface IBDBankTransactionBS {
	static String TABLE_Invests = "invests";
	static String TABLE_Exchanges = "exchanges";
	static String TABLE_Cashouts = "cashouts";
	
	Map<String, Object> cashoutBill(String bank, String cashoutor, BigDecimal amount, String memo);

	void investBill(String bank, String invester, BigDecimal amount, EInvesterType type, String informAddress);

	void exchangeBill(String bank, String exchanger, BigDecimal bondQuantities, String informAddress);

	void issueStockBill(String v, String issuer, BigDecimal bondQuantities, String informAddress);

}
