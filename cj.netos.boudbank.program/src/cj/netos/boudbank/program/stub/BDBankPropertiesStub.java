package cj.netos.boudbank.program.stub;

import cj.netos.boudbank.bs.IBDBankPropertiesBS;
import cj.netos.boudbank.stub.IBDBankPropertiesStub;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;
@CjService(name="/properties.service")
public class BDBankPropertiesStub extends GatewayAppSiteRestStub implements IBDBankPropertiesStub {
	@CjServiceRef(refByName = "BDBAEngine.bdBankPropertiesBS")
	IBDBankPropertiesBS bdBankPropertiesBS;
	@Override
	public void put(String bank, String key, String value,String desc) {
		bdBankPropertiesBS.put(bank,key,value,desc);
	}

	@Override
	public String get(String bank, String key) {
		return bdBankPropertiesBS.get(bank,key);
	}

	@Override
	public String[] enumKey(String bank) {
		return bdBankPropertiesBS.enumKey(bank);
	}
	@Override
	public String desc(String bank, String key) {
		return bdBankPropertiesBS.desc(bank,key);
	}
	@Override
	public String[] pageKeys(String bank, int currPage, int pageSize) {
		return bdBankPropertiesBS.pageKeys(bank,currPage,pageSize);
	}

	@Override
	public long count(String bank) {
		return bdBankPropertiesBS.count(bank);
	}
	@Override
	public void remove(String bank, String key) {
		bdBankPropertiesBS.remove(bank, key);;
	}

}
