package cj.netos.boudbank.bs;

import java.math.BigDecimal;
import java.util.List;

import cj.netos.boudbank.args.BondToTYBill;
import cj.netos.boudbank.args.CashoutBill;
import cj.netos.boudbank.args.ExchangeBill;
import cj.netos.boudbank.args.InvestBill;

public interface IBDBankAssetBS {

	long investBillCount(String bank);

	long cashBillCount(String bank);

	long exchangeBillCount(String bank);

	BigDecimal totalInvestBillAmount(String bank);

	BigDecimal totalLiabilitiesAmount(String bank);

	BigDecimal totalBuyBondAmount(String bank);

	BigDecimal totalConsumerBondQuantities(String bank);

	BigDecimal totalMerchantBondQuantities(String bank);

	List<InvestBill> pageInvestBill(String bank);

	List<CashoutBill> pageCashoutBill(String bank);

	List<ExchangeBill> pageExchangeBill(String bank);

	List<BondToTYBill> pageBondToStockBill(String bank);

}
