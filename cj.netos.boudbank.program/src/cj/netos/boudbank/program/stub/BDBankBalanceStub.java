package cj.netos.boudbank.program.stub;

import java.math.BigDecimal;
import java.util.List;

import cj.netos.boudbank.args.Balance;
import cj.netos.boudbank.args.BondBalanceStockBill;
import cj.netos.boudbank.args.FreeBalanceStockBill;
import cj.netos.boudbank.bs.IBDBankBalanceBS;
import cj.netos.boudbank.stub.IBDBankBalanceStub;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.annotation.CjServiceSite;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;
import cj.studio.util.reactor.IReactor;

@CjService(name = "/balance.service")
public class BDBankBalanceStub extends GatewayAppSiteRestStub implements IBDBankBalanceStub {
	@CjServiceRef(refByName = "BDBAEngine.bdBankBalanceBS")
	IBDBankBalanceBS bdBankBalanceBS;
	@CjServiceSite
	IServiceSite site;
	IReactor reactor;

	protected IReactor getReactor() {
		if (reactor == null) {
			reactor = (IReactor) site.getService("$.reactor");
		}
		return reactor;
	}

	@Override
	public Balance loadBalance(String bank) {
		return bdBankBalanceBS.loadBalance(bank);
	}

	@Override
	public BigDecimal getMerchantDepositAmount(String bank) {
		return bdBankBalanceBS.getMerchantDepositAmount(bank);
	}

	@Override
	public BigDecimal getMerchantBondQuantities(String bank) {
		return bdBankBalanceBS.getMerchantBondQuantities(bank);
	}

	@Override
	public BigDecimal getConsumerBondQuantities(String bank) {
		return bdBankBalanceBS.getConsumerBondQuantities(bank);
	}

	@Override
	public BigDecimal getFreeAmountBalance(String bank) {
		return bdBankBalanceBS.getFreeAmountBalance(bank);
	}

	@Override
	public BigDecimal getTailInFreeAmountBalance(String bank) {
		return bdBankBalanceBS.getTailInFreeAmountBalance(bank);
	}

	@Override
	public BigDecimal getCommissionInFreeAmountBalance(String bank) {
		return bdBankBalanceBS.getCommissionInFreeAmountBalance(bank);
	}

	@Override
	public BigDecimal subtractFreeAmount(String bank, BigDecimal amount) {
		return bdBankBalanceBS.subtractFreeAmount(bank, amount);
	}

	@Override
	public BigDecimal addFreeAmount(String bank, String user, String source, String type, BigDecimal amount) {
		return bdBankBalanceBS.addFreeAmount(bank, user, source, type, amount);
	}

	@Override
	public List<FreeBalanceStockBill> pageFreeAmount(String bank, int currPage, int pageSize) {
		return bdBankBalanceBS.pageFreeAmount(bank, currPage, pageSize);
	}

	@Override
	public BigDecimal subtractMerchantBondQuantities(String bank, BigDecimal bondQuantities) {
		return bdBankBalanceBS.subtractMerchantBondQuantities(bank, bondQuantities);
	}

	@Override
	public BigDecimal addMerchantBondQuantities(String bank, String merchant, String source, BigDecimal bondQuantities,
			BigDecimal faceValue) {
		return bdBankBalanceBS.addMerchantBondQuantities(bank, merchant, source, bondQuantities, faceValue);
	}

	@Override
	public List<BondBalanceStockBill> pageBondBalanceStock(String bank, int currPage, int pageSize) {
		return bdBankBalanceBS.pageBondBalanceStock(bank, currPage, pageSize);
	}

}
