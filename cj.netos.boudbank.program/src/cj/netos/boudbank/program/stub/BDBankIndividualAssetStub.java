package cj.netos.boudbank.program.stub;

import java.math.BigDecimal;
import java.util.List;

import cj.netos.boudbank.args.BondToTYBill;
import cj.netos.boudbank.args.CashoutBill;
import cj.netos.boudbank.args.ExchangeBill;
import cj.netos.boudbank.args.InvestBill;
import cj.netos.boudbank.bs.IBDBankIndividualAssetBS;
import cj.netos.boudbank.stub.IBDBankIndividualAssetStub;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;
@CjService(name="/asset/individual.service")
public class BDBankIndividualAssetStub extends GatewayAppSiteRestStub implements IBDBankIndividualAssetStub {
	@CjServiceRef(refByName = "BDBAEngine.bdBankIndividualAssetBS")
	IBDBankIndividualAssetBS bdBankIndividualAssetBS;
	@Override
	public BigDecimal boudBalance(String bank, String user) {
		return bdBankIndividualAssetBS.boudBalance(bank,user);
	}

	@Override
	public long depositBillCount(String bank, String depositor) {
		return bdBankIndividualAssetBS.depositBillCount(bank,depositor);
	}

	@Override
	public long cashoutBillCount(String bank, String cashoutor, String identity) {
		return bdBankIndividualAssetBS.cashoutBillCount(bank,cashoutor,identity);
	}

	@Override
	public long exchangeBillCount(String bank, String exchanger) {
		return bdBankIndividualAssetBS.exchangeBillCount(bank,exchanger);
	}

	@Override
	public long bondToStockBillCount(String bank, String issuer) {
		return bdBankIndividualAssetBS.bondToStockBillCount(bank,issuer);
	}

	@Override
	public BigDecimal totalInvestBillAmount(String bank, String user) {
		return bdBankIndividualAssetBS.totalInvestBillAmount(bank,user);
	}

	@Override
	public BigDecimal totalExchangeBillAmount(String bank, String user) {
		return bdBankIndividualAssetBS.totalExchangeBillAmount(bank,user);
	}

	@Override
	public BigDecimal totalChashoutBillAmount(String bank, String user) {
		return bdBankIndividualAssetBS.totalChashoutBillAmount(bank,user);
	}

	@Override
	public List<InvestBill> pageInvestBill(String bank, String user, int currPage, int pageSize) {
		return bdBankIndividualAssetBS.pageInvestBill(bank,user,currPage,pageSize);
	}

	@Override
	public List<ExchangeBill> pageExchangeBill(String bank, String user, int currPage, int pageSize) {
		return bdBankIndividualAssetBS.pageExchangeBill(bank,user,currPage,pageSize);
	}

	@Override
	public List<CashoutBill> pageCashoutBill(String bank, String user, int currPage, int pageSize) {
		return bdBankIndividualAssetBS.pageCashoutBill(bank,user,currPage,pageSize);
	}

	@Override
	public List<BondToTYBill> pageBondToTYBill(String bank, String user, int currPage, int pageSize) {
		return bdBankIndividualAssetBS.pageBondToTYBill(bank,user,currPage,pageSize);
	}

}
