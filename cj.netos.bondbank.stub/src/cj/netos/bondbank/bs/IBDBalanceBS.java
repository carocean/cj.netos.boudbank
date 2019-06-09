package cj.netos.bondbank.bs;

import cj.netos.bondbank.args.InvestBill;

public interface IBDBalanceBS {
	static String TABLE_BankBalance="balance.banks";
	static String TABLE_IndividualBalance="balance.individuals";
	static String TABLE_BondQuantities="stock.bondQuantities";
	public void onAddInvestBill(String bankno, InvestBill bill);
}
