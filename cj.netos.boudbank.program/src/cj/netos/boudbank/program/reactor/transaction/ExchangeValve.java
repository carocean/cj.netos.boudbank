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

@CjService(name = "transaction.exchange")
public class ExchangeValve implements IValve {
	@CjServiceRef(refByName = "$.netos.informer")
	Informer informer;
	@CjServiceRef(refByName = "BDBAEngine.bdBankTransactionBS")
	IBDBankTransactionBS bdBankTransactionBS;
	@Override
	public void flow(Event e, IPipeline pipeline) throws CircuitException {
		String informAddress = (String) e.getParameters().get("address");
		String exchanger = (String) e.getParameters().get("exchanger");
		BigDecimal bondQuantities = (BigDecimal) e.getParameters().get("bondQuantities");
		
		Map<String, Object> map =bdBankTransactionBS.exchangeBill(e.getKey(), exchanger,bondQuantities,informAddress);
		Frame frame = informer.createFrame(informAddress, map);
		MemoryOutputChannel oc = new MemoryOutputChannel();
		Circuit circuit = new Circuit(oc, "http/1.1 200 ok");
		informer.inform(frame, circuit);
	}

}
