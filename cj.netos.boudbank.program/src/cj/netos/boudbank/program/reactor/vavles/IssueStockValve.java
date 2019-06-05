package cj.netos.boudbank.program.reactor.vavles;

import java.math.BigDecimal;

import cj.netos.boudbank.bs.IBDBankTransactionBS;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IPipeline;
import cj.studio.util.reactor.IValve;

@CjService(name = "issueStock")
public class IssueStockValve implements IValve {
	@CjServiceRef(refByName = "BDBAEngine.bdBankTransactionBS")
	IBDBankTransactionBS bdBankTransactionBS;
	@Override
	public void flow(Event e, IPipeline pipeline) {
		String issuer = (String) e.getParameters().get("issuer");
		BigDecimal bondQuantities = (BigDecimal) e.getParameters().get("bondQuantities");
		
		bdBankTransactionBS.issueStockBill(e.getKey(), issuer,bondQuantities);
	}

}
