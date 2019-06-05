package cj.netos.boudbank.args;

/**
 * 银行营业执照
 * 
 * @author caroceanjofers
 *
 */
public class BankLicense {
	String code;
	String token;
	String bank;
	String president;
	String company;
	long ctime;
	long issueDate;
	long expiryDate;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getPresident() {
		return president;
	}
	public void setPresident(String president) {
		this.president = president;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	public long getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(long issueDate) {
		this.issueDate = issueDate;
	}
	public long getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}
	
}
