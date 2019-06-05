package cj.netos.boudbank.program.reactor.vavles;

import java.math.BigDecimal;

import cj.netos.boudbank.bs.IBDBankTransactionBS;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IPipeline;
import cj.studio.util.reactor.IValve;

@CjService(name = "exchange")
public class ExchangeValve implements IValve {
	@CjServiceRef(refByName = "BDBAEngine.bdBankTransactionBS")
	IBDBankTransactionBS bdBankTransactionBS;
	@Override
	public void flow(Event e, IPipeline pipeline) {
		String exchanger = (String) e.getParameters().get("exchanger");
		BigDecimal bondQuantities = (BigDecimal) e.getParameters().get("bondQuantities");
		
		bdBankTransactionBS.exchangeBill(e.getKey(), exchanger,bondQuantities);
	}

}
