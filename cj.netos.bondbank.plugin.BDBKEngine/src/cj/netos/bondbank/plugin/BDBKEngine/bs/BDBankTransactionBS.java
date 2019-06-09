package cj.netos.bondbank.plugin.BDBKEngine.bs;

import java.math.BigDecimal;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.bondbank.args.EInvesterType;
import cj.netos.bondbank.args.InvestBill;
import cj.netos.bondbank.bs.IBDBankIndividualAssetBS;
import cj.netos.bondbank.bs.IBDBankInfoBS;
import cj.netos.bondbank.bs.IBDBankPropertiesBS;
import cj.netos.bondbank.bs.IBDBankTransactionBS;
import cj.netos.bondbank.util.BigDecimalConstants;
import cj.netos.fsbank.stub.IFSBankTransactionStub;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjBridge;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.annotation.CjServiceSite;
import cj.studio.gateway.stub.annotation.CjStubRef;

@CjBridge(aspects = "@rest")
@CjService(name = "bdBankTransactionBS")
public class BDBankTransactionBS implements IBDBankTransactionBS, BigDecimalConstants {
	@CjServiceSite
	IServiceSite site;
	@CjServiceRef
	IBDBankPropertiesBS bdBankPropertiesBS;
	@CjServiceRef
	IBDBankInfoBS bdBankInfoBS;
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
	public void cashoutBill(String key, String balanceType, String cashoutor, String identity, BigDecimal reqAmount,
			String memo, String informAddress) {
		// TODO Auto-generated method stub
	}

	// 记账（存入informAddress在fsbank回调事件接收中取出以通知原调用者)，并向fsbank购买债券，并告诉fsbank回调地址，在接收通知的模块中真正的计算
	@Override
	public void investBill(String bank, String invester, BigDecimal amount, EInvesterType type, String informAddress) {
		InvestBill bill = new InvestBill();
		bill.setIntime(System.currentTimeMillis());
		bill.setAmount(amount);
		bill.setInvester(invester);
		if (type == null) {
			type = EInvesterType.merchant;
		}
		bill.setType(type);
		bill.setInformAddress(informAddress);
		BigDecimal buyBondAmount = null;
		if (bill.getType() == EInvesterType.customer) {
			bill.setMerchantBondRate(new BigDecimal(0));
			bill.setMerchantBondAmount(new BigDecimal(0));//消费者投白单时，商户不能得现金
			BigDecimal feeRate = feeRate(bdBankPropertiesBS, bank);
			bill.setFeeBondRate(feeRate);
			bill.setMerchantBondRate(feeRate);
			bill.setTotalBondAmount(amount);
			// 消费者投白单情况是委托债券银行代买债，在购得债之后商户从债中按此比率提取拥金，因此是全额买债
			BigDecimal customerBondRate = new BigDecimal(1).subtract(feeRate).setScale(scale, roundingMode);
			bill.setCustomerBondRate(customerBondRate);
			buyBondAmount = amount;
		} else if (bill.getType() == EInvesterType.merchant) {
			BigDecimal customerBondRate = customerBondRate(bdBankPropertiesBS, bank);
			bill.setCustomerBondRate(customerBondRate);
			bill.setCustomerBondAmount(new BigDecimal(0));// 在商户投单时，消费者不能得现金

			BigDecimal merchantBondRate = merchantBondRate(bdBankPropertiesBS, bank);
			bill.setMerchantBondRate(merchantBondRate);
			BigDecimal merchantBondAmount = amount.multiply(merchantBondRate).setScale(scale, roundingMode);
			bill.setMerchantBondAmount(merchantBondAmount);
			BigDecimal merchantDepositAmount = amount.multiply(new BigDecimal(1).subtract(merchantBondRate))
					.setScale(scale, roundingMode);
			bill.setMerchantSelfAmount(merchantDepositAmount);

			BigDecimal totalBondAmount = amount.multiply(merchantBondRate.add(customerBondRate)).setScale(scale,
					roundingMode);
			bill.setTotalBondAmount(totalBondAmount);

			BigDecimal tailAmount = new BigDecimal(0);
			bill.setTailAmount(tailAmount);

			buyBondAmount = merchantBondAmount;
		}

		ICube cubeBank = getBankCube(bank);
		String id = cubeBank.saveDoc(TABLE_Invests, new TupleDocument<>(bill));
		bill.setCode(id);

		String fsbankInformAddress = String.format("%s?bankno=%s%sbillno=%s",
				site.getProperty("transaction_investBill_informAddress"), bank, "%26", id);// 由当前项目接收
		try {
			String fsbankno = this.bdBankInfoBS.getBankInfo(bank).getFsbank();
			fsBankTransactionStub.deposit(fsbankno, invester, buyBondAmount, fsbankInformAddress);
		} catch (Exception e) {
			cubeBank.deleteDoc(TABLE_Invests, id);
			throw e;
		}
	}

	@Override
	public void exchangeBill(String key, String exchanger, BigDecimal bondQuantities, String informAddress) {
		// TODO Auto-generated method stub
	}

	@Override
	public void issueStockBill(String key, String issuer, BigDecimal bondQuantities, String informAddress) {
		// TODO Auto-generated method stub
	}

}
