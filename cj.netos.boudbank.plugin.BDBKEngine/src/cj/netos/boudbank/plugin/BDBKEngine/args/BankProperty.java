package cj.netos.boudbank.plugin.BDBKEngine.args;

public class BankProperty {
	public final static transient String CONSTANS_KEY_MERCHANTBONDRATE = "merchantBondRate";
	public final static transient String CONSTANS_KEY_CUSTOMERBONDRATE = "customerBondRate";
	public final static transient String CONSTANS_KEY_FEERATE = "feeRate";
	String key;
	String value;
	String desc;
	String bank;

	public BankProperty() {
		// TODO Auto-generated constructor stub
	}

	public BankProperty(String bank, String key, String value,String desc) {
		this.bank = bank;
		this.key = key;
		this.value = value;
		this.desc=desc;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
}
