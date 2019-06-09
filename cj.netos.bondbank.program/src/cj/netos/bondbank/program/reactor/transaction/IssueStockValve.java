package cj.netos.bondbank.program.reactor.transaction;

import java.math.BigDecimal;

import cj.netos.bondbank.bs.IBDBankTransactionBS;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IPipeline;
import cj.studio.util.reactor.IValve;

@CjService(name = "transaction.issueStock")
public class IssueStockValve implements IValve {
	@CjServiceRef(refByName = "BDBKEngine.bdBankTransactionBS")
	IBDBankTransactionBS bdBankTransactionBS;
	@Override
	public void flow(Event e, IPipeline pipeline) throws CircuitException {
		String informAddress = (String) e.getParameters().get("address");
		String issuer = (String) e.getParameters().get("issuer");
		BigDecimal bondQuantities = (BigDecimal) e.getParameters().get("bondQuantities");
		
		bdBankTransactionBS.issueStockBill(e.getKey(), issuer,bondQuantities,informAddress);
	}

}
