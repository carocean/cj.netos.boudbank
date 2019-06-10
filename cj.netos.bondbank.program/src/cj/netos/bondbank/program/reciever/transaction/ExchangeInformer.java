package cj.netos.bondbank.program.reciever.transaction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.bs.IBDBalanceBS;
import cj.netos.bondbank.bs.IBDBankBillStateTracerBS;
import cj.netos.bondbank.bs.IBDBankExchangeBillBS;
import cj.netos.inform.Informer;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.Circuit;
import cj.studio.ecm.net.CircuitException;
import cj.studio.ecm.net.Frame;
import cj.studio.ecm.net.io.MemoryContentReciever;
import cj.studio.gateway.socket.app.IGatewayAppSiteResource;
import cj.studio.gateway.socket.app.IGatewayAppSiteWayWebView;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.gson2.com.google.gson.reflect.TypeToken;

@CjService(name = "/reciever/transaction/exchange.service")
public class ExchangeInformer implements IGatewayAppSiteWayWebView {
	@CjServiceRef(refByName = "$.netos.informer")
	Informer informer;
	@CjServiceRef(refByName = "BDBKEngine.bdBalanceBS")
	IBDBalanceBS bdBalanceBS;
	@CjServiceRef(refByName = "BDBKEngine.bdBankExchangeBillBS")
	IBDBankExchangeBillBS bdBankExchangeBillBS;
	@CjServiceRef(refByName = "BDBKEngine.bdBankBillStateTracerBS")
	IBDBankBillStateTracerBS bdBankBillStateTracerBS;

	@Override
	public void flow(Frame frame, Circuit circuit, IGatewayAppSiteResource resource) throws CircuitException {
		frame.content().accept(new MemoryContentReciever() {
			@Override
			public void done(byte[] b, int pos, int length) throws CircuitException {
				super.done(b, pos, length);
				calculateExchangeBill(frame, circuit, resource);
			}

		});
	}

	protected void calculateExchangeBill(Frame frame, Circuit circuit, IGatewayAppSiteResource resource)
			throws CircuitException {
		String bankno = frame.parameter("bankno");
		String billno = frame.parameter("billno");
		String json = new String(frame.content().readFully());
		Map<String, Object> response = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {
		}.getType());
		ExchangeBill bill = bdBankExchangeBillBS.getBill(bankno, billno);
		bill.setBondPrice(new BigDecimal(response.get("dealBondPrice") + ""));
		bill.setBondQuantities(new BigDecimal(response.get("dealBondQuantities") + ""));
		bill.setDeservedAmount(new BigDecimal(response.get("deservedAmount") + ""));
		bill.setSource(response.get("billno") + "");
		bill.setDealTime(new BigDecimal(response.get("dealtime") + "").longValue());
		bdBalanceBS.onAddExchangeBill(bankno, bill);
		
//		String informAddress=bill.getInformAddress();//结束后向调用者发起通知
		
	}

}
