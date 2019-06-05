package cj.netos.boudbank.program;

import java.util.HashMap;
import java.util.Map;

import cj.netos.boudbank.args.BankInfo;
import cj.netos.boudbank.bs.IBDBankInfoBS;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;

@CjService(name = "bankCacher")
public class BankCacher implements IBankCacher {
	@CjServiceRef(refByName = "FSBAEngine.bdBankInfoBS")
	IBDBankInfoBS bdBankInfoBS;
	Map<String, BankInfo> bankInfos;

	public BankCacher() {
		bankInfos = new HashMap<String, BankInfo>();
	}


	@Override
	public BankInfo getBankInfo(String bank) {
		BankInfo r = bankInfos.get(bank);
		if (r != null) {
			return r;
		}
		r = bdBankInfoBS.getBankInfo(bank);
		bankInfos.put(bank, r);
		return r;
	}

}
