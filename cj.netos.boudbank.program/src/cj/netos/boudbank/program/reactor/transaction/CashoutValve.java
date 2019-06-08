package cj.netos.boudbank.program.reactor.transaction;

import java.math.BigDecimal;
import java.util.Map;

import cj.netos.boudbank.bs.IBDBankTransactionBS;
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

@CjService(name = "transaction.cashout")
public class CashoutValve implements IValve {
	@CjServiceRef(refByName = "$.netos.informer")
	Informer informer;
	@CjServiceRef(refByName = "BDBAEngine.bdBankTransactionBS")
	IBDBankTransactionBS bdBankTransactionBS;

	@Override
	public void flow(Event e, IPipeline pipeline) throws CircuitException{
		String informAddress = (String) e.getParameters().get("address");
		String cashoutor = (String) e.getParameters().get("cashoutor");
		String identity = (String) e.getParameters().get("identity");
		String memo = (String) e.getParameters().get("memo");
		String balanceType = (String) e.getParameters().get("balanceType");
		BigDecimal reqAmount = (BigDecimal) e.getParameters().get("reqAmount");
		Map<String, Object> map =bdBankTransactionBS.cashoutBill(e.getKey(), balanceType, cashoutor, identity, reqAmount, memo,informAddress);
		
		Frame frame = informer.createFrame(informAddress, map);
		MemoryOutputChannel oc = new MemoryOutputChannel();
		Circuit circuit = new Circuit(oc, "http/1.1 200 ok");
		informer.inform(frame, circuit);
	}

}
