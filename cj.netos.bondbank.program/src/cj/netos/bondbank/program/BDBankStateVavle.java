package cj.netos.bondbank.program;

import cj.netos.bondbank.args.BState;
import cj.netos.bondbank.args.BankState;
import cj.netos.bondbank.bs.IBDBankInfoBS;
import cj.netos.bondbank.bs.IBDBankStateBS;
import cj.netos.bondbank.stub.IBDBankManagerStub;
import cj.studio.ecm.Scope;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.ecm.net.Frame;
import cj.studio.gateway.socket.pipeline.IAnnotationInputValve;
import cj.studio.gateway.socket.pipeline.IIPipeline;
import cj.ultimate.util.StringUtil;

@CjService(name = "fSBankStateVavle", scope = Scope.multiton)
public class BDBankStateVavle implements IAnnotationInputValve {

	@CjServiceRef(refByName = "BDBKEngine.bdBankStateBS")
	IBDBankStateBS bdBankStateBS;
	@CjServiceRef(refByName = "BDBKEngine.bdBankInfoBS")
	IBDBankInfoBS bdBankInfoBS;

	@Override
	public void onActive(String inputName, IIPipeline pipeline) throws CircuitException {
		pipeline.nextOnActive(inputName, this);
	}

	@Override
	public void flow(Object request, Object response, IIPipeline pipeline) throws CircuitException {
		if (!(request instanceof Frame)) {
			pipeline.nextFlow(request, response, this);
			return;
		}
		Frame f = (Frame) request;
		if (IBDBankManagerStub.class.getName().equals(f.head("Rest-StubFace"))) {
			pipeline.nextFlow(request, response, this);
			return;
		}
		String bank = f.parameter("bankCode");
		if (StringUtil.isEmpty(bank)) {
			bank = f.parameter("bank");
		}
		if (StringUtil.isEmpty(bank)) {
			pipeline.nextFlow(request, response, this);
			return;
		}
		if (this.bdBankInfoBS.isExpired(bank)) {
			BankState state = new BankState();
			state.setBank(bank);
			state.setCtime(System.currentTimeMillis());
			state.setState(BState.freeze);
			state.setDesc("The license expires and the bank has been frozen. Please re-apply for the license.");
			bdBankStateBS.save(state);
		}
		BankState state = bdBankStateBS.getState(bank);
		switch (state.getState()) {
		case closed:
		case freeze:
		case revoke:
			throw new CircuitException("308", String.format("Bank:%s Denial of Service, Reasons:%s %s", bank, state.getState(),
					state.getDesc() == null ? "" : state.getDesc()));
		case opened:
			pipeline.nextFlow(request, response, this);
			break;
		}

	}

	@Override
	public void onInactive(String inputName, IIPipeline pipeline) throws CircuitException {
		pipeline.nextOnInactive(inputName, this);
	}

	@Override
	public int getSort() {
		return 0;
	}

}
