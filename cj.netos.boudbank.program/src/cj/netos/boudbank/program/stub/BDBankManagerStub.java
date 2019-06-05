package cj.netos.boudbank.program.stub;

import java.util.List;

import cj.netos.boudbank.args.BState;
import cj.netos.boudbank.args.BankInfo;
import cj.netos.boudbank.args.BankLicense;
import cj.netos.boudbank.args.BankState;
import cj.netos.boudbank.bs.IBDBankInfoBS;
import cj.netos.boudbank.bs.IBDBankLicenseBS;
import cj.netos.boudbank.bs.IBDBankStateBS;
import cj.netos.boudbank.stub.IBDBankManagerStub;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;
import cj.ultimate.util.StringUtil;

@CjService(name = "/manager.service")
public class BDBankManagerStub extends GatewayAppSiteRestStub implements IBDBankManagerStub {
	@CjServiceRef(refByName = "BDBAEngine.bdBankInfoBS")
	IBDBankInfoBS bdBankInfoBS;

	@CjServiceRef(refByName = "BDBAEngine.bdBankStateBS")
	IBDBankStateBS bdBankStateBS;

	@CjServiceRef(refByName = "BDBAEngine.bdBankLicenseBS")
	IBDBankLicenseBS bdBankLicenseBS;

	@Override
	public String registerBank(String bankName, String president, String company) throws CircuitException {
		if (StringUtil.isEmpty(bankName)) {
			throw new CircuitException("404", String.format("银行名为空"));
		}
		if (StringUtil.isEmpty(president)) {
			throw new CircuitException("404", String.format("行长为空"));
		}
		BankInfo info = new BankInfo();
		info.setCode(null);
		info.setPresident(president);
		info.setCompany(company);
		info.setName(bankName);
		info.setCtime(System.currentTimeMillis());
		BankState state = new BankState();
		state.setState(BState.opened);
		info.setBstate(state.getState());

		bdBankInfoBS.saveBank(info);
		// 插入营业状态为正常
		state.setBank(info.getCode());
		state.setCtime(System.currentTimeMillis());
		bdBankStateBS.save(state);
		return info.getCode();
	}

	@Override
	public void updateBankName(String bank, String name) throws CircuitException {
		bdBankInfoBS.updateBankName(bank, name);
	}

	@Override
	public void updateBankPresident(String bank, String president) throws CircuitException {
		bdBankInfoBS.updateBankPresident(bank, president);
	}

	@Override
	public void updateBankCompany(String bank, String company) throws CircuitException {
		bdBankInfoBS.updateBankCompany(bank, company);
	}

	@Override
	public String issueBankLicense(String bank, long issueDate, long expiryDate, String presidentPwd)
			throws CircuitException {
		BankInfo info = bdBankInfoBS.getBankInfo(bank);
		if (info == null) {
			throw new CircuitException("404", String.format("银行不存在：" + bank));
		}
		if (StringUtil.isEmpty(info.getPresident())) {
			throw new CircuitException("404", String.format("未指定行长"));
		}
		if (StringUtil.isEmpty(presidentPwd)) {
			throw new CircuitException("404", String.format("未指定行行登录密码"));
		}
		long currTime = System.currentTimeMillis();
		if (issueDate < currTime || expiryDate < currTime) {
			throw new CircuitException("500", String.format("颁发日期或过期日期无效"));
		}
		BankLicense license = new BankLicense();
		license.setBank(bank);
		license.setCompany(info.getCompany());
		license.setCtime(currTime);
		license.setExpiryDate(expiryDate);
		license.setIssueDate(issueDate);
		license.setPresident(info.getPresident());
		bdBankLicenseBS.saveLicense(presidentPwd, license);
		return license.getCode();
	}

	@Override
	public void revokeBank(String bankCode) throws CircuitException {
		bdBankStateBS.revokeBank(bankCode);
	}

	@Override
	public void freezeBank(String bankCode) {
		bdBankStateBS.freezeBank(bankCode);
	}

	@Override
	public void closedBank(String bankCode) {
		bdBankStateBS.closedBank(bankCode);
	}

	@Override
	public void resumeBank(String bankCode) {
		bdBankStateBS.resumeBank(bankCode);
	}

	@Override
	public BankInfo getBankInfo(String bankCode) {
		return bdBankInfoBS.getBankInfo(bankCode);
	}

	@Override
	public BankLicense getBankLicense(String bankCode) {
		return bdBankLicenseBS.getBankLicense(bankCode);
	}

	@Override
	public List<BankInfo> pageBankInfo(int currPage, int pageSize) {
		return bdBankInfoBS.pageBankInfo(currPage, pageSize);
	}

	@Override
	public List<BankLicense> pageBankLicense(int currPage, int pageSize) {
		return bdBankLicenseBS.pageBankLicense(currPage, pageSize);
	}

	@Override
	public BState getBankState(String bankCode) {
		return bdBankStateBS.getState(bankCode).getState();
	}

}
