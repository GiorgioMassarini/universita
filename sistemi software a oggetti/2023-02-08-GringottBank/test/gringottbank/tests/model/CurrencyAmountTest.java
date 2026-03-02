package gringottbank.tests.model;

import static org.junit.jupiter.api.Assertions.*;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gringottbank.model.Currency;
import gringottbank.model.CurrencyAmount;

class CurrencyAmountTest {
	
	private static NumberFormat formatter;

	@BeforeAll
	static void setup() {
		formatter = NumberFormat.getInstance(Locale.ITALY);
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
	}

	@Test
	void testCtor1_GBP() {
		var amount = new CurrencyAmount(100, Currency.GBP);
		assertEquals(100, amount.getQuantity(), 0.01);
		assertEquals(Currency.GBP, amount.getCurrency());
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testCtor1_USD() {
		var amount = new CurrencyAmount(150, Currency.USD);
		assertEquals(150, amount.getQuantity(), 0.01);
		assertEquals(Currency.USD, amount.getCurrency());
		assertFalse(amount.isNegative());
	}

	@Test
	void testCtor1_EUR() {
		var amount = new CurrencyAmount(200, Currency.EUR);
		assertEquals(200, amount.getQuantity(), 0.01);
		assertEquals(Currency.EUR, amount.getCurrency());
		assertFalse(amount.isNegative());
	}

	@Test
	void testCtor1KO_GBP() {
		assertThrows(IllegalArgumentException.class, () -> new CurrencyAmount(-300, Currency.GBP));
	}
	@Test
	void testCtor1KO_USD() {
		assertThrows(IllegalArgumentException.class, () -> new CurrencyAmount(-300, Currency.USD));
	}
	@Test
	void testCtor1KO_EUR() {
		assertThrows(IllegalArgumentException.class, () -> new CurrencyAmount(-300, Currency.EUR));
	}
	
	@Test
	void testToStringOK_GBP() {
		double importo = 100;
		var amount = new CurrencyAmount(importo, Currency.GBP);
		assertEquals(importo, amount.getQuantity(), 0.01);
		assertEquals(Currency.GBP, amount.getCurrency());
		assertFalse(amount.isNegative());
		assertEquals(formatter.format(importo) + " " + Currency.GBP.getName(), amount.toString());
	}
	
	@Test
	void testToStringOK_USD() {
		int importo = 150;
		var amount = new CurrencyAmount(importo, Currency.USD);
		assertEquals(importo, amount.getQuantity(), 0.01);
		assertEquals(Currency.USD, amount.getCurrency());
		assertFalse(amount.isNegative());
		assertEquals(formatter.format(importo) + " " + Currency.USD.getName(), amount.toString());
	}
	
	@Test
	void testToStringOK_EUR() {
		int importo = 200;
		var amount = new CurrencyAmount(importo, Currency.EUR);
		assertEquals(importo, amount.getQuantity(), 0.01);
		assertEquals(Currency.EUR, amount.getCurrency());
		assertFalse(amount.isNegative());
		assertEquals(formatter.format(importo) + " " + Currency.EUR.getName(), amount.toString());
	}
	
	@Test
	void testPlus_EUR() {
		var amount = new CurrencyAmount(200, Currency.EUR);
		assertEquals(200, amount.getQuantity(), 0.01);
		assertEquals(Currency.EUR, amount.getCurrency());
		assertFalse(amount.isNegative());
		var res = amount.plus(Currency.EUR, 40);
		assertEquals(240, res.getQuantity(), 0.01);
		assertEquals(Currency.EUR, res.getCurrency());
		assertFalse(res.isNegative());
	}
	
	@Test
	void testPlus_GBP() {
		var amount = new CurrencyAmount(180, Currency.GBP);
		assertEquals(180, amount.getQuantity(), 0.01);
		assertEquals(Currency.GBP, amount.getCurrency());
		assertFalse(amount.isNegative());
		var res = amount.plus(Currency.GBP, 20);
		assertEquals(200, res.getQuantity(), 0.01);
		assertEquals(Currency.GBP, res.getCurrency());
		assertFalse(res.isNegative());
	}
	
	@Test
	void testPlus_USD() {
		var amount = new CurrencyAmount(260, Currency.USD);
		assertEquals(260, amount.getQuantity(), 0.01);
		assertEquals(Currency.USD, amount.getCurrency());
		assertFalse(amount.isNegative());
		var res = amount.plus(Currency.USD, 40);
		assertEquals(300, res.getQuantity(), 0.01);
		assertEquals(Currency.USD, res.getCurrency());
		assertFalse(res.isNegative());
	}
	
	@Test
	void testPlus_GBPUSD() {
		var amount = new CurrencyAmount(100, Currency.GBP);
		double importoIniziale = 100;
		assertEquals(importoIniziale, amount.getQuantity(), 0.01);
		assertEquals(Currency.GBP, amount.getCurrency());
		assertFalse(amount.isNegative());
		double importoAggiuntivo = 12;
		var res = amount.plus(Currency.USD, importoAggiuntivo); // 12 USD = 2 galeoni = 10 GBP
		var tassoDiCambio = Currency.USD.getExchangeRate() / Currency.GBP.getExchangeRate();
		assertEquals(importoIniziale + importoAggiuntivo / tassoDiCambio, res.getQuantity(), 0.01);
		assertEquals(Currency.GBP, res.getCurrency());
		assertFalse(res.isNegative());
		// System.out.println(res); // 110,00 sterline
	}
	
	@Test
	void testPlus_USDGBP() {
		var amount = new CurrencyAmount(100, Currency.USD);
		double importoIniziale = 100;
		assertEquals(importoIniziale, amount.getQuantity(), 0.01);
		assertEquals(Currency.USD, amount.getCurrency());
		assertFalse(amount.isNegative());
		double importoAggiuntivo = 12;
		var res = amount.plus(Currency.GBP, importoAggiuntivo); // 12 GBP = 2.4 galeoni = 14.40 USD
		var tassoDiCambio = Currency.GBP.getExchangeRate() / Currency.USD.getExchangeRate();
		assertEquals(importoIniziale + importoAggiuntivo / tassoDiCambio, res.getQuantity(), 0.01);
		assertEquals(Currency.USD, res.getCurrency());
		assertFalse(res.isNegative());
		// System.out.println(res); // 114,40 dollari
	}
	
	@Test
	void testPlus_GBPEUR() {
		var amount = new CurrencyAmount(100, Currency.GBP);
		double importoIniziale = 100;
		assertEquals(importoIniziale, amount.getQuantity(), 0.01);
		assertEquals(Currency.GBP, amount.getCurrency());
		assertFalse(amount.isNegative());
		double importoAggiuntivo = 12;
		var res = amount.plus(Currency.EUR, importoAggiuntivo); // 12 EUR = 2.12 galeoni = 10.60 GBP
		var tassoDiCambio = Currency.EUR.getExchangeRate() / Currency.GBP.getExchangeRate();
		assertEquals(importoIniziale + importoAggiuntivo / tassoDiCambio, res.getQuantity(), 0.01);
		assertEquals(Currency.GBP, res.getCurrency());
		assertFalse(res.isNegative());
		// System.out.println(res); // 110,60 sterline
	}
	
	@Test
	void testPlus_EURUSD() {
		var amount = new CurrencyAmount(100, Currency.EUR);
		double importoIniziale = 100;
		assertEquals(importoIniziale, amount.getQuantity(), 0.01);
		assertEquals(Currency.EUR, amount.getCurrency());
		assertFalse(amount.isNegative());
		double importoAggiuntivo = 100;
		var res = amount.plus(Currency.USD, importoAggiuntivo); // 100 USD =  16.67 galeoni = 94.33 EUR
		var tassoDiCambio = Currency.USD.getExchangeRate() / Currency.EUR.getExchangeRate();
		assertEquals(importoIniziale + importoAggiuntivo / tassoDiCambio, res.getQuantity(), 0.01);
		assertEquals(Currency.EUR, res.getCurrency());
		assertFalse(res.isNegative());
		// System.out.println(res); // 194,33 euro
	}
	
	@Test
	void testMinus_EURUSD() {
		var amount = new CurrencyAmount(100, Currency.EUR);
		double importoIniziale = 100;
		assertEquals(importoIniziale, amount.getQuantity(), 0.01);
		assertEquals(Currency.EUR, amount.getCurrency());
		assertFalse(amount.isNegative());
		double importoAggiuntivo = 100;
		var res = amount.minus(Currency.USD, importoAggiuntivo); // 100 USD =  16.67 galeoni = 94.33 EUR
		var tassoDiCambio = Currency.USD.getExchangeRate() / Currency.EUR.getExchangeRate();
		assertEquals(importoIniziale - importoAggiuntivo / tassoDiCambio, res.getQuantity(), 0.01);
		assertEquals(Currency.EUR, res.getCurrency());
		assertFalse(res.isNegative());
		// System.out.println(res); // 5,66 euro
	}
	
	@Test
	void testMinusPlus_EURUSDGBP() {
		var amount = new CurrencyAmount(100, Currency.EUR);
		double importoIniziale = 100;
		assertEquals(importoIniziale, amount.getQuantity(), 0.01);
		assertEquals(Currency.EUR, amount.getCurrency());
		assertFalse(amount.isNegative());
		double importoAggiuntivo1 = 100;
		var res = amount.minus(Currency.USD, importoAggiuntivo1); // 100 USD =  16.67 galeoni = 94.33 EUR
		var tassoDiCambio1 = Currency.USD.getExchangeRate() / Currency.EUR.getExchangeRate();
		assertEquals(importoIniziale - importoAggiuntivo1 / tassoDiCambio1, res.getQuantity(), 0.01);
		assertEquals(Currency.EUR, res.getCurrency());
		assertFalse(res.isNegative());
		//----------------------------------------------
		double intermediateQuantity = res.getQuantity(); // la salviamo per fare il check dopo
		double importoAggiuntivo2 = 100;
		res.plus(Currency.GBP, importoAggiuntivo2); // 100 GBP = 20 galeoni = 113.20 euro // NB: res si modifica
		// System.out.println(res); // 118,86 euro
		var tassoDiCambio2 = Currency.GBP.getExchangeRate() / Currency.EUR.getExchangeRate();
		assertEquals(intermediateQuantity + importoAggiuntivo2 / tassoDiCambio2, res.getQuantity(), 0.01);
		assertEquals(Currency.EUR, res.getCurrency());
		assertFalse(res.isNegative());
		// System.out.println(res); // 118,86 euro
	}
	
	@Test
	void testMinusNeg_EUR() {
		var amount = new CurrencyAmount(200, Currency.EUR);
		assertEquals(200, amount.getQuantity(), 0.01);
		assertEquals(Currency.EUR, amount.getCurrency());
		assertFalse(amount.isNegative());
		var res = amount.minus(Currency.EUR, 240);
		assertEquals(-40, res.getQuantity(), 0.01);
		assertEquals(Currency.EUR, res.getCurrency());
		assertTrue(res.isNegative());
	}
	
	@Test
	void testMinusNeg_EURUSD() {
		var amount = new CurrencyAmount(100, Currency.EUR);
		double importoIniziale = 100;
		assertEquals(importoIniziale, amount.getQuantity(), 0.01);
		assertEquals(Currency.EUR, amount.getCurrency());
		assertFalse(amount.isNegative());
		double importoAggiuntivo = 120;
		var res = amount.minus(Currency.USD, importoAggiuntivo); // 120 USD =  20 galeoni = 113.20 EUR
		var tassoDiCambio = Currency.USD.getExchangeRate() / Currency.EUR.getExchangeRate();
		assertEquals(importoIniziale - importoAggiuntivo / tassoDiCambio, res.getQuantity(), 0.01);
		assertEquals(Currency.EUR, res.getCurrency());
		assertTrue(res.isNegative());
		// System.out.println(res); // -13.20 euro
	}
	
}
