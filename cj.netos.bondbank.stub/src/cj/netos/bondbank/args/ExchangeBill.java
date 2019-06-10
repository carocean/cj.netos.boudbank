package cj.netos.bondbank.args;

import java.math.BigDecimal;

public class ExchangeBill {
	String code;
	String exchanger;
	BigDecimal bondQuantities;
	BigDecimal bondPrice;
	BigDecimal deservedAmount;//得金
	long etime;
	String source;//来源于fsbank的承兑单号
	long dealTime;
	public long getDealTime() {
		return dealTime;
	}
	public void setDealTime(long dealTime) {
		this.dealTime = dealTime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getExchanger() {
		return exchanger;
	}
	public void setExchanger(String exchanger) {
		this.exchanger = exchanger;
	}
	public BigDecimal getBondQuantities() {
		return bondQuantities;
	}
	public void setBondQuantities(BigDecimal bondQuantities) {
		this.bondQuantities = bondQuantities;
	}
	public BigDecimal getBondPrice() {
		return bondPrice;
	}
	public void setBondPrice(BigDecimal bondPrice) {
		this.bondPrice = bondPrice;
	}
	public BigDecimal getDeservedAmount() {
		return deservedAmount;
	}
	public void setDeservedAmount(BigDecimal deservedAmount) {
		this.deservedAmount = deservedAmount;
	}
	public long getEtime() {
		return etime;
	}
	public void setEtime(long etime) {
		this.etime = etime;
	}
	
	
}
