package cj.netos.bondbank.bs;

import java.math.BigDecimal;
import java.util.List;

import cj.netos.bondbank.args.Balance;
import cj.netos.bondbank.args.BondBalanceStockBill;
import cj.netos.bondbank.args.FreeBalanceStockBill;

public interface IBDBankBalanceBS {

	Balance loadBalance(String bank);

	BigDecimal getMerchantDepositAmount(String bank);

	BigDecimal getMerchantBondQuantities(String bank);

	BigDecimal getConsumerBondQuantities(String bank);

	BigDecimal getFreeAmountBalance(String bank);

	BigDecimal getTailInFreeAmountBalance(String bank);

	BigDecimal getCommissionInFreeAmountBalance(String bank);

	List<BondBalanceStockBill> pageBondBalanceStock(String bank, int currPage, int pageSize);

	BigDecimal addMerchantBondQuantities(String bank, String merchant, String source, BigDecimal bondQuantities,
			BigDecimal faceValue);

	BigDecimal subtractMerchantBondQuantities(String bank, BigDecimal bondQuantities);

	BigDecimal addFreeAmount(String bank, String user, String source, String type, BigDecimal amount);

	List<FreeBalanceStockBill> pageFreeAmount(String bank, int currPage, int pageSize);

	BigDecimal subtractFreeAmount(String bank, BigDecimal amount);

}
