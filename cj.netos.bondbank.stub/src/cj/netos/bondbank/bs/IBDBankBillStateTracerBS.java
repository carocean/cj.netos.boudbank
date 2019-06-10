package cj.netos.bondbank.bs;

import cj.netos.bondbank.args.BillState;

/**
 * 订单状态根踪器。<br>
 * 收到客户请求-向金证银行提交请求-收到金证银行扣款通知-完成扣账<br>
 * 在此期单可能出错，所以需要全程跟踪
 * 
 * @author caroceanjofers
 *
 */
public interface IBDBankBillStateTracerBS {
	void updateState(String bank, String billno, String billName, BillState state);

	BillState getState(String bank, String billno, String billName);
}
