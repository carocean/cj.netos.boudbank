package cj.netos.bondbank.program.reactor.transaction;

import java.math.BigDecimal;
import java.util.Map;

import cj.netos.bondbank.bs.IBDBankTransactionBS;
import cj.netos.inform.Informer;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.Circuit;
import cj.studio.ecm.net.CircuitException;
import cj.studio.ecm.net.Frame;
import cj.studio.ecm.net.io.MemoryOutputChannel;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IPipeline;
import cj.studio.util.reactor.IValve;
import cj.ultimate.util.StringUtil;

@CjService(name = "transaction.cashout")
public class CashoutValve implements IValve {
	@CjServiceRef(refByName = "BDBKEngine.bdBankTransactionBS")
	IBDBankTransactionBS bdBankTransactionBS;
	@CjServiceRef(refByName = "$.netos.informer")
	Informer informer;

	@Override
	public void flow(Event e, IPipeline pipeline) throws CircuitException {
		String informAddress = (String) e.getParameters().get("informAddress");
		String cashoutor = (String) e.getParameters().get("cashoutor");
		String memo = (String) e.getParameters().get("memo");
		BigDecimal amount = (BigDecimal) e.getParameters().get("amount");
		Map<String, Object> map = bdBankTransactionBS.cashoutBill(e.getKey(), cashoutor, amount, memo);
		// 提现仅与当前银行的个人账户余额s有关，不需要到其它银行扣款。
		if (!StringUtil.isEmpty(informAddress)) {
			Frame f = informer.createFrame(informAddress, map);
			MemoryOutputChannel oc = new MemoryOutputChannel();
			Circuit c = new Circuit(oc, "http/1.1 200 ok");
			informer.inform(f, c);
		}
	}

}
