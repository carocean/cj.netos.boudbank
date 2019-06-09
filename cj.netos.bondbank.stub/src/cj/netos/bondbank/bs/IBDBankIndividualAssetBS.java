package cj.netos.bondbank.bs;

import java.math.BigDecimal;
import java.util.List;

import cj.netos.bondbank.args.BondToTYBill;
import cj.netos.bondbank.args.CashoutBill;
import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.args.InvestBill;

public interface IBDBankIndividualAssetBS {


	List<BondToTYBill> pageBondToTYBill(String bank, String user, int currPage, int pageSize);

	List<CashoutBill> pageCashoutBill(String bank, String user, int currPage, int pageSize);

	List<ExchangeBill> pageExchangeBill(String bank, String user, int currPage, int pageSize);

	List<InvestBill> pageInvestBill(String bank, String user, int currPage, int pageSize);

	BigDecimal totalChashoutBillAmount(String bank, String user);

	long depositBillCount(String bank, String depositor);

	long cashoutBillCount(String bank, String cashoutor, String identity);

	long exchangeBillCount(String bank, String exchanger);

	long bondToStockBillCount(String bank, String issuer);

	BigDecimal totalInvestBillAmount(String bank, String user);

	BigDecimal totalExchangeBillAmount(String bank, String user);


}
