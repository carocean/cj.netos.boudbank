package cj.netos.bondbank.stub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cj.netos.bondbank.args.BondToTYBill;
import cj.netos.bondbank.args.CashoutBill;
import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.args.InvestBill;
import cj.studio.gateway.stub.annotation.CjStubInParameter;
import cj.studio.gateway.stub.annotation.CjStubMethod;
import cj.studio.gateway.stub.annotation.CjStubReturn;
import cj.studio.gateway.stub.annotation.CjStubService;

@CjStubService(bindService = "/asset/individual.service", usage = "个人资产")
public interface IBDBankIndividualAssetStub {
	@CjStubMethod(usage = "用户债券余额")
	@CjStubReturn(type = BigDecimal.class, usage = "用户的债券余额")
	BigDecimal boudBalance(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "user", usage = "用户") String user);

	@CjStubMethod(usage = "投单笔数")
	long depositBillCount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "depositor", usage = "存款人") String depositor);

	@CjStubMethod(usage = "提现笔数")
	long cashoutBillCount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "cashoutor", usage = "提现人") String cashoutor,
			@CjStubInParameter(key = "identity", usage = "身份") String identity);

	@CjStubMethod(usage = "承兑笔数")
	long exchangeBillCount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "exchanger", usage = "承兑人") String exchanger);

	@CjStubMethod(usage = "债转股笔数")
	long bondToStockBillCount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "issuer", usage = "发行人") String issuer);

	@CjStubMethod(usage = "投单总金额")
	BigDecimal totalInvestBillAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "user", usage = "用户") String user);

	@CjStubMethod(usage = "承兑总金额")
	BigDecimal totalExchangeBillAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "user", usage = "用户") String user);

	@CjStubMethod(usage = "提现总金额")
	BigDecimal totalChashoutBillAmount(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "user", usage = "用户") String user);

	@CjStubMethod(usage = "投单分页")
	@CjStubReturn(type = ArrayList.class, elementType = InvestBill.class, usage = "列表")
	List<InvestBill> pageInvestBill(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "user", usage = "用户") String user,
			@CjStubInParameter(key = "currPage", usage = "当前分页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "分页大小") int pageSize);

	@CjStubMethod(usage = "承兑分页")
	@CjStubReturn(type = ArrayList.class, elementType = ExchangeBill.class, usage = "列表")
	List<ExchangeBill> pageExchangeBill(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "user", usage = "用户") String user,
			@CjStubInParameter(key = "currPage", usage = "当前分页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "分页大小") int pageSize);

	@CjStubMethod(usage = "提现分页")
	@CjStubReturn(type = ArrayList.class, elementType = CashoutBill.class, usage = "列表")
	List<CashoutBill> pageCashoutBill(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "user", usage = "用户") String user,
			@CjStubInParameter(key = "currPage", usage = "当前分页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "分页大小") int pageSize);

	@CjStubMethod(usage = "债转帑分页")
	@CjStubReturn(type = ArrayList.class, elementType = BondToTYBill.class, usage = "列表")
	List<BondToTYBill> pageBondToTYBill(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "user", usage = "用户") String user,
			@CjStubInParameter(key = "currPage", usage = "当前分页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "分页大小") int pageSize);
}
