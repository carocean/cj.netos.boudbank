package cj.netos.bondbank.bs;

import java.math.BigDecimal;

import cj.netos.bondbank.args.BankBalance;
import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.args.IndividualBalance;
import cj.netos.bondbank.args.InvestBill;

public interface IBDBalanceBS {
	static String TABLE_BankBalance = "balance.banks";
	static String TABLE_IndividualBalance = "balance.individuals";
	static String TABLE_Stock_BondQuantities = "stock.bondQuantities";

	public void onAddInvestBill(String bankno, InvestBill bill);

	public void onAddExchangeBill(String bankno, ExchangeBill bill);

	IndividualBalance getIndividualBalance(String bank, String user);

	BigDecimal getIndividualBondQuantitiesBalance(String bankno, String user);

	void addIndividualCashAmount(String bankno, String user, BigDecimal deservedAmount);

	BigDecimal getIndividualCashAmountBalance(String bankno, String user);

	void decIndividualBondQuantities(String bankno, String user, BigDecimal bondQuantities);

	void decBankBondQuantities(String bankno, BigDecimal bondQuantities);

	BankBalance getBankBalance(String bank);

	BigDecimal getBankBondQuantitiesBalance(String bank);
}
