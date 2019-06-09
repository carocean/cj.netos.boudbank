package cj.netos.bondbank.plugin.BDBKEngine.bs;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.UpdateOptions;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.bondbank.args.BState;
import cj.netos.bondbank.args.BankState;
import cj.netos.bondbank.bs.IBDBankStateBS;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;

@CjService(name = "bdBankStateBS")
public class BDBankStateBS implements IBDBankStateBS {
	@CjServiceRef(refByName = "mongodb.bdbank.home")
	ICube home;// 银行本身的信息均存在fsbank网盘下的home下，home管理平台所有金证银行信息，而交易放在金证银行对应的存储方案空间中。

	@Override
	public void save(BankState state) {
		state.setId(null);
		String id = home.saveDoc(TABLE_BANK_STATE, new TupleDocument<>(state));
		state.setId(id);
	}
	@Override
	public BankState getState(String bank) {
		String cjql="select {'tuple':'*'} from tuple ?(colName) ?(colType) where {'tuple.bank':'?(bank)'}";
		IQuery<BankState> q=home.createQuery(cjql);
		q.setParameter("colName", TABLE_BANK_STATE);
		q.setParameter("colType", BankState.class.getName());
		q.setParameter("bank", bank);
		IDocument<BankState> doc=q.getSingleResult();
		if(doc==null) {
			BankState state=new BankState();
			state.setBank(bank);
			state.setCtime(System.currentTimeMillis());
			state.setState(BState.opened);
			return state;
		}
		return doc.tuple();
	}
	@Override
	public void revokeBank(String bank) {
		Bson filter = Document.parse(String.format("{'tuple.bank':'%s'}",bank));
		Bson update = Document.parse(String.format("{'$set':{'tuple.state':'%s'}}", BState.revoke));
		UpdateOptions uo = new UpdateOptions();
		uo.upsert(true);
		home.updateDocOne(TABLE_BANK_STATE, filter, update, uo);
	}
	@Override
	public void freezeBank(String bank) {
		Bson filter = Document.parse(String.format("{'tuple.bank':'%s'}",bank));
		Bson update = Document.parse(String.format("{'$set':{'tuple.state':'%s'}}", BState.freeze));
		UpdateOptions uo = new UpdateOptions();
		uo.upsert(true);
		home.updateDocOne(TABLE_BANK_STATE, filter, update, uo);
	}
	@Override
	public void closedBank(String bank) {
		Bson filter = Document.parse(String.format("{'tuple.bank':'%s'}",bank));
		Bson update = Document.parse(String.format("{'$set':{'tuple.state':'%s'}}", BState.closed));
		UpdateOptions uo = new UpdateOptions();
		uo.upsert(true);
		home.updateDocOne(TABLE_BANK_STATE, filter, update, uo);
		
	}
	@Override
	public void resumeBank(String bank) {
		Bson filter = Document.parse(String.format("{'tuple.bank':'%s'}",bank));
		Bson update = Document.parse(String.format("{'$set':{'tuple.state':'%s'}}", BState.opened));
		UpdateOptions uo = new UpdateOptions();
		uo.upsert(true);
		home.updateDocOne(TABLE_BANK_STATE, filter, update, uo);
	}
}
