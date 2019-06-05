package cj.netos.boudbank.program.reactor.vavles;

import java.math.BigDecimal;

import cj.netos.boudbank.bs.IBDBankTransactionBS;
import cj.netos.boudbank.program.IBankCacher;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IPipeline;
import cj.studio.util.reactor.IValve;

@CjService(name = "invest")
public class InvestValve implements IValve {
	@CjServiceRef
	IBankCacher bankCacher;
	@CjServiceRef(refByName = "BDBAEngine.bdBankTransactionBS")
	IBDBankTransactionBS bdBankTransactionBS;

	@Override
	public void flow(Event e, IPipeline pipeline) {
		String depositor = (String) e.getParameters().get("depositor");
		BigDecimal amount = (BigDecimal) e.getParameters().get("amount");
		
		bdBankTransactionBS.investBill(e.getKey(),depositor,amount);
	}

}
