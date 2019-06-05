package cj.netos.boudbank.args;

import java.math.BigDecimal;

public class CashoutBill {
	String code;
	String cashoutor;
	String identity;
	BigDecimal reqAmount;
	BigDecimal resAmount;//reqAmount=resAmount+poundageAmount
	long ctime;
	BigDecimal poundageRate;//手续费
	BigDecimal poundageAmount;
	BigDecimal balance;//余额，与balanceType有关
	String memo;
	String balanceType;
}
