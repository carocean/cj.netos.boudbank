package cj.netos.bondbank.bs;

import java.util.List;

import cj.netos.bondbank.args.BankInfo;
import cj.studio.ecm.net.CircuitException;

public interface IBDBankInfoBS {
	static String TABLE_BANK_INFO = "banks";
	void saveBank(BankInfo info);

	void updateBankName(String bank, String name);

	void updateBankPresident(String bank, String president);

	void updateBankCompany(String bank, String company);

	BankInfo getBankInfo(String bank);

	List<BankInfo> pageBankInfo(int currPage, int pageSize);

	boolean existsBankName(String name);

	boolean existsBankCode(String bank);

	boolean isExpired(String bank) throws CircuitException;

	void updateFsBank(String bank, String fsbank);


}
