package cj.netos.bondbank.args;

import java.math.BigDecimal;

//拉链表：存量交易流水单。以
public class BondQuantitiesStockTransaction {
	String code;
	String user;// 用户
	String inbillno;// 入库单编号：指investBill
	String outbillno;// 出库单编号：指ExchangeBill或IssueBill
	BigDecimal quantities;// 出库成交量
	long ctime;// 出库成交时间

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInbillno() {
		return inbillno;
	}

	public void setInbillno(String inbillno) {
		this.inbillno = inbillno;
	}

	public String getOutbillno() {
		return outbillno;
	}

	public void setOutbillno(String outbillno) {
		this.outbillno = outbillno;
	}

	public BigDecimal getQuantities() {
		return quantities;
	}

	public void setQuantities(BigDecimal quantities) {
		this.quantities = quantities;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

}
