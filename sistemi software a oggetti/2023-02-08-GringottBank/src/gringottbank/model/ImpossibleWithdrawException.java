package gringottbank.model;

import java.util.Optional;

public class ImpossibleWithdrawException extends Exception {

	private static final long serialVersionUID = 1L;
	private Optional<Amount> amount;

	public ImpossibleWithdrawException() {
		amount = Optional.empty();
	}

	public ImpossibleWithdrawException(String s) {
		super(s);
		amount = Optional.empty();
	}
	
	public ImpossibleWithdrawException(String s, Amount amount) {
		super(s);
		this.amount=Optional.of(amount);
	}

	public Optional<Amount> getAmount() {
		return amount;
	}
	
}
