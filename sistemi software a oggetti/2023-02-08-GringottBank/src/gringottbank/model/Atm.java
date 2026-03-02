package gringottbank.model;

public interface Atm {
	public Amount withdraw(CurrencyAmount desiredAmount) throws ImpossibleWithdrawException;
	public Amount withdraw(CoinAmount desiredAmount) throws ImpossibleWithdrawException;
}
