package cj.netos.boudbank.stub;

import java.math.BigDecimal;

import cj.netos.boudbank.args.EInvesterType;
import cj.studio.gateway.stub.annotation.CjStubInParameter;
import cj.studio.gateway.stub.annotation.CjStubMethod;
import cj.studio.gateway.stub.annotation.CjStubService;

@CjStubService(bindService = "/transaction.service", usage = "债券银行交易事务")
public interface IBDBankTransactionStub {
	@CjStubMethod(usage = "投资")
	void invest(@CjStubInParameter(key = "bank", usage = "银行标识") String bank,
			@CjStubInParameter(key = "investor", usage = "投资人") String investor,
			@CjStubInParameter(key = "amount", usage = "金额") BigDecimal amount,
			@CjStubInParameter(key = "type", usage = "投单者类型") EInvesterType type,
			@CjStubInParameter(key = "informAddress", usage = "通知地址") String informAddress);

	@CjStubMethod(usage = "提现")
	void cashout(@CjStubInParameter(key = "bank", usage = "银行标识") String bank,
			@CjStubInParameter(key = "balanceType", usage = "提现的余额类型，只有三种：余额表的商户存款余额、自由金余额和尾金余额，分别是：companyBalance,freeBalance，tailBalance") String balanceType,
			@CjStubInParameter(key = "cashoutor", usage = "提现人") String cashoutor,
			@CjStubInParameter(key = "identity", usage = "提现人身份") String identity,
			@CjStubInParameter(key = "reqAmount", usage = "请求金额") BigDecimal reqAmount,
			@CjStubInParameter(key = "memo", usage = "备注") String memo,
			@CjStubInParameter(key = "informAddress", usage = "通知地址") String informAddress);

	@CjStubMethod(usage = "承兑债券得到现金")
	void exchange(@CjStubInParameter(key = "bank", usage = "银行标识") String bank,
			@CjStubInParameter(key = "exchanger", usage = "承兑人") String exchanger,
			@CjStubInParameter(key = "bondQuantities", usage = "承兑数量，单位来自于银行的拆单规则表") BigDecimal bondQuantities,
			@CjStubInParameter(key = "informAddress", usage = "通知地址") String informAddress);

	@CjStubMethod(usage = "发行帑银，将债券按债率和市盈率计算总负债金额，再由总负债金额投放到帑银交易市场，并由市场转换为帑银并发行。")
	void issueStock(@CjStubInParameter(key = "bank", usage = "银行标识") String bank,
			@CjStubInParameter(key = "issuer", usage = "发行人，一般是银行行长或银行自身。它发行的是债券银行商户的债券，因为一个银行只为一个商户服务") String issuer,
			@CjStubInParameter(key = "bondQuantities", usage = "要转换的债券数量") BigDecimal bondQuantities,
			@CjStubInParameter(key = "informAddress", usage = "通知地址") String informAddress);
}
