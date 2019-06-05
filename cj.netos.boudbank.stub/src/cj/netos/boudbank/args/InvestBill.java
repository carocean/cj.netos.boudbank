package cj.netos.boudbank.args;

import java.math.BigDecimal;

public class InvestBill {
	String code;
	String depositor;
	BigDecimal amount;
	BigDecimal currBondPrice;
	BigDecimal newBondPrice;
	long dtime;
	BigDecimal tailAmount;
}
