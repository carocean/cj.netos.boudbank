package cj.netos.boudbank.bs;

import java.math.BigDecimal;
import java.util.Map;

import cj.netos.boudbank.args.EInvesterType;

public interface IBDBankTransactionBS {

	Map<String,Object> cashoutBill(String key, String balanceType, String cashoutor, String identity, BigDecimal reqAmount,
			String memo,String informAddress);

	Map<String,Object> investBill(String key, String invester, BigDecimal amount,EInvesterType type,String informAddress);

	Map<String,Object> exchangeBill(String key, String exchanger, BigDecimal bondQuantities,String informAddress);

	Map<String,Object> issueStockBill(String key, String issuer, BigDecimal bondQuantities,String informAddress);

}
