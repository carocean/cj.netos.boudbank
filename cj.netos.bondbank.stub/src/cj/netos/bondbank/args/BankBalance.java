package cj.netos.bondbank.args;

import java.math.BigDecimal;

public class BankBalance {
	BigDecimal bondQuantities;
	BigDecimal investBillAmount;
	public BigDecimal getBondQuantities() {
		return bondQuantities;
	}
	public void setBondQuantities(BigDecimal bondQuantities) {
		this.bondQuantities = bondQuantities;
	}
	public BigDecimal getInvestBillAmount() {
		return investBillAmount;
	}
	public void setInvestBillAmount(BigDecimal investBillAmount) {
		this.investBillAmount = investBillAmount;
	}
}
