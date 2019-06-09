package cj.netos.bondbank.args;

import java.math.BigDecimal;
//债券出入库存量表。入库是增加记录，出库是删除和拆分记录
public class BondQuantitiesStock {
	String user;
	String source;//投单号
	ESourceType type;
	BigDecimal bondQuantities;//债券存量是统计出来
	BigDecimal bondFaceValue;//面值
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public BigDecimal getBondQuantities() {
		return bondQuantities;
	}
	public void setBondQuantities(BigDecimal bondQuantities) {
		this.bondQuantities = bondQuantities;
	}
	public BigDecimal getBondFaceValue() {
		return bondFaceValue;
	}
	public void setBondFaceValue(BigDecimal bondFaceValue) {
		this.bondFaceValue = bondFaceValue;
	}
	public ESourceType getType() {
		return type;
	}
	public void setType(ESourceType type) {
		this.type = type;
	}
	
}
