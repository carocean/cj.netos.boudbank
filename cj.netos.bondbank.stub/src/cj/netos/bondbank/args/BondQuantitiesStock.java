package cj.netos.bondbank.args;

import java.math.BigDecimal;

//入库流水单：InvestBill（有余额字段）,出库流水单：ExchangeBill（有余额字段）或IssueBill（有余额字段）,存量单：BondQuantitiesStock,存量交易流水表(拉链表）：BondQuantitiesStockTranscation,余额表：IndividualBalance
//债券出入库存量表。入库是增加记录，出库是删除和拆分记录
public class BondQuantitiesStock {
	String user;
	String source;// 投单号
	BigDecimal bondQuantities;// 债券存量是统计出来w
	BigDecimal bondFaceValue;// 面值
	BigDecimal balance;// 入存量库后债券余额
	long ctime;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
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

	public BigDecimal getBondQuantities() {
		return bondQuantities;
	}

	public void setBondQuantities(BigDecimal bondQuantities) {
		this.bondQuantities = bondQuantities;
	}

	public BigDecimal getBondFaceValue() {
		return bondFaceValue;
	}

	public void setBondFaceValue(BigDecimal bondFaceValue) {
		this.bondFaceValue = bondFaceValue;
	}

}
