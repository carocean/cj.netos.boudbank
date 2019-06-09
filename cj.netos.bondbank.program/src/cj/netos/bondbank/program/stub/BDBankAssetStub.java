package cj.netos.bondbank.program.stub;

import java.math.BigDecimal;
import java.util.List;

import cj.netos.bondbank.args.BondToTYBill;
import cj.netos.bondbank.args.CashoutBill;
import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.args.InvestBill;
import cj.netos.bondbank.bs.IBDBankAssetBS;
import cj.netos.bondbank.stub.IBDBankAssetStub;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;
@CjService(name="/asset/bank.service")
public class BDBankAssetStub extends GatewayAppSiteRestStub implements IBDBankAssetStub {
	@CjServiceRef(refByName = "BDBKEngine.bdBankAssetBS")
	IBDBankAssetBS bdBankAssetBS;
	@Override
	public long investBillCount(String bank) {
		return bdBankAssetBS.investBillCount(bank);
	}

	@Override
	public long cashBillCount(String bank) {
		return bdBankAssetBS.cashBillCount(bank);
	}

	@Override
	public long exchangeBillCount(String bank) {
		return bdBankAssetBS.exchangeBillCount(bank);
	}

	@Override
	public BigDecimal totalInvestBillAmount(String bank) {
		return bdBankAssetBS.totalInvestBillAmount(bank);
	}

	@Override
	public BigDecimal totalLiabilitiesAmount(String bank) {
		return bdBankAssetBS.totalLiabilitiesAmount(bank);
	}

	@Override
	public BigDecimal totalBuyBondAmount(String bank) {
		return bdBankAssetBS.totalBuyBondAmount(bank);
	}

	@Override
	public BigDecimal totalMerchantBondQuantities(String bank) {
		return bdBankAssetBS.totalMerchantBondQuantities(bank);
	}

	@Override
	public BigDecimal totalConsumerBondQuantities(String bank) {
		return bdBankAssetBS.totalConsumerBondQuantities(bank);
	}

	@Override
	public List<InvestBill> pageInvestBill(String bank, int currPage, int pageSize) {
		return bdBankAssetBS.pageInvestBill(bank);
	}

	@Override
	public List<CashoutBill> pageCashoutBill(String bank, int currPage, int pageSize) {
		return bdBankAssetBS.pageCashoutBill(bank);
	}

	@Override
	public List<ExchangeBill> pageExchangeBill(String bank, int currPage, int pageSize) {
		return bdBankAssetBS.pageExchangeBill(bank);
	}

	@Override
	public List<BondToTYBill> pageBondToStockBill(String bank, int currPage, int pageSize) {
		return bdBankAssetBS.pageBondToStockBill(bank);
	}

}
