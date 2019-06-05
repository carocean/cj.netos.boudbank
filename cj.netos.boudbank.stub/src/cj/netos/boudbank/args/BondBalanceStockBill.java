package cj.netos.boudbank.args;
//债券余额存量表

import java.math.BigDecimal;

public class BondBalanceStockBill {
	String id;
	String source;//投单流水表单号
	String merchant;//商户
	BigDecimal stock;//存量
	BigDecimal faceValue;//面值
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public BigDecimal getStock() {
		return stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	public BigDecimal getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	
	
}
