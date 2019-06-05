package cj.netos.boudbank.bs;

import java.math.BigDecimal;
import java.util.List;

import cj.netos.boudbank.args.BondToTYBill;
import cj.netos.boudbank.args.CashoutBill;
import cj.netos.boudbank.args.ExchangeBill;
import cj.netos.boudbank.args.InvestBill;

public interface IBDBankIndividualAssetBS {

	BigDecimal boudBalance(String bank, String user);

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
