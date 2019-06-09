package cj.netos.bondbank.program.reciever.transaction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cj.netos.bondbank.args.InvestBill;
import cj.netos.bondbank.bs.IBDBankInvestBillBS;
import cj.netos.bondbank.util.BigDecimalConstants;
import cj.netos.inform.Informer;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.Circuit;
import cj.studio.ecm.net.CircuitException;
import cj.studio.ecm.net.Frame;
import cj.studio.ecm.net.io.MemoryContentReciever;
import cj.studio.ecm.net.io.MemoryOutputChannel;
import cj.studio.gateway.socket.app.IGatewayAppSiteResource;
import cj.studio.gateway.socket.app.IGatewayAppSiteWayWebView;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.gson2.com.google.gson.reflect.TypeToken;

@CjService(name = "/reciever/transaction/invest.service")
public class InvestInformer implements IGatewayAppSiteWayWebView {
	@CjServiceRef(refByName = "BDBKEngine.bdBankInvestBillBS")
	IBDBankInvestBillBS bdBankInvestBillBS;

	@CjServiceRef(refByName = "$.netos.informer")
	Informer informer;

	@Override
	public void flow(Frame frame, Circuit circuit, IGatewayAppSiteResource resource) throws CircuitException {
		frame.content().accept(new MemoryContentReciever() {
			@Override
			public void done(byte[] b, int pos, int length) throws CircuitException {
				super.done(b, pos, length);
				calculateInvestBill(frame, circuit, resource);
			}
		});

	}

	protected void calculateInvestBill(Frame frame, Circuit circuit, IGatewayAppSiteResource resource)
			throws CircuitException {
		String bankno = frame.parameter("bankno");
		String billno = frame.parameter("billno");
		String json = new String(frame.content().readFully());
		Map<String, Object> response = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {
		}.getType());
		InvestBill bill = bdBankInvestBillBS.getBill(bankno, billno);
		BigDecimal bondQuantities = new BigDecimal(response.get("bondQuantities") + "");
		this.bdBankInvestBillBS.updateBondQuantities(bankno,billno,bondQuantities);
		BigDecimal bondFaceValue = bill.getTotalBondAmount().divide(bondQuantities, BigDecimalConstants.scale,
				BigDecimalConstants.roundingMode);
		this.bdBankInvestBillBS.updateBondFaceValue(bankno,billno,bondFaceValue);
		BigDecimal bondAllRate = bill.getMerchantBondRate().add(bill.getCustomerBondRate());
		BigDecimal merchantBondQuantities = bondQuantities.multiply(bill.getMerchantBondRate().divide(bondAllRate,
				BigDecimalConstants.scale, BigDecimalConstants.roundingMode));
		this.bdBankInvestBillBS.updateMerchantBondQuantities(bankno,billno,merchantBondQuantities);
		BigDecimal customerBondQuantities = bondQuantities.multiply(bill.getCustomerBondRate().divide(bondAllRate,
				BigDecimalConstants.scale, BigDecimalConstants.roundingMode));
		this.bdBankInvestBillBS.updateCustomerBondQuantities(bankno,billno,customerBondQuantities);
		
		BigDecimal bondPrice=new BigDecimal(response.get("oldBondPrice")+"");
		this.bdBankInvestBillBS.updateBondPrice(bankno, billno, bondPrice);
		
		bill.setBondQuantities(bondQuantities);
		bill.setBondFaceValue(bondFaceValue);
		bill.setMerchantBondQuantities(merchantBondQuantities);
		bill.setCustomerBondQuantities(customerBondQuantities);
		bill.setBondPrice(bondPrice);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bill", new Gson().toJson(bill));
		Frame f = informer.createFrame(bill.getInformAddress(), map);
		MemoryOutputChannel oc = new MemoryOutputChannel();
		Circuit c = new Circuit(oc, "http/1.1 200 ok");
		informer.inform(f, c);
	}

}
