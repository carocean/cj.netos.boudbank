package cj.netos.bondbank.plugin.BDBKEngine.bs;

import java.math.BigDecimal;

import org.bson.Document;
import org.bson.conversions.Bson;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.netos.bondbank.args.InvestBill;
import cj.netos.bondbank.bs.IBDBankInvestBillBS;
import cj.netos.bondbank.bs.IBDBankTransactionBS;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceSite;
@CjService(name="bdBankInvestBillBS")
public class BDBankInvestBillBS implements IBDBankInvestBillBS {
	private ICube cubeBank;
	@CjServiceSite
	IServiceSite site;

	protected ICube getBankCube(String bank) {
		if (cubeBank != null) {
			return cubeBank;
		}
		cubeBank = (ICube) site.getService("mongodb.bdbank." + bank + ":autocreate");
		return cubeBank;
	}

	@Override
	public InvestBill getBill(String bankno, String billno) {
		String cjql = String.format("select {'tuple':'*'} from tuple %s %s where {'_id':ObjectId('%s')}",
				IBDBankTransactionBS.TABLE_Invests, InvestBill.class.getName(), billno);
		IQuery<InvestBill> q = getBankCube(bankno).createQuery(cjql);
		IDocument<InvestBill> doc = q.getSingleResult();
		if (doc == null)
			return null;
		doc.tuple().setCode(doc.docid());
		return doc.tuple();
	}

	@Override
	public void updateBondQuantities(String bank,String billno, BigDecimal bondQuantities) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", billno));
		Bson update = Document.parse(String.format("{'$set':{'tuple.bondQuantities':'%s'}}", bondQuantities));
		getBankCube(bank).updateDocOne(IBDBankTransactionBS.TABLE_Invests, filter, update);
	}

	@Override
	public void updateBondFaceValue(String bank,String billno, BigDecimal bondFaceValue) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", billno));
		Bson update = Document.parse(String.format("{'$set':{'tuple.bondFaceValue':'%s'}}", bondFaceValue));
		getBankCube(bank).updateDocOne(IBDBankTransactionBS.TABLE_Invests, filter, update);
	}

	@Override
	public void updateMerchantBondQuantities(String bank,String billno, BigDecimal merchantBondQuantities) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", billno));
		Bson update = Document.parse(String.format("{'$set':{'tuple.merchantBondQuantities':'%s'}}", merchantBondQuantities));
		getBankCube(bank).updateDocOne(IBDBankTransactionBS.TABLE_Invests, filter, update);
	}

	@Override
	public void updateCustomerBondQuantities(String bank,String billno, BigDecimal customerBondQuantities) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", billno));
		Bson update = Document.parse(String.format("{'$set':{'tuple.customerBondQuantities':'%s'}}", customerBondQuantities));
		getBankCube(bank).updateDocOne(IBDBankTransactionBS.TABLE_Invests, filter, update);
	}
	@Override
	public void updateBondPrice(String bank,String billno, BigDecimal bondPrice) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", billno));
		Bson update = Document.parse(String.format("{'$set':{'tuple.bondPrice':'%s'}}", bondPrice));
		getBankCube(bank).updateDocOne(IBDBankTransactionBS.TABLE_Invests, filter, update);
	}
}
