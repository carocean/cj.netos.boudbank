package cj.netos.boudbank.plugin.BDBKEngine.bs;

import java.math.BigDecimal;
import java.util.Map;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.netos.boudbank.args.EInvesterType;
import cj.netos.boudbank.args.InvestBill;
import cj.netos.boudbank.bs.IBDBankBalanceBS;
import cj.netos.boudbank.bs.IBDBankIndividualAssetBS;
import cj.netos.boudbank.bs.IBDBankPropertiesBS;
import cj.netos.boudbank.bs.IBDBankTransactionBS;
import cj.netos.boudbank.plugin.BDBKEngine.util.BigDecimalConstants;
import cj.netos.fsbank.stub.IFSBankTransactionStub;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjBridge;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.annotation.CjServiceSite;
import cj.studio.gateway.stub.annotation.CjStubRef;

@CjBridge(aspects = "@rest")
@CjService(name = "bdBankTransactionBS")
public class BDBankTransactionBS implements IBDBankTransactionBS,BigDecimalConstants {
	@CjServiceSite
	IServiceSite site;
	@CjServiceRef
	IBDBankBalanceBS bdBankBalance;
	@CjServiceRef
	IBDBankPropertiesBS bdBankPropertiesBS;
	@CjServiceRef
	IBDBankIndividualAssetBS bdBankIndividualAccountAssetBS;
	ICube cubeBank;
	@CjStubRef(remote = "rest://backend/fsbank/", stub = IFSBankTransactionStub.class)
	IFSBankTransactionStub fsBankTransactionStub;

	protected ICube getBankCube(String bank) {
		if (cubeBank != null) {
			return cubeBank;
		}
		cubeBank = (ICube) site.getService("mongodb.bdbank." + bank + ":autocreate");
		return cubeBank;
	}

	@Override
	public Map<String, Object> cashoutBill(String key, String balanceType, String cashoutor, String identity,
			BigDecimal reqAmount, String memo, String informAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	// 记账（存入informAddress在fsbank回调事件接收中取出以通知原调用者)，并向fsbank购买债券，并告诉fsbank回调地址，在接收通知的模块中真正的计算
	@Override
	public Map<String, Object> investBill(String bank, String invester, BigDecimal amount, EInvesterType type,
			String informAddress) {
		InvestBill bill = new InvestBill();
		bill.setIntime(System.currentTimeMillis());
		bill.setAmount(amount);
		bill.setInvester(invester);
		if (type != null) {
			bill.setType(type);
		}
		bill.setInformAddress(informAddress);
		BigDecimal customerBondRate=customerBondRate(bdBankPropertiesBS,bank);
		bill.setCustomerBondRate(customerBondRate);
		
		BigDecimal merchantBondRate=merchantBondRate(bdBankPropertiesBS,bank);
		bill.setMerchantBondRate(merchantBondRate);
		
		BigDecimal feeRate=feeRate(bdBankPropertiesBS, bank);
		bill.setFeeRate(feeRate);
		
		fsBankTransactionStub.deposit(bank, invester, amount, informAddress);
		return null;
	}

	@Override
	public Map<String, Object> exchangeBill(String key, String exchanger, BigDecimal bondQuantities,
			String informAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> issueStockBill(String key, String issuer, BigDecimal bondQuantities,
			String informAddress) {
		// TODO Auto-generated method stub
		return null;
	}

}
