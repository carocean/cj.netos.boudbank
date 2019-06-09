package cj.netos.bondbank.program.stub;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cj.netos.bondbank.args.BState;
import cj.netos.bondbank.args.BankInfo;
import cj.netos.bondbank.args.BankState;
import cj.netos.bondbank.bs.IBDBankInfoBS;
import cj.netos.bondbank.bs.IBDBankStateBS;
import cj.netos.bondbank.stub.IBDBankManagerStub;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;
import cj.ultimate.util.StringUtil;

@CjService(name = "/manager.service")
public class BDBankManagerStub extends GatewayAppSiteRestStub implements IBDBankManagerStub {
	@CjServiceRef(refByName = "BDBKEngine.bdBankInfoBS")
	IBDBankInfoBS bdBankInfoBS;

	@CjServiceRef(refByName = "BDBKEngine.bdBankStateBS")
	IBDBankStateBS bdBankStateBS;


	@Override
	public String registerBank(String bankName, String fsbank, String president, String company, String expiredDate)
			throws CircuitException {
		if (StringUtil.isEmpty(bankName)) {
			throw new CircuitException("404", String.format("银行名为空"));
		}
		if (StringUtil.isEmpty(president)) {
			throw new CircuitException("404", String.format("行长为空"));
		}
		if (StringUtil.isEmpty(expiredDate)) {
			throw new CircuitException("404", String.format("到期日期为空"));
		}
		if (StringUtil.isEmpty(fsbank)) {
			throw new CircuitException("404", String.format("缺少所属的金证银行"));
		}
		
		BankInfo info = new BankInfo();
		info.setCode(null);
		info.setFsbank(fsbank);
		info.setPresident(president);
		info.setCompany(company);
		info.setName(bankName);
		info.setCtime(System.currentTimeMillis());
		BankState state = new BankState();
		state.setState(BState.opened);
		info.setBstate(state.getState());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date expire = null;
		try {
			expire = sdf.parse(expiredDate);
		} catch (ParseException e) {
			throw new CircuitException("500",e);
		}
		info.setExpiredTime(expire.getTime());

		bdBankInfoBS.saveBank(info);
		// 插入营业状态为正常
		state.setBank(info.getCode());
		state.setCtime(System.currentTimeMillis());
		bdBankStateBS.save(state);
		return info.getCode();
	}
	@Override
	public void updateFsBank(String bank, String fsbank) {
		bdBankInfoBS.updateFsBank(bank, fsbank);
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
	public List<BankInfo> pageBankInfo(int currPage, int pageSize) {
		return bdBankInfoBS.pageBankInfo(currPage, pageSize);
	}

	@Override
	public BState getBankState(String bankCode) {
		return bdBankStateBS.getState(bankCode).getState();
	}

}
