package gringottbank.tests.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import gringottbank.model.Currency;

class CurrencyTest {

	@Test
	void testGetDivider() {
		assertEquals(5.0, Currency.GBP.getExchangeRate(), 0.01);
		assertEquals(6.0, Currency.USD.getExchangeRate(), 0.01);
		assertEquals(5.66, Currency.EUR.getExchangeRate(), 0.01);
	}
	
	@Test
	void testGetName() {
		assertEquals("sterline", Currency.GBP.getName());
		assertEquals("dollari", Currency.USD.getName());
		assertEquals("euro", Currency.EUR.getName());
	}
	
	@Test
	void testGetNames() {
		String[] names = {"sterline","dollari","euro"};
		assertArrayEquals(names, Currency.getNames());
	}

}
