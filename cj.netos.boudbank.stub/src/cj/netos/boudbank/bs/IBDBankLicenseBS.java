package cj.netos.boudbank.bs;

import java.util.List;

import cj.netos.boudbank.args.BankLicense;

public interface IBDBankLicenseBS {

	BankLicense getBankLicense(String bank);

	void saveLicense(String presidentPwd, BankLicense license);

	List<BankLicense> pageBankLicense(int currPage, int pageSize);

}
