package cj.netos.bondbank.program.reactor.transaction;

import java.math.BigDecimal;

import cj.netos.bondbank.args.EInvesterType;
import cj.netos.bondbank.bs.IBDBankTransactionBS;
import cj.netos.bondbank.program.IBankCacher;
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
	@CjServiceRef(refByName = "BDBKEngine.bdBankTransactionBS")
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
