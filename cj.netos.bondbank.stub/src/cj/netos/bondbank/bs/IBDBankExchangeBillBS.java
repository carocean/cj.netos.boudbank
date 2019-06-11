package cj.netos.bondbank.bs;

import cj.netos.bondbank.args.ExchangeBill;

public interface IBDBankExchangeBillBS {

	ExchangeBill getBill(String bankno, String billno);

	void update(String bankno, ExchangeBill bill);
	
}
