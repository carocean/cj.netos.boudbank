package cj.netos.bondbank.program.stub;

import java.math.BigDecimal;

import cj.netos.bondbank.args.EInvesterType;
import cj.netos.bondbank.stub.IBDBankTransactionStub;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceSite;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IReactor;

@CjService(name = "/transaction.service")
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
	public void invest(String bank, String investor, BigDecimal amount,EInvesterType type, String informAddress) {
		IReactor reactor = getReactor();
		Event e = new Event(bank, "transaction.invest");
		e.getParameters().put("informAddress", informAddress);
		e.getParameters().put("investor", investor);
		e.getParameters().put("amount", amount);
		e.getParameters().put("type", type);
		reactor.input(e);
	}

	@Override
	public void cashout(String bank, String balanceType, String cashoutor, String identity, BigDecimal reqAmount,
			String memo, String informAddress) {
		IReactor reactor = getReactor();
		Event e = new Event(bank, "transaction.cashout");
		e.getParameters().put("informAddress", informAddress);
		e.getParameters().put("cashoutor", cashoutor);
		e.getParameters().put("reqAmount", reqAmount);
		e.getParameters().put("identity", identity);
		e.getParameters().put("memo", memo);
		e.getParameters().put("balanceType", balanceType);
		reactor.input(e);
	}

	@Override
	public void exchange(String bank, String exchanger, BigDecimal bondQuantities, String informAddress) {
		IReactor reactor = getReactor();
		Event e = new Event(bank, "transaction.exchange");
		e.getParameters().put("informAddress", informAddress);
		e.getParameters().put("exchanger", exchanger);
		e.getParameters().put("bondQuantities", bondQuantities);
		reactor.input(e);
	}

	@Override
	public void issueStock(String bank, String issuer, BigDecimal bondQuantities, String informAddress) {
		IReactor reactor = getReactor();
		Event e = new Event(bank, "transaction.issueStock");
		e.getParameters().put("issuer", issuer);
		e.getParameters().put("bondQuantities", bondQuantities);
		reactor.input(e);
	}

}
