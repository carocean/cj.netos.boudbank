package cj.netos.boudbank.stub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cj.netos.boudbank.args.BondToTYBill;
import cj.netos.boudbank.args.CashoutBill;
import cj.netos.boudbank.args.ExchangeBill;
import cj.netos.boudbank.args.InvestBill;
import cj.studio.gateway.stub.annotation.CjStubInParameter;
import cj.studio.gateway.stub.annotation.CjStubMethod;
import cj.studio.gateway.stub.annotation.CjStubReturn;
import cj.studio.gateway.stub.annotation.CjStubService;

@CjStubService(bindService = "/asset/bank.service", usage = "债券银行资产")
public interface IBDBankAssetStub {
	@CjStubMethod(usage = "投单总数")
	long investBillCount(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "提现单总数")
	long cashBillCount(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "承兑单总数")
	long exchangeBillCount(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "统计商户投单总金额")
	BigDecimal totalInvestBillAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "统计银行总负债金额")
	BigDecimal totalLiabilitiesAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "统计商户用于买债券的资金总金额")
	BigDecimal totalBuyBondAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "统计商户持有债券总量")
	BigDecimal totalMerchantBondQuantities(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "统计消费者持有债券总量")
	BigDecimal totalConsumerBondQuantities(@CjStubInParameter(key = "bank", usage = "银行") String bank);

	@CjStubMethod(usage = "投单分页")
	@CjStubReturn(type = ArrayList.class, elementType = InvestBill.class, usage = "列表")
	List<InvestBill> pageInvestBill(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "currPage", usage = "当前分页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "分页大小") int pageSize);

	@CjStubMethod(usage = "提现单分页")
	@CjStubReturn(type = ArrayList.class, elementType = CashoutBill.class, usage = "列表")
	List<CashoutBill> pageCashoutBill(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "currPage", usage = "当前分页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "分页大小") int pageSize);

	@CjStubMethod(usage = "承兑单分页")
	@CjStubReturn(type = ArrayList.class, elementType = ExchangeBill.class, usage = "列表")
	List<ExchangeBill> pageExchangeBill(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "currPage", usage = "当前分页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "分页大小") int pageSize);
	
	@CjStubMethod(usage = "债转股流水分页")
	@CjStubReturn(type = ArrayList.class, elementType = BondToTYBill.class, usage = "列表")
	List<BondToTYBill> pageBondToStockBill(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "currPage", usage = "当前分页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "分页大小") int pageSize);
}
