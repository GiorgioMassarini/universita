package gringottbank.tests.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gringottbank.controller.Controller;
import gringottbank.model.Atm;
import gringottbank.model.Ceiling;
import gringottbank.model.Coin;
import gringottbank.model.CoinAmount;
import gringottbank.model.Currency;
import gringottbank.model.GringottAtm;
import gringottbank.model.ImpossibleWithdrawException;

class ControllerTest {

	private static Atm atm;
	private static List<Ceiling> ceilings;
	private static Controller controller;
	
	@BeforeAll
	static void setupAtm() {
		atm = new GringottAtm();
		ceilings = List.of(
					new Ceiling("Harry",	new CoinAmount().plus(Coin.GALLEON, 20)),
					new Ceiling("Hermione",	new CoinAmount().plus(Coin.GALLEON, 19).plus(Coin.KNUT, 20)),
					new Ceiling("Enrico",	new CoinAmount().plus(Coin.GALLEON, 200)),
					new Ceiling("Ambra",	new CoinAmount().plus(Coin.GALLEON, 100).plus(Coin.KNUT, 20)),
					new Ceiling("Roberta",	new CoinAmount().plus(Coin.GALLEON, 100).plus(Coin.KNUT, 28)),
					new Ceiling("Ron",		new CoinAmount().plus(Coin.GALLEON, 12).plus(Coin.SICKLE, 10).plus(Coin.KNUT, 6))
					);
		controller = new Controller(atm,ceilings);
	}
	
	@Test
	void testAccessorOK() {
		assertSame(atm, controller.getAtm());
		assertEquals(ceilings, controller.getCeilings());
	}
	
	@Test
	void testGetClientsOK() {
		assertEquals(List.of("Harry","Hermione","Enrico","Ambra","Roberta","Ron"), controller.getClients());
	}
	
	@Test
	void testWithdrawCoin_Enrico_OK() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
		*/
		var withdrawnAmount = controller.withdraw("Enrico", 20,14,5);
		assertNotNull(withdrawnAmount);
		var expectedAmount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 14).plus(Coin.GALLEON, 20);		
		assertEquals(expectedAmount, withdrawnAmount);
	}
	
	@Test
	void testWithdrawCoin_Hermione_KO() {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
		*/
		//var amount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 14).plus(Coin.GALLEON, 20);		
		assertThrows(ImpossibleWithdrawException.class, () -> controller.withdraw("Hermione", 20,14,5));
	}
	
	@Test
	void testWithdrawCurrencyEUR_Enrico_OK() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
		*/
		var withdrawnAmount = controller.withdraw("Enrico", 100, Currency.EUR);
		assertNotNull(withdrawnAmount);
		var expectedAmount = new CoinAmount().plus(Coin.KNUT, 10).plus(Coin.SICKLE, 11).plus(Coin.GALLEON, 17);
		assertEquals(expectedAmount, withdrawnAmount);
	}
	
	@Test
	void testWithdrawCurrencyEUR_Enrico_KO() throws ImpossibleWithdrawException {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
		*/
		assertThrows(ImpossibleWithdrawException.class, () -> controller.withdraw("Enrico", 1200, Currency.EUR));
		var expectedAmount = new CoinAmount().plus(Coin.KNUT, 6).plus(Coin.SICKLE, 17).plus(Coin.GALLEON, 211);
		try {
			controller.withdraw("Enrico", 1200, Currency.EUR);
		}
		catch(ImpossibleWithdrawException e) {
			assertEquals(expectedAmount, e.getAmount().get());
		}
	}
	
	@Test
	void testWithdrawCoin_Hermione() {
		/* 3) L’importo in monete Gringott deve prevedere il massimo numero di galeoni possibile, 
		  	  garantendo tuttavia che siano sempre essere erogati almeno 5 falci d’argento. 
		   4) Nel caso di importi specificati in Dollari, Euro o Sterline, deve essere usato il 
		   	  tasso di cambio fisso prestabilito
		*/
		//var amount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 14).plus(Coin.GALLEON, 20);		
		assertThrows(ImpossibleWithdrawException.class, () -> controller.withdraw("Hermione", 20,14,5));
		var expectedAmount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 14).plus(Coin.GALLEON, 20);
		try {
			controller.withdraw("Hermione", 20,14,5);
		}
		catch(ImpossibleWithdrawException e) {
			assertEquals(expectedAmount, e.getAmount().get());
		}
	}
}
