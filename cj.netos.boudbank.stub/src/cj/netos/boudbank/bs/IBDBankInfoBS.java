package cj.netos.boudbank.bs;

import java.util.List;

import cj.netos.boudbank.args.BankInfo;

public interface IBDBankInfoBS {

	void saveBank(BankInfo info);

	void updateBankName(String bank, String name);

	void updateBankPresident(String bank, String president);

	void updateBankCompany(String bank, String company);

	BankInfo getBankInfo(String bank);

	List<BankInfo> pageBankInfo(int currPage, int pageSize);


}
