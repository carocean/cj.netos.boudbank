package cj.netos.boudbank.args;

import java.math.BigDecimal;

public class FreeBalanceStockBill {
	String id;
	String user;
	String source;
	String type;//尾金、手续费
	BigDecimal stock;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getStock() {
		return stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	
}
