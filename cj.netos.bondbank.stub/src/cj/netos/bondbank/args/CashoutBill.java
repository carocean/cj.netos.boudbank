package cj.netos.bondbank.args;

import java.math.BigDecimal;

public class CashoutBill {
	String code;
	String cashoutor;
	BigDecimal amount;//请求金额，要么提走，要么失败，因此不存在什么请求金、晌应金之说。
	BigDecimal balance;//余额
	String memo;
	long ctime;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCashoutor() {
		return cashoutor;
	}
	public void setCashoutor(String cashoutor) {
		this.cashoutor = cashoutor;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
}
