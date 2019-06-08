package cj.netos.boudbank.bs;

import java.util.List;

import cj.netos.boudbank.args.BankLicense;
import cj.studio.ecm.net.CircuitException;

public interface IBDBankLicenseBS {
	static String TABLE_LISENCE = "lisences";

	BankLicense getBankLicense(String bank);

	void saveLicense(String presidentPwd, BankLicense license) throws CircuitException;

	List<BankLicense> pageBankLicense(int currPage, int pageSize);

	boolean hasLicenseOfBank(String bank);

	boolean isExpired(String bankCode);

}
