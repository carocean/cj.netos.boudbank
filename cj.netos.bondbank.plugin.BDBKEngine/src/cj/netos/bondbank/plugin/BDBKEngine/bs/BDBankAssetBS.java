package cj.netos.bondbank.plugin.BDBKEngine.bs;

import java.math.BigDecimal;
import java.util.List;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.netos.bondbank.args.BondToTYBill;
import cj.netos.bondbank.args.CashoutBill;
import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.args.InvestBill;
import cj.netos.bondbank.bs.IBDBankAssetBS;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjServiceSite;

public class BDBankAssetBS implements IBDBankAssetBS {
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
	public long investBillCount(String bank) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long cashBillCount(String bank) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long exchangeBillCount(String bank) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal totalInvestBillAmount(String bank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal totalLiabilitiesAmount(String bank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal totalBuyBondAmount(String bank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal totalConsumerBondQuantities(String bank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal totalMerchantBondQuantities(String bank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvestBill> pageInvestBill(String bank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CashoutBill> pageCashoutBill(String bank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExchangeBill> pageExchangeBill(String bank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BondToTYBill> pageBondToStockBill(String bank) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
