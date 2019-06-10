package cj.netos.bondbank.plugin.BDBKEngine.bs;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.netos.bondbank.args.ExchangeBill;
import cj.netos.bondbank.bs.IBDBankExchangeBillBS;
import cj.netos.bondbank.bs.IBDBankTransactionBS;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceSite;
@CjService(name="bdBankExchangeBillBS")
public class BDBankExchangeBillBS implements IBDBankExchangeBillBS {
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
	public ExchangeBill getBill(String bankno, String billno) {
		String cjql = String.format("select {'tuple':'*'} from tuple %s %s where {'_id':ObjectId('%s')}",
				IBDBankTransactionBS.TABLE_Exchanges, ExchangeBill.class.getName(), billno);
		IQuery<ExchangeBill> q = getBankCube(bankno).createQuery(cjql);
		IDocument<ExchangeBill> doc = q.getSingleResult();
		if (doc == null)
			return null;
		doc.tuple().setCode(doc.docid());
		return doc.tuple();
	}

}
