package cj.netos.bondbank.args;

import java.math.BigDecimal;

public class IndividualBalance {
	String user;
	BigDecimal freeAmount;//现金余额
	BigDecimal bondQuantities;//债券余额
	public BigDecimal getBondQuantities() {
		return bondQuantities;
	}
	public void setBondQuantities(BigDecimal bondQuantities) {
		this.bondQuantities = bondQuantities;
	}
	public BigDecimal getFreeAmount() {
		return freeAmount;
	}
	public void setFreeAmount(BigDecimal freeAmount) {
		this.freeAmount = freeAmount;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
