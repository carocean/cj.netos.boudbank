package cj.netos.bondbank.plugin.BDBKEngine.bs;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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
	@Override
	public BankBalance getBankBalance(String bank) {
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
	@Override
	public void decBankBondQuantities(String bankno, BigDecimal bondQuantities) {
		BigDecimal bondQuantitiesBalance= getBankBondQuantitiesBalance(bankno);
		BigDecimal balance=bondQuantitiesBalance.subtract(bondQuantities);
		updateBankBondQuantities(bankno,balance);
	}
	@Override
	public BigDecimal getBankBondQuantitiesBalance(String bank) {
		String cjql = String.format("select {'tuple.bondQuantities':1} from tuple %s %s where {}",
				TABLE_BankBalance, HashMap.class.getName());
		IQuery<HashMap<String, Object>> q = getBankCube(bank).createQuery(cjql);
		IDocument<HashMap<String, Object>> doc = q.getSingleResult();
		if (doc == null || doc.tuple() == null) {
			return new BigDecimal(0);
		}
		return new BigDecimal(doc.tuple().get("bondQuantities") + "");
	}
	
	private void updateBankBondQuantities(String bank, BigDecimal balance) {
		Bson filter = Document.parse(String.format("{}"));
		Bson update = Document.parse(String.format("{'$set':{'tuple.bondQuantities':%s}}", balance));
		UpdateOptions op = new UpdateOptions();
		op.upsert(true);
		getBankCube(bank).updateDocOne(TABLE_BankBalance, filter, update, op);
	}

	private void addBondQuantitiesStock(String bank, String user, String billno, BigDecimal bondQuantities,
			BigDecimal bondFaceValue) {
		BondQuantitiesStock stock = new BondQuantitiesStock();
		stock.setBondFaceValue(bondFaceValue);
		stock.setBondQuantities(bondQuantities);
		stock.setSource(billno);
		stock.setUser(user);
		stock.setCtime(System.currentTimeMillis());
		stock.setType(ESourceType.investTable);
		getBankCube(bank).saveDoc(TABLE_Stock_BondQuantities, new TupleDocument<>(stock));
	}

	private void decBondQuantitiesStock(String bank, String user, BigDecimal bondQuantities) {
		//从最旧的开始扣起（就是删除旧记录，如果尾记录不够完全用完，则需拆单，直到最新
		String cjql = String.format("select {'tuple':'*'}.sort({'tuple.ctime':1}) from tuple %s %s where {'tuple.user':'%s'}",
				TABLE_Stock_BondQuantities, BondQuantitiesStock.class.getName(), user);
		IQuery<BondQuantitiesStock> q = getBankCube(bank).createQuery(cjql);
		List<IDocument<BondQuantitiesStock>> docs = q.getResultList();
		BigDecimal remaining=bondQuantities;
		BigDecimal zero=new BigDecimal(0);
		for(IDocument<BondQuantitiesStock> doc:docs) {
			if(remaining.compareTo(zero)<=0) {
				break;
			}
			BigDecimal bq=doc.tuple().getBondQuantities();
			if(remaining.compareTo(bq)>=0) {
				remaining=remaining.subtract(bq);
				getBankCube(bank).deleteDoc(TABLE_Stock_BondQuantities, doc.docid());
				continue;
			}
			BigDecimal stock=bq.subtract(remaining);
			Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", doc.docid()));
			Bson update = Document.parse(String.format("{'$set':{'tuple.bondQuantities':%s}}", new Gson().toJson(stock)));
			getBankCube(bank).updateDocOne(TABLE_Stock_BondQuantities, filter, update);
			break;
		}
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
		decIndividualBondQuantities(bankno, bill.getExchanger(), bill.getBondQuantities());
		addIndividualCashAmount(bankno, bill.getExchanger(), bill.getDeservedAmount());
		decBankBondQuantities(bankno,bill.getBondQuantities());
	}


	@Override
	public void decIndividualBondQuantities(String bankno, String user, BigDecimal bondQuantities) {
		BigDecimal balance = getIndividualBondQuantitiesBalance(bankno, user);
		BigDecimal bondQuantitiesBalance = balance.subtract(bondQuantities);
		updateIndividualBondQuantitiesBalance(bankno, user, bondQuantitiesBalance);
		decBondQuantitiesStock(bankno, user, bondQuantities);
	}

	private void updateIndividualBondQuantitiesBalance(String bank, String user, BigDecimal bondQuantitiesBalance) {
		Bson filter = Document.parse(String.format("{'tuple.user':'%s'}", user));
		Bson update = Document.parse(String.format("{'$set':{'tuple.bondQuantities':%s}}", bondQuantitiesBalance));
		UpdateOptions op = new UpdateOptions();
		op.upsert(true);
		getBankCube(bank).updateDocOne(TABLE_IndividualBalance, filter, update, op);
	}

	@Override
	public BigDecimal getIndividualBondQuantitiesBalance(String bank, String user) {
		String cjql = String.format("select {'tuple.bondQuantities':1} from tuple %s %s where {'tuple.user':'%s'}",
				TABLE_IndividualBalance, HashMap.class.getName(), user);
		IQuery<HashMap<String, Object>> q = getBankCube(bank).createQuery(cjql);
		IDocument<HashMap<String, Object>> doc = q.getSingleResult();
		if (doc == null || doc.tuple() == null) {
			return new BigDecimal(0);
		}
		return new BigDecimal(doc.tuple().get("bondQuantities") + "");
	}

	@Override
	public void addIndividualCashAmount(String bankno, String user, BigDecimal deservedAmount) {
		BigDecimal balance = getIndividualCashAmountBalance(bankno, user);
		BigDecimal cashAmountBalance = balance.add(deservedAmount);
		updateIndividualCashAmountBalance(bankno, user, cashAmountBalance);
	}

	private void updateIndividualCashAmountBalance(String bank, String user, BigDecimal cashAmountBalance) {
		Bson filter = Document.parse(String.format("{'tuple.user':'%s'}", user));
		Bson update = Document.parse(String.format("{'$set':{'tuple.cashAmount':%s}}", cashAmountBalance));
		UpdateOptions op = new UpdateOptions();
		op.upsert(true);
		getBankCube(bank).updateDocOne(TABLE_IndividualBalance, filter, update, op);
	}

	@Override
	public BigDecimal getIndividualCashAmountBalance(String bank, String user) {
		String cjql = String.format("select {'tuple.cashAmount':1} from tuple %s %s where {'tuple.user':'%s'}",
				TABLE_IndividualBalance, HashMap.class.getName(), user);
		IQuery<HashMap<String, Object>> q = getBankCube(bank).createQuery(cjql);
		IDocument<HashMap<String, Object>> doc = q.getSingleResult();
		if (doc == null || doc.tuple() == null) {
			return new BigDecimal(0);
		}
		return new BigDecimal(doc.tuple().get("cashAmount") + "");
	}
}
