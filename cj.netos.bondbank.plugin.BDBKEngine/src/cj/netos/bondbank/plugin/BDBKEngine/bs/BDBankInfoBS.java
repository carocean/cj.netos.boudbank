package cj.netos.bondbank.plugin.BDBKEngine.bs;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.bondbank.args.BankInfo;
import cj.netos.bondbank.bs.IBDBankInfoBS;
import cj.studio.ecm.EcmException;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;

@CjService(name = "bdBankInfoBS")
public class BDBankInfoBS implements IBDBankInfoBS {
	@CjServiceRef(refByName = "mongodb.bdbank.home")
	ICube home;// 银行本身的信息均存在fsbank网盘下的home下，home管理平台所有金证银行信息，而交易放在金证银行对应的存储方案空间中。

	@Override
	public boolean existsBankName(String name) {
		String where = String.format("{'tuple.name':'%s'}", name);
		return home.tupleCount(TABLE_BANK_INFO, where) > 0;
	}

	@Override
	public void saveBank(BankInfo info) {
		if (existsBankName(info.getName())) {
			throw new EcmException("已存在债券银行名为：" + info.getName());
		}
		info.setCode(null);
		String id = home.saveDoc(TABLE_BANK_INFO, new TupleDocument<>(info));
		info.setCode(id);
	}

	@Override
	public boolean existsBankCode(String bank) {
		String where = String.format("{'_id':ObjectId('%s')}", bank);
		return home.tupleCount(TABLE_BANK_INFO, where) > 0;
	}

	@Override
	public BankInfo getBankInfo(String bankCode) {
		String cjql = String.format("select {'tuple':'*'} from tuple %s %s where {'_id':ObjectId('%s')}",
				TABLE_BANK_INFO, BankInfo.class.getName(), bankCode);
		IQuery<BankInfo> q = home.createQuery(cjql);
		IDocument<BankInfo> doc = q.getSingleResult();
		if (doc == null)
			return null;
		doc.tuple().setCode(doc.docid());
		return doc.tuple();
	}

	@Override
	public List<BankInfo> pageBankInfo(int currPage, int pageSize) {
		String cjql = String.format("select {'tuple':'*'}.limit(%s).skip(%s) from tuple %s %s where {}", pageSize,
				currPage, TABLE_BANK_INFO, BankInfo.class.getName());
		IQuery<BankInfo> q = home.createQuery(cjql);
		List<IDocument<BankInfo>> docs = q.getResultList();
		List<BankInfo> list = new ArrayList<BankInfo>();
		for (IDocument<BankInfo> doc : docs) {
			doc.tuple().setCode(doc.docid());
			list.add(doc.tuple());
		}
		return list;
	}

	@Override
	public void updateFsBank(String bank, String fsbank) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", bank));
		Bson update = Document.parse(String.format("{'$set':{'tuple.fsbank':'%s'}}", fsbank));
		home.updateDocOne(TABLE_BANK_INFO, filter, update);
	}

	@Override
	public void updateBankName(String bank, String name) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", bank));
		Bson update = Document.parse(String.format("{'$set':{'tuple.name':'%s'}}", name));
		home.updateDocOne(TABLE_BANK_INFO, filter, update);
	}

	@Override
	public void updateBankPresident(String bank, String president) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", bank));
		Bson update = Document.parse(String.format("{'$set':{'tuple.president':'%s'}}", president));
		home.updateDocOne(TABLE_BANK_INFO, filter, update);
	}

	@Override
	public void updateBankCompany(String bank, String company) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", bank));
		Bson update = Document.parse(String.format("{'$set':{'tuple.company':'%s'}}", company));
		home.updateDocOne(TABLE_BANK_INFO, filter, update);
	}

	@Override
	public boolean isExpired(String bank) throws CircuitException {
		BankInfo info = getBankInfo(bank);
		if (info == null) {
			throw new CircuitException("404",String.format("The bankno %s of boundbank does not exist. ",bank));
		}
		return info.getExpiredTime() <= System.currentTimeMillis();
	}
}
