package cj.netos.boudbank.program.reactor.vavles;

import java.math.BigDecimal;

import cj.netos.boudbank.bs.IBDBankTransactionBS;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IPipeline;
import cj.studio.util.reactor.IValve;

@CjService(name = "cashout")
public class CashoutValve implements IValve {

	@CjServiceRef(refByName = "BDBAEngine.bdBankTransactionBS")
	IBDBankTransactionBS bdBankTransactionBS;

	@Override
	public void flow(Event e, IPipeline pipeline) {
		String cashoutor = (String) e.getParameters().get("cashoutor");
		String identity = (String) e.getParameters().get("identity");
		String memo = (String) e.getParameters().get("memo");
		String balanceType = (String) e.getParameters().get("balanceType");
		BigDecimal reqAmount = (BigDecimal) e.getParameters().get("reqAmount");
		bdBankTransactionBS.cashoutBill(e.getKey(), balanceType, cashoutor, identity, reqAmount, memo);
	}

}
