package cj.netos.bondbank.plugin.BDBKEngine.bs;

import java.math.BigDecimal;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.UpdateOptions;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.bondbank.args.BankBalance;
import cj.netos.bondbank.args.BankInfo;
import cj.netos.bondbank.args.BondQuantitiesStock;
import cj.netos.bondbank.args.EInvesterType;
import cj.netos.bondbank.args.ESourceType;
import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.args.IndividualBalance;
import cj.netos.bondbank.args.InvestBill;
import cj.netos.bondbank.bs.IBDBalanceBS;
import cj.netos.bondbank.bs.IBDBankInfoBS;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.annotation.CjServiceSite;
import cj.ultimate.gson2.com.google.gson.Gson;

@CjService(name = "bdBalanceBS")
public class BDBalanceBS implements IBDBalanceBS {
	private ICube cubeBank;
	@CjServiceSite
	IServiceSite site;
	@CjServiceRef(refByName = "bdBankInfoBS")
	IBDBankInfoBS bdBankInfoBS;

	protected ICube getBankCube(String bank) {
		if (cubeBank != null) {
			return cubeBank;
		}
		cubeBank = (ICube) site.getService("mongodb.bdbank." + bank + ":autocreate");
		return cubeBank;
	}

	@Override
	public void onAddInvestBill(String bank, InvestBill bill) {
		addBankBalance(bank, bill);
		addIndividualBalance(bank, bill);
	}

	private void addBankBalance(String bank, InvestBill bill) {// 一个银行只有一条余额记录
		BankBalance balance = getBankBalance(bank);
		BigDecimal addInvestBillAmount = balance.getInvestBillAmount().add(bill.getAmount());
		balance.setInvestBillAmount(addInvestBillAmount);
		BigDecimal addBondQuantities = balance.getBondQuantities().add(bill.getBondQuantities());
		balance.setBondQuantities(addBondQuantities);
		updateBankBalance(bank, balance);
	}

	private void updateBankBalance(String bank, BankBalance balance) {
		Bson filter = Document.parse(String.format("{}"));
		Bson update = Document.parse(String.format("{'$set':{'tuple':%s}}", new Gson().toJson(balance)));
		UpdateOptions op = new UpdateOptions();
		op.upsert(true);
		getBankCube(bank).updateDocOne(TABLE_BankBalance, filter, update, op);
	}

	private BankBalance getBankBalance(String bank) {
		String cjql = String.format("select {'tuple':'*'} from tuple %s %s where {}", TABLE_BankBalance,
				BankBalance.class.getName());
		IQuery<BankBalance> q = getBankCube(bank).createQuery(cjql);
		IDocument<BankBalance> doc = q.getSingleResult();
		if (doc == null || doc.tuple() == null) {
			BankBalance balance = new BankBalance();
			balance.setBondQuantities(new BigDecimal(0));
			balance.setInvestBillAmount(new BigDecimal(0));
			return balance;
		}
		return doc.tuple();
	}
	
	private void addIndividualBalance(String bank, InvestBill bill) {// 一个银行的一个用户只有一条余额记录
		BankInfo info = bdBankInfoBS.getBankInfo(bank);
		String president = info.getPresident();
		if (bill.getType() == EInvesterType.customer) {// 如果是消费者投单，则先计算消费者余额，再求出对户商户，并计算商户余额
			addToCustomerBalance(bank, bill.getInvester(), bill);
			addToMerchantBalance(bank, president, bill);
		} else {
			addToCustomerBalance(bank, bill.getInvester(), bill);
			addToMerchantBalance(bank, president, bill);
		}
	}

	private void addToMerchantBalance(String bank, String user, InvestBill bill) {
		IndividualBalance balance = getIndividualBalance(bank, user);
		balance.setBondQuantities(balance.getBondQuantities().add(bill.getMerchantBondQuantities()));
		balance.setCashAmount(balance.getCashAmount()
				.add(bill.getMerchantSelfAmount() == null ? new BigDecimal(0) : bill.getMerchantSelfAmount()));
		updateIndividualBalance(bank, user, balance);
		addBondQuantitiesStock(bank, user, bill.getCode(), bill.getMerchantBondQuantities(), bill.getBondFaceValue());
	}

	private void addToCustomerBalance(String bank, String user, InvestBill bill) {
		IndividualBalance balance = getIndividualBalance(bank, user);
		balance.setBondQuantities(balance.getBondQuantities().add(bill.getCustomerBondQuantities()));
		balance.setCashAmount(balance.getCashAmount()
				.add(bill.getCustomerBondAmount() == null ? new BigDecimal(0) : bill.getCustomerBondAmount()));
		updateIndividualBalance(bank, user, balance);
		addBondQuantitiesStock(bank, user, bill.getCode(), bill.getCustomerBondQuantities(), bill.getBondFaceValue());
	}

	private void addBondQuantitiesStock(String bank, String user, String billno, BigDecimal bondQuantities,
			BigDecimal bondFaceValue) {
		BondQuantitiesStock stock = new BondQuantitiesStock();
		stock.setBondFaceValue(bondFaceValue);
		stock.setBondQuantities(bondQuantities);
		stock.setSource(billno);
		stock.setUser(user);
		stock.setType(ESourceType.investTable);
		getBankCube(bank).saveDoc(TABLE_BondQuantities, new TupleDocument<>(stock));
	}

	private void updateIndividualBalance(String bank, String user, IndividualBalance balance) {
		Bson filter = Document.parse(String.format("{'tuple.user':'%s'}", user));
		Bson update = Document.parse(String.format("{'$set':{'tuple':%s}}", new Gson().toJson(balance)));
		UpdateOptions op = new UpdateOptions();
		op.upsert(true);
		getBankCube(bank).updateDocOne(TABLE_IndividualBalance, filter, update, op);
	}
	@Override
	public IndividualBalance getIndividualBalance(String bank, String user) {
		String cjql = String.format("select {'tuple':'*'} from tuple %s %s where {'tuple.user':'%s'}",
				TABLE_IndividualBalance, IndividualBalance.class.getName(), user);
		IQuery<IndividualBalance> q = getBankCube(bank).createQuery(cjql);
		IDocument<IndividualBalance> doc = q.getSingleResult();
		if (doc == null || doc.tuple() == null) {
			IndividualBalance balance = new IndividualBalance();
			balance.setBondQuantities(new BigDecimal(0));
			balance.setCashAmount(new BigDecimal(0));
			balance.setUser(user);
			return balance;
		}
		return doc.tuple();
	}
	@Override
	public void onAddExchangeBill(String bankno, ExchangeBill bill) {
		// 正式扣除个人及银行的债券，并存入承兑后的现金
		
	}
}
