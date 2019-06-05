package cj.netos.boudbank.bs;

import java.math.BigDecimal;

public interface IBDBankTransactionBS {

	void cashoutBill(String key, String balanceType, String cashoutor, String identity, BigDecimal reqAmount,
			String memo);

	void investBill(String key, String depositor, BigDecimal amount);

	void exchangeBill(String key, String exchanger, BigDecimal bondQuantities);

	void issueStockBill(String key, String issuer, BigDecimal bondQuantities);

}
