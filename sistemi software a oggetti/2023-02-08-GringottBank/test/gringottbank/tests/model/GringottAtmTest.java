package gringottbank.tests.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gringottbank.model.Atm;
import gringottbank.model.Coin;
import gringottbank.model.CoinAmount;
import gringottbank.model.Currency;
import gringottbank.model.CurrencyAmount;
import gringottbank.model.GringottAtm;
import gringottbank.model.ImpossibleWithdrawException;

class GringottAtmTest {

	private static Atm atm;
	
	@BeforeAll
	static void setupAtm() {
		atm = new GringottAtm();
	}
	
	@Test
	void testWithdrawCoinOK1() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
		*/
		var amount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 14).plus(Coin.GALLEON, 3);
		assertEquals(3, amount.getQuantity(Coin.GALLEON));
		assertEquals(14, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		var withdrawnAmount = atm.withdraw(amount);
		assertEquals(amount, withdrawnAmount);
	}
	
	@Test
	void testWithdrawCoinOK2() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
		*/
		var amount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 4).plus(Coin.GALLEON, 3);
		assertEquals(3, amount.getQuantity(Coin.GALLEON));
		assertEquals(4, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		var withdrawnAmount = atm.withdraw(amount);
		var expectedAmount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 21).plus(Coin.GALLEON, 2);
		assertEquals(expectedAmount, withdrawnAmount);
	}
	
	@Test
	void testWithdrawCoinOK3() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
	   	ESEMPI
		20 galeoni --> 19 galeoni + 17 falci
		*/
		var amount = new CoinAmount().plus(Coin.GALLEON, 20);
		assertEquals(20, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		var withdrawnAmount = atm.withdraw(amount);
		var expectedAmount = new CoinAmount().plus(Coin.SICKLE, 17).plus(Coin.GALLEON, 19);
		assertEquals(expectedAmount, withdrawnAmount);
	}

	@Test
	void testWithdrawCoinOK4() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
	   	ESEMPI
		19 galeoni e 20 zellini -->  18 galeoni + 17 falci + 20 zellini
		*/
		var amount = new CoinAmount().plus(Coin.GALLEON, 19).plus(Coin.KNUT, 20);
		assertEquals(19, amount.getQuantity(Coin.GALLEON));
		assertEquals( 0, amount.getQuantity(Coin.SICKLE));
		assertEquals(20, amount.getQuantity(Coin.KNUT));
		var withdrawnAmount = atm.withdraw(amount);
		var expectedAmount = new CoinAmount().plus(Coin.SICKLE, 17).plus(Coin.GALLEON, 18).plus(Coin.KNUT, 20);
		assertEquals(expectedAmount, withdrawnAmount);
	}

	@Test
	void testWithdrawCurrencyEUR() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
	   	ESEMPI
		500 euro --> (1 galeone = € 5.66) 88 galeoni + 5 falci + 22 zellini (importo effettivo: 499,67 €)
		*/
		var amount = new CurrencyAmount(500, Currency.EUR);
		assertEquals(500, amount.getQuantity(), 0.01);
		var withdrawnAmount = atm.withdraw(amount);
		var expectedAmount = new CoinAmount().plus(Coin.GALLEON, 88).plus(Coin.SICKLE, 5).plus(Coin.KNUT, 22);
		assertEquals(expectedAmount, withdrawnAmount);
	}
	
	@Test
	void testWithdrawCurrencyUSD() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
	   	ESEMPI
		500 dollari --> (1 galeone = $ 6) 83 galeoni + 5 falci + 19 zellini (importo effettivo: 499,67 €)
		*/
		var amount = new CurrencyAmount(500, Currency.USD);
		assertEquals(500, amount.getQuantity(), 0.01);
		var withdrawnAmount = atm.withdraw(amount);
		var expectedAmount = new CoinAmount().plus(Coin.GALLEON, 83).plus(Coin.SICKLE, 5).plus(Coin.KNUT, 19);
		assertEquals(expectedAmount, withdrawnAmount);
	}
	
	@Test
	void testWithdrawCurrencyGBP() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
	   	ESEMPI
		500 sterline --> (1 galeone = £ 5) 100 galeoni --> (regola 2) --> 99 galeoni + 17 falci
		*/
		var amount = new CurrencyAmount(500, Currency.GBP);
		assertEquals(500, amount.getQuantity(), 0.01);
		var withdrawnAmount = atm.withdraw(amount);
		var expectedAmount = new CoinAmount().plus(Coin.GALLEON, 99).plus(Coin.SICKLE, 17);
		assertEquals(expectedAmount, withdrawnAmount);
	}
	
	@Test
	void testNormalizeOK1() {
		var amount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 4).plus(Coin.GALLEON, 3);
		assertEquals(3, amount.getQuantity(Coin.GALLEON));
		assertEquals(4, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		if (atm instanceof GringottAtm gAtm) {
			var withdrawnAmount = gAtm.normalize(amount);
			assertEquals(3, withdrawnAmount.getQuantity(Coin.GALLEON));
			assertEquals(4, withdrawnAmount.getQuantity(Coin.SICKLE));
			assertEquals(5, withdrawnAmount.getQuantity(Coin.KNUT));
		}
	}
	
	@Test
	void testNormalizeOK2() {
		var amount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 21).plus(Coin.GALLEON, 3);
		assertEquals(3, amount.getQuantity(Coin.GALLEON));
		assertEquals(21, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		if (atm instanceof GringottAtm gAtm) {
			var withdrawnAmount = gAtm.normalize(amount);
			assertEquals(4, withdrawnAmount.getQuantity(Coin.GALLEON));
			assertEquals(4, withdrawnAmount.getQuantity(Coin.SICKLE));
			assertEquals(5, withdrawnAmount.getQuantity(Coin.KNUT));
		}
	}
	
	@Test
	void testNormalizeOK3() {
		var amount = new CoinAmount().plus(Coin.KNUT, 35).plus(Coin.SICKLE, 21).plus(Coin.GALLEON, 3);
		assertEquals(3, amount.getQuantity(Coin.GALLEON));
		assertEquals(21, amount.getQuantity(Coin.SICKLE));
		assertEquals(35, amount.getQuantity(Coin.KNUT));
		if (atm instanceof GringottAtm gAtm) {
			var withdrawnAmount = gAtm.normalize(amount);
			assertEquals(4, withdrawnAmount.getQuantity(Coin.GALLEON));
			assertEquals(5, withdrawnAmount.getQuantity(Coin.SICKLE));
			assertEquals(6, withdrawnAmount.getQuantity(Coin.KNUT));
		}
	}

}
