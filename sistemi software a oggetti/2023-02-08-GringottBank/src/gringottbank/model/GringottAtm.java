package gringottbank.model;

public class GringottAtm implements Atm {

	public CoinAmount normalize(CoinAmount amount) {
		int galleons = amount.getQuantity(Coin.GALLEON);
		int sickles =  amount.getQuantity(Coin.SICKLE);
		int knuts =    amount.getQuantity(Coin.KNUT);
		while (knuts > Coin.SICKLE.getDivider()) { knuts -=Coin.SICKLE.getDivider(); sickles++; }
		while (sickles > Coin.GALLEON.getDivider()) { sickles -=Coin.GALLEON.getDivider(); galleons++; }
		return new CoinAmount().plus(Coin.GALLEON, galleons).plus(Coin.SICKLE,sickles).plus(Coin.KNUT,knuts);
	} 

	/* 	3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		   garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   tasso di cambio fisso prestabilito
	   ESEMPI
		20 galeoni --> 19 galeoni + 17 falci
		19 galeoni e 20 zellini --> 18 galeoni + 17 falci + 20 zellini
		500 sterline --> (1 galeone = £ 5.00) 100 galeoni teorici --> 99 galeoni +17 falci
		500 euro     --> (1 galeone = € 5.66) 88 galeoni + 5 falci + 22 zellini (importo effettivo: 499,67 €)
		500 dollari  --> (1 galeone = $ 6.00) 83 galeoni + 5 falci + 19 zellini (importo effettivo: 499,67 €)
	*/

	@Override
	public Amount withdraw(CoinAmount amount) throws ImpossibleWithdrawException {
		if(amount.isNegative()) {
			throw new ImpossibleWithdrawException("ammontare da prelevare negativo");
		}
		CoinAmount result = new CoinAmount();
		int amountValue = amount.getValueInKnuts(); //valore totale in zellini
		//
		amountValue=amountValue-(5*Coin.SICKLE.getDivider()); //tolgo il valore delle 5 falci obbligatorie
		result.adder(Coin.SICKLE, 5); //aggiungo come prima cosa le 5 falci obbligatorie
		//
		while(amountValue>=493) { //valore di un galeone in zellini (17*29)
			amountValue=amountValue-(17*Coin.SICKLE.getDivider());
			result.adder(Coin.GALLEON, 1); //aggiungo più galeoni possibili
		}
		//
		while(amountValue>=29) { //valore di una falce in zellini (29)
			amountValue=amountValue-Coin.SICKLE.getDivider();
			result.adder(Coin.SICKLE, 1); //aggiungo più falci possibili
		}
		//
		result.adder(Coin.KNUT, amountValue); //aggiungo i zellini rimanenti (sicuramente meno di 29)
		return result;
	}

	@Override
	public Amount withdraw(CurrencyAmount amount) throws ImpossibleWithdrawException {
		if(amount.isNegative()) {
			throw new ImpossibleWithdrawException("ammontare da prelevare negativo");
		}
		Amount result = new CoinAmount();
		double quantitaCurrency = amount.getQuantity();
		double galloniIpotetici=0;
		if(amount.getCurrency().equals(Currency.EUR)) {
			galloniIpotetici=(quantitaCurrency/5.66);
		}
		if(amount.getCurrency().equals(Currency.GBP)) {
			galloniIpotetici=(quantitaCurrency/5);
		}
		if(amount.getCurrency().equals(Currency.USD)) {
			galloniIpotetici=(quantitaCurrency/6);
		}
		int valoreFinaleInZellini = (int) Math.floor(galloniIpotetici*17*29);
		CoinAmount valoreAsAmount = new CoinAmount(Coin.KNUT, valoreFinaleInZellini);
		result=withdraw(valoreAsAmount);
		return result;
	}
	
}
