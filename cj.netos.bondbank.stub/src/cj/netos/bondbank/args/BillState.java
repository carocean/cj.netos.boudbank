package cj.netos.bondbank.args;
/**
 * 订单状态<br>
 * 订单一般由本银行发起申请，到多证银行处理，并在收到回调时处理结束
 * @author caroceanjofers
 *
 */
public class BillState {
	EState state;
	String message;
	long ctime;
	public BillState() {
	}
	
	public BillState(EState state, String message) {
		this.state = state;
		this.message = message;
		this.ctime=System.currentTimeMillis();
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public EState getState() {
		return state;
	}
	public void setState(EState state) {
		this.state = state;
	}
}
