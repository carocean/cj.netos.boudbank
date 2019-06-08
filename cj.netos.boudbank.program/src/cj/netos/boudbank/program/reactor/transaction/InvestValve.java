package cj.netos.boudbank.program.reactor.transaction;

import java.math.BigDecimal;

import cj.netos.boudbank.args.EInvesterType;
import cj.netos.boudbank.bs.IBDBankTransactionBS;
import cj.netos.boudbank.program.IBankCacher;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IPipeline;
import cj.studio.util.reactor.IValve;

@CjService(name = "transaction.invest")
public class InvestValve implements IValve {
	@CjServiceRef
	IBankCacher bankCacher;
	@CjServiceRef(refByName = "BDBAEngine.bdBankTransactionBS")
	IBDBankTransactionBS bdBankTransactionBS;
	@Override
	public void flow(Event e, IPipeline pipeline) throws CircuitException {
		String invester = (String) e.getParameters().get("invester");
		String informAddress = (String) e.getParameters().get("informAddress");
		EInvesterType type=(EInvesterType)e.getParameters().get("type");
		BigDecimal amount = (BigDecimal) e.getParameters().get("amount");
		
		bdBankTransactionBS.investBill(e.getKey(),invester,amount,type,informAddress);
	}

}
