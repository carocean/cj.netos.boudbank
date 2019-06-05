package cj.netos.boudbank.stub;

import java.util.ArrayList;
import java.util.List;

import cj.netos.boudbank.args.BState;
import cj.netos.boudbank.args.BankInfo;
import cj.netos.boudbank.args.BankLicense;
import cj.studio.ecm.net.CircuitException;
import cj.studio.gateway.stub.annotation.CjStubInParameter;
import cj.studio.gateway.stub.annotation.CjStubMethod;
import cj.studio.gateway.stub.annotation.CjStubReturn;
import cj.studio.gateway.stub.annotation.CjStubService;

@CjStubService(bindService = "/manager.service", usage = "债券银行管理")
public interface IBDBankManagerStub {
	@CjStubMethod(usage = "注册银行，该方法仅是创建银行主体信息，稍后要调用其它方法以完善资料")
	@CjStubReturn(usage = "返回行号")
	String registerBank(@CjStubInParameter(key = "bankName", usage = "银行名") String bankName,
			@CjStubInParameter(key = "president", usage = "行长") String president,
			@CjStubInParameter(key = "company", usage = "公司") String company) throws CircuitException;

	@CjStubMethod(usage = "更新银行名")
	void updateBankName(@CjStubInParameter(key = "bank", usage = "银行标识编码") String bank,
			@CjStubInParameter(key = "name", usage = "银行名") String name) throws CircuitException;

	@CjStubMethod(usage = "更新银行行长")
	void updateBankPresident(@CjStubInParameter(key = "bank", usage = "银行标识编码") String bank,
			@CjStubInParameter(key = "president", usage = "行长") String president) throws CircuitException;

	@CjStubMethod(usage = "更新银行归属企业")
	void updateBankCompany(@CjStubInParameter(key = "bank", usage = "银行标识编码") String bank,
			@CjStubInParameter(key = "company", usage = "归属企业") String company) throws CircuitException;

	@CjStubMethod(usage = "颁发银行牌照")
	@CjStubReturn(usage = "执照标识")
	String issueBankLicense(@CjStubInParameter(key = "bank", usage = "银行") String bank,
			@CjStubInParameter(key = "issueDate", usage = "颁发且生效日期") long issueDate,
			@CjStubInParameter(key = "expiryDate", usage = "过期日期") long expiryDate,
			@CjStubInParameter(key = "presidentPwd", usage = "行长密码，要在license内容中指定行长") String presidentPwd)
			throws CircuitException;

	@CjStubMethod(usage = "吊销指定的银行，吊销并不是删除，只是改变状态为吊销")
	void revokeBank(@CjStubInParameter(key = "bankCode", usage = "银行代码") String bankCode) throws CircuitException;

	@CjStubMethod(usage = "冻结指定的银行")
	void freezeBank(@CjStubInParameter(key = "bankCode", usage = "银行代码") String bankCode);

	@CjStubMethod(usage = "暂停运行指定的银行")
	void closedBank(@CjStubInParameter(key = "bankCode", usage = "银行代码") String bankCode);

	@CjStubMethod(usage = "恢复运行指定的银行")
	void resumeBank(@CjStubInParameter(key = "bankCode", usage = "银行代码") String bankCode);

	@CjStubMethod(usage = "获取指定的银行信息")
	BankInfo getBankInfo(@CjStubInParameter(key = "bankCode", usage = "银行代码") String bankCode);

	@CjStubMethod(usage = "获取指定的银行信息")
	BankLicense getBankLicense(@CjStubInParameter(key = "bankCode", usage = "银行代码") String bankCode);

	@CjStubMethod(usage = "分页查询银行列表")
	@CjStubReturn(elementType = ArrayList.class, type = BankInfo.class, usage = "银行列表")
	List<BankInfo> pageBankInfo(@CjStubInParameter(key = "currPage", usage = "当前页码") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "页大小") int pageSize);

	@CjStubMethod(usage = "分页查询银行列表")
	@CjStubReturn(elementType = ArrayList.class, type = BankLicense.class, usage = "营业执照列表")
	List<BankLicense> pageBankLicense(@CjStubInParameter(key = "currPage", usage = "当前页码") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "页大小") int pageSize);

	@CjStubMethod(usage = "获取银行当前状态")
	BState getBankState(@CjStubInParameter(key = "bankCode", usage = "银行代码") String bankCode);

}
