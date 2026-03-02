package gringottbank.tests.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import gringottbank.model.Coin;

class CoinTest {

	@Test
	void testGetDivider() {
		assertEquals(17, Coin.GALLEON.getDivider());
		assertEquals(29, Coin.SICKLE.getDivider());
		assertEquals( 1, Coin.KNUT.getDivider());
	}
	
	@Test
	void testGetName() {
		assertEquals("galeoni", Coin.GALLEON.getName());
		assertEquals("falci", Coin.SICKLE.getName());
		assertEquals("zellini", Coin.KNUT.getName());
	}
	
	@Test
	void testGetNames() {
		String[] names = {"galeoni","falci","zellini"};
		assertArrayEquals(names, Coin.getNames());
	}

}
