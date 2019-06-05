package cj.netos.boudbank.stub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cj.netos.boudbank.args.Balance;
import cj.netos.boudbank.args.BondBalanceStockBill;
import cj.netos.boudbank.args.FreeBalanceStockBill;
import cj.studio.gateway.stub.annotation.CjStubInParameter;
import cj.studio.gateway.stub.annotation.CjStubMethod;
import cj.studio.gateway.stub.annotation.CjStubReturn;
import cj.studio.gateway.stub.annotation.CjStubService;

@CjStubService(bindService = "/balance.service", usage = "债券银行余额")
public interface IBDBankBalanceStub {
	@CjStubMethod(usage = "加载余额")
	@CjStubReturn(usage = "返回余额表", type = Balance.class)
	Balance loadBalance(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "商户存入余额")
	BigDecimal getMerchantDepositAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "商户债券余额")
	BigDecimal getMerchantBondQuantities(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "消费者债券余额")
	BigDecimal getConsumerBondQuantities(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "自由金余额")
	BigDecimal getFreeAmountBalance(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "自由金中包含的尾金余额，从自由金交易事务表中动态统计")
	BigDecimal getTailInFreeAmountBalance(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "自由金中包含的拥金余额，从自由金交易事务表中动态统计")
	BigDecimal getCommissionInFreeAmountBalance(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "扣减自由金余额")
	@CjStubReturn(usage = "返回扣减后的自由金余额")
	BigDecimal subtractFreeAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "amount", usage = "自由金余额") BigDecimal amount);

	@CjStubMethod(usage = "增加自由金余额")
	@CjStubReturn(usage = "返回增加后的自由金余额")
	BigDecimal addFreeAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "user", usage = "用户") String user,
			@CjStubInParameter(key = "source", usage = "来源：来源于手续费对应的单号、尾金对应的单号，这由类型来识别") String source,
			@CjStubInParameter(key = "type", usage = "尾金、手续费") String type,
			@CjStubInParameter(key = "amount", usage = "自由金余额") BigDecimal amount);

	@CjStubMethod(usage = "按页获取自由金存量")
	@CjStubReturn(usage = "一页数", elementType = FreeBalanceStockBill.class, type = ArrayList.class)
	List<FreeBalanceStockBill> pageFreeAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "currPage", usage = "当前页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "页大小") int pageSize);

	@CjStubMethod(usage = "扣减商户债券余额")
	@CjStubReturn(usage = "返回扣减后的债券余额")
	BigDecimal subtractMerchantBondQuantities(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "bondQuantities", usage = "商户债券余额") BigDecimal bondQuantities);

	@CjStubMethod(usage = "增加商户债券余额")
	@CjStubReturn(usage = "返回增加后的债券余额")
	BigDecimal addMerchantBondQuantities(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "merchant", usage = "商户") String merchant,
			@CjStubInParameter(key = "source", usage = "来源：投单流水表单号") String source,
			@CjStubInParameter(key = "bondQuantities", usage = "商户债券余额") BigDecimal bondQuantities,
			@CjStubInParameter(key = "faceValue", usage = "债券面值") BigDecimal faceValue);

	@CjStubMethod(usage = "按页获取商户债券余额存量")
	@CjStubReturn(usage = "一页数", elementType = BondBalanceStockBill.class, type = ArrayList.class)
	List<BondBalanceStockBill> pageBondBalanceStock(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "currPage", usage = "当前页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "页大小") int pageSize);
}
