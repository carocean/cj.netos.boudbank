package cj.netos.bondbank.args;

import java.math.BigDecimal;

public class InvestBill {
	String code;
	String invester;
	EInvesterType type;// 如果是消费者投白单，则等同于通过债券银行向金证银行购买债券，债券银行仅收取一定的手续费比率，不应用其它比率
	BigDecimal amount;// 投单金额
	long intime;
	BigDecimal merchantBondRate;// 对商户负债率
	BigDecimal customerBondRate;// 对消费者负债率
	BigDecimal feeBondRate;// 如果是消费者的白单，则商户银行收取的服务费率，提拥就不是从投单的现金中提而是从购得的债券按拥金比例提取给商家merchantBondQuantities，其余是消费者的customerBondQuantities
	BigDecimal merchantBondAmount;// 对商户负债金，也是商户以此金额向金证银行购入债券
	BigDecimal merchantSelfAmount;// 商户自有金，是买债余下的金额称为自有金，商家可随时提出
	BigDecimal customerBondAmount;// 对消费者负债金
	BigDecimal totalBondAmount;// 银行总负债
	// 以下在收到fsbank的通知后计算
	BigDecimal bondQuantities;// 购买到的债券数量
	BigDecimal merchantBondQuantities;// 商户分得的债券数量
	BigDecimal customerBondQuantities;// 消费者分得的债券数量
	BigDecimal bondFaceValue;// 债券面值
	BigDecimal bondPrice;// 当时买债时的债券价格
	BigDecimal tailAmount;// 尾金
	String informAddress;// 因为是异步的，而且买债也是异步的，所以将第一个异步通知地址记录下来
	BigDecimal bondQuantitiesBalance;//投单后余额
	public InvestBill() {
		type = EInvesterType.merchant;
	}
	public BigDecimal getBondQuantitiesBalance() {
		return bondQuantitiesBalance;
	}
	public void setBondQuantitiesBalance(BigDecimal bondQuantitiesBalance) {
		this.bondQuantitiesBalance = bondQuantitiesBalance;
	}
	public BigDecimal getMerchantBondQuantities() {
		return merchantBondQuantities;
	}

	public void setMerchantBondQuantities(BigDecimal merchantBondQuantities) {
		this.merchantBondQuantities = merchantBondQuantities;
	}

	public BigDecimal getCustomerBondQuantities() {
		return customerBondQuantities;
	}

	public void setCustomerBondQuantities(BigDecimal customerBondQuantities) {
		this.customerBondQuantities = customerBondQuantities;
	}

	public BigDecimal getMerchantSelfAmount() {
		return merchantSelfAmount;
	}

	public void setMerchantSelfAmount(BigDecimal merchantSelfAmount) {
		this.merchantSelfAmount = merchantSelfAmount;
	}

	public String getInformAddress() {
		return informAddress;
	}

	public void setInformAddress(String informAddress) {
		this.informAddress = informAddress;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInvester() {
		return invester;
	}

	public void setInvester(String invester) {
		this.invester = invester;
	}

	public EInvesterType getType() {
		return type;
	}

	public void setType(EInvesterType type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public long getIntime() {
		return intime;
	}

	public void setIntime(long intime) {
		this.intime = intime;
	}

	public BigDecimal getTailAmount() {
		return tailAmount;
	}

	public void setTailAmount(BigDecimal tailAmount) {
		this.tailAmount = tailAmount;
	}

	public BigDecimal getMerchantBondRate() {
		return merchantBondRate;
	}

	public void setMerchantBondRate(BigDecimal merchantBondRate) {
		this.merchantBondRate = merchantBondRate;
	}

	public BigDecimal getCustomerBondRate() {
		return customerBondRate;
	}

	public void setCustomerBondRate(BigDecimal customerBondRate) {
		this.customerBondRate = customerBondRate;
	}

	public BigDecimal getFeeBondRate() {
		return feeBondRate;
	}

	public void setFeeBondRate(BigDecimal feeBondRate) {
		this.feeBondRate = feeBondRate;
	}

	public BigDecimal getMerchantBondAmount() {
		return merchantBondAmount;
	}

	public void setMerchantBondAmount(BigDecimal merchantBondAmount) {
		this.merchantBondAmount = merchantBondAmount;
	}

	public BigDecimal getCustomerBondAmount() {
		return customerBondAmount;
	}

	public void setCustomerBondAmount(BigDecimal customerBondAmount) {
		this.customerBondAmount = customerBondAmount;
	}

	public BigDecimal getTotalBondAmount() {
		return totalBondAmount;
	}

	public void setTotalBondAmount(BigDecimal totalBondAmount) {
		this.totalBondAmount = totalBondAmount;
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

	public BigDecimal getBondPrice() {
		return bondPrice;
	}

	public void setBondPrice(BigDecimal bondPrice) {
		this.bondPrice = bondPrice;
	}

}
