package cj.netos.bondbank.args;

import java.math.BigDecimal;

public class Balance {
	BigDecimal merchantDepositAmount;//商户存入余额
	BigDecimal merchantBondQuantities;//商户债券数量，对应交易事务表
	BigDecimal consumerBondQuantities;//消费者债券余额
	BigDecimal freeAmount;//自由金余额有尾金（tail）、手续费（commission），对应交易事务表
	public BigDecimal getMerchantDepositAmount() {
		return merchantDepositAmount;
	}
	public void setMerchantDepositAmount(BigDecimal merchantDepositAmount) {
		this.merchantDepositAmount = merchantDepositAmount;
	}
	public BigDecimal getMerchantBondQuantities() {
		return merchantBondQuantities;
	}
	public void setMerchantBondQuantities(BigDecimal merchantBondQuantities) {
		this.merchantBondQuantities = merchantBondQuantities;
	}
	public BigDecimal getConsumerBondQuantities() {
		return consumerBondQuantities;
	}
	public void setConsumerBondQuantities(BigDecimal consumerBondQuantities) {
		this.consumerBondQuantities = consumerBondQuantities;
	}
	public BigDecimal getFreeAmount() {
		return freeAmount;
	}
	public void setFreeAmount(BigDecimal freeAmount) {
		this.freeAmount = freeAmount;
	}

}
