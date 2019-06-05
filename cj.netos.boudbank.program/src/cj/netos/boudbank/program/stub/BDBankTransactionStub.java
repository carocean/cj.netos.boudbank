package cj.netos.boudbank.program.stub;

import java.math.BigDecimal;

import cj.netos.boudbank.stub.IBDBankTransactionStub;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceSite;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IReactor;
@CjService(name="/transaction.service")
public class BDBankTransactionStub extends GatewayAppSiteRestStub implements IBDBankTransactionStub {
	@CjServiceSite
	IServiceSite site;
	IReactor reactor;

	protected IReactor getReactor() {
		if (reactor == null) {
			reactor = (IReactor) site.getService("$.reactor");
		}
		return reactor;
	}
	@Override
	public void invest(String bank, String investor, BigDecimal amount) {
		IReactor reactor = getReactor();
		Event e = new Event(bank, "invest");
		e.getParameters().put("investor", investor);
		e.getParameters().put("amount", amount);
		reactor.input(e);
	}

	@Override
	public void cashout(String bank, String balanceType, String cashoutor, String identity, BigDecimal reqAmount,
			String memo) {
		IReactor reactor = getReactor();
		Event e = new Event(bank, "cashout");
		e.getParameters().put("cashoutor", cashoutor);
		e.getParameters().put("reqAmount", reqAmount);
		e.getParameters().put("identity", identity);
		e.getParameters().put("memo",memo);
		e.getParameters().put("balanceType",balanceType);
		reactor.input(e);
	}

	@Override
	public void exchange(String bank, String exchanger, BigDecimal bondQuantities) {
		IReactor reactor = getReactor();
		Event e = new Event(bank, "exchange");
		e.getParameters().put("exchanger", exchanger);
		e.getParameters().put("bondQuantities", bondQuantities);
		reactor.input(e);
	}

	@Override
	public void issueStock(String bank, String issuer, BigDecimal bondQuantities) {
		IReactor reactor = getReactor();
		Event e = new Event(bank, "issueStock");
		e.getParameters().put("issuer", issuer);
		e.getParameters().put("bondQuantities", bondQuantities);
		reactor.input(e);
	}

}
