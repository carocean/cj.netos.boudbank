package cj.netos.boudbank.plugin.BDBKEngine.bs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.boudbank.args.BankLicense;
import cj.netos.boudbank.bs.IBDBankLicenseBS;
import cj.studio.backend.uc.service.AuthenticationException;
import cj.studio.backend.uc.stub.ITenantTokenStub;
import cj.studio.ecm.annotation.CjBridge;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.gateway.stub.annotation.CjStubRef;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.gson2.com.google.gson.reflect.TypeToken;

@CjBridge(aspects = "@rest")
@CjService(name = "bdBankLicenseBS")
public class BDBankLicenseBS implements IBDBankLicenseBS {
	@CjServiceRef(refByName = "mongodb.bdbank.home")
	ICube home;// 银行本身的信息均存在fsbank网盘下的home下，home管理平台所有金证银行信息，而交易放在金证银行对应的存储方案空间中。

	@CjStubRef(remote = "rest://backend/uc/", stub = ITenantTokenStub.class)
	ITenantTokenStub tenantTokenStub;

	@Override
	public void saveLicense(String presidentPwd, BankLicense license) throws CircuitException {
		license.setCode(null);
		// 产生token
		String json = "";
		try {
			json = tenantTokenStub.genToken("netos.fsbank", license.getPresident(), presidentPwd,
					license.getExpiryDate());
		} catch (AuthenticationException e) {
			throw new CircuitException(e.getStatus(), e);
		}
		Map<String, String> map = new Gson().fromJson(json, new TypeToken<HashMap<String, String>>() {
		}.getType());
		if (!"200".equals(map.get("status"))) {
			throw new CircuitException(map.get("status"), "uc消息：" + map.get("message"));
		}
		license.setToken(map.get("result"));
		String id = home.saveDoc(TABLE_LISENCE, new TupleDocument<>(license));
		license.setCode(id);
	}

	@Override
	public boolean hasLicenseOfBank(String bank) {// 一个银行一个企业
		String where = String.format("{'tuple.bank':'%s'}", bank);
		return home.tupleCount(TABLE_LISENCE, where) > 0;
	}

	@Override
	public BankLicense getBankLicense(String bankCode) {
		String cjql = "select {'tuple':'*'}.sort({'_id':-1,'tuple.issueDate':-1}) from tuple ?(colName) ?(clazz) where {'tuple.bank':'?(bank)'}";
		IQuery<BankLicense> q = home.createQuery(cjql);
		q.setParameter("colName", TABLE_LISENCE);
		q.setParameter("clazz", BankLicense.class.getName());
		q.setParameter("bank", bankCode);
		IDocument<BankLicense> doc = q.getSingleResult();
		if (doc == null)
			return null;
		doc.tuple().setCode(doc.docid());
		return doc.tuple();
	}

	@Override
	public List<BankLicense> pageBankLicense(int currPage, int pageSize) {
		String cjql = "select {'tuple':'*'} from tuple ?(colName) ?(clazz) where {}";
		IQuery<BankLicense> q = home.createQuery(cjql);
		q.setParameter("colName", TABLE_LISENCE);
		q.setParameter("clazz", BankLicense.class.getName());
		List<IDocument<BankLicense>> docs = q.getResultList();
		List<BankLicense> list = new ArrayList<>();
		for (IDocument<BankLicense> doc : docs) {
			doc.tuple().setCode(doc.docid());
			list.add(doc.tuple());
		}
		return list;
	}

	@Override
	public boolean isExpired(String bankCode) {
		BankLicense license=getBankLicense(bankCode);
		if(license==null)return true;
//		license.getToken();//商户向平台申请token，成功后其金证银行拥有牌照，系统由于仍在netos上部署无商户无关，因此校验程序在此校验，发现过期则转为冻结
		return System.currentTimeMillis()-license.getExpiryDate()>0;
	}

}
