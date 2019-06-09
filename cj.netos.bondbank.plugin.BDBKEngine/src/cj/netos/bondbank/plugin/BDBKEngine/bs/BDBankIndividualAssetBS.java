package cj.netos.bondbank.plugin.BDBKEngine.bs;

import java.math.BigDecimal;
import java.util.List;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.netos.bondbank.args.BondToTYBill;
import cj.netos.bondbank.args.CashoutBill;
import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.args.InvestBill;
import cj.netos.bondbank.bs.IBDBankIndividualAssetBS;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceSite;

@CjService(name = "bdBankIndividualAssetBS")
public class BDBankIndividualAssetBS implements IBDBankIndividualAssetBS {
	private ICube cubeBank;
	@CjServiceSite
	IServiceSite site;

	protected ICube getBankCube(String bank) {
		if (cubeBank != null) {
			return cubeBank;
		}
		cubeBank = (ICube) site.getService("mongodb.bdbank." + bank + ":autocreate");
		return cubeBank;
	}

	@Override
	public List<BondToTYBill> pageBondToTYBill(String bank, String user, int currPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CashoutBill> pageCashoutBill(String bank, String user, int currPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExchangeBill> pageExchangeBill(String bank, String user, int currPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvestBill> pageInvestBill(String bank, String user, int currPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal totalChashoutBillAmount(String bank, String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long depositBillCount(String bank, String depositor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long cashoutBillCount(String bank, String cashoutor, String identity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long exchangeBillCount(String bank, String exchanger) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long bondToStockBillCount(String bank, String issuer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal totalInvestBillAmount(String bank, String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal totalExchangeBillAmount(String bank, String user) {
		// TODO Auto-generated method stub
		return null;
	}

}
