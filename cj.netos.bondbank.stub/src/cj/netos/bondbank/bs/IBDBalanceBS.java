package cj.netos.bondbank.bs;

import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.args.IndividualBalance;
import cj.netos.bondbank.args.InvestBill;

public interface IBDBalanceBS {
	static String TABLE_BankBalance="balance.banks";
	static String TABLE_IndividualBalance="balance.individuals";
	static String TABLE_BondQuantities="stock.bondQuantities";
	public void onAddInvestBill(String bankno, InvestBill bill);
	IndividualBalance getIndividualBalance(String bank, String user);
	public void onAddExchangeBill(String bankno, ExchangeBill bill);
}
