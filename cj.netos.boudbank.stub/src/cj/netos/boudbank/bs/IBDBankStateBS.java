package cj.netos.boudbank.bs;

import cj.netos.boudbank.args.BankState;

public interface IBDBankStateBS {

	void save(BankState state);

	void revokeBank(String bankCode);

	void freezeBank(String bankCode);

	void closedBank(String bankCode);

	void resumeBank(String bankCode);

	BankState getState(String bankCode);

}
