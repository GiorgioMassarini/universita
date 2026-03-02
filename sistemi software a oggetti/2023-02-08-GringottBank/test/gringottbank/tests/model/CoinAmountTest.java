package gringottbank.tests.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import gringottbank.model.Coin;
import gringottbank.model.CoinAmount;

class CoinAmountTest {

	@Test
	void testCtor0() {
		var amount = new CoinAmount();
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testCtor1OK_Galleon() {
		var amount = new CoinAmount(Coin.GALLEON, 11);
		assertEquals(11, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testCtor1OK_Sickle() {
		var amount = new CoinAmount(Coin.SICKLE, 22);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(22, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testCtor1OK_Knut() {
		var amount = new CoinAmount(Coin.KNUT, 33);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(33, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}

	@Test
	void testCtor1KO_Galleon() {
		assertThrows(IllegalArgumentException.class, () -> new CoinAmount(Coin.GALLEON, -1));
	}
	
	@Test
	void testToStringOK_ImportoVuoto() {
		var amount = new CoinAmount();
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertEquals("0 " + Coin.GALLEON.getName() + ", 0 " + Coin.SICKLE.getName() + ", 0 " + Coin.KNUT.getName(), 
				amount.toString());
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testToStringOK_Galleon() {
		var importo = 11;
		var amount = new CoinAmount(Coin.GALLEON, importo);
		assertEquals(importo, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertEquals(
				importo + " " + Coin.GALLEON.getName() + ", 0 " + Coin.SICKLE.getName() + ", 0 " + Coin.KNUT.getName(),
				amount.toString());
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testToStringOK_Sickle() {
		var importo = 22;
		var amount = new CoinAmount(Coin.SICKLE, importo);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(importo, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertEquals(
				"0 " + Coin.GALLEON.getName() + ", " + importo + " " + Coin.SICKLE.getName() + ", 0 " + Coin.KNUT.getName(),
				amount.toString());
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testToStringOK_Knuts() {
		var importo = 33;
		var amount = new CoinAmount(Coin.KNUT, importo);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(importo, amount.getQuantity(Coin.KNUT));
		assertEquals("0 " + Coin.GALLEON.getName() + ", 0 " + Coin.SICKLE.getName() + ", " + importo + " " + Coin.KNUT.getName(),
				amount.toString());
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testPlus3g_FromCtor0() {
		var amount = new CoinAmount().plus(Coin.GALLEON, 3);
		assertEquals(3, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testPlus4s_FromCtor0() {
		var amount = new CoinAmount().plus(Coin.SICKLE, 4);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(4, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testPlus5k_FromCtor0() {
		var amount = new CoinAmount().plus(Coin.KNUT, 5);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testPlusA_FromCtor0() {
		var amount = new CoinAmount().plus(Coin.KNUT, 5).plus(Coin.SICKLE, 4).plus(Coin.GALLEON, 3);
		assertEquals(3, amount.getQuantity(Coin.GALLEON));
		assertEquals(4, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testPlusB_FromCtor0() {
		var amount = new CoinAmount().plus(Coin.GALLEON, 30).plus(Coin.SICKLE, 4).plus(Coin.KNUT, 5);
		assertEquals(30, amount.getQuantity(Coin.GALLEON));
		assertEquals(4, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testPlusC_FromCtor1_Galleon() {
		var amount = new CoinAmount(Coin.GALLEON, 11).plus(Coin.SICKLE, 4).plus(Coin.KNUT, 5);
		assertEquals(11, amount.getQuantity(Coin.GALLEON));
		assertEquals(4, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testPlusD_FromCtor1_Sickle() {
		var amount = new CoinAmount(Coin.SICKLE, 10).plus(Coin.SICKLE, 4).plus(Coin.KNUT, 5);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(14, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testPlusE_FromCtor1_Sickle() {
		var amount = new CoinAmount(Coin.KNUT, 10).plus(Coin.SICKLE, 4).plus(Coin.GALLEON, 5).plus(Coin.KNUT, 5);
		assertEquals(5, amount.getQuantity(Coin.GALLEON));
		assertEquals(4, amount.getQuantity(Coin.SICKLE));
		assertEquals(15, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testMinus3g_FromCtor0() {
		var amount = new CoinAmount().minus(Coin.GALLEON, 3);
		assertEquals(-3, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertTrue(amount.isNegative());
	}
	
	@Test
	void testMinus4s_FromCtor0() {
		var amount = new CoinAmount().minus(Coin.SICKLE, 4);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(-4, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertTrue(amount.isNegative());
	}
	
	@Test
	void testMinus5k_FromCtor0() {
		var amount = new CoinAmount().minus(Coin.KNUT, 5);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(0, amount.getQuantity(Coin.SICKLE));
		assertEquals(-5, amount.getQuantity(Coin.KNUT));
		assertTrue(amount.isNegative());
	}
	
	@Test
	void testMinusA_FromCtor0() {
		var amount = new CoinAmount().minus(Coin.KNUT, 5).minus(Coin.SICKLE, 4).minus(Coin.GALLEON, 3);
		assertEquals(-3, amount.getQuantity(Coin.GALLEON));
		assertEquals(-4, amount.getQuantity(Coin.SICKLE));
		assertEquals(-5, amount.getQuantity(Coin.KNUT));
		assertTrue(amount.isNegative());
	}
	
	@Test
	void testMinusB_FromCtor0() {
		var amount = new CoinAmount().minus(Coin.GALLEON, 30).minus(Coin.SICKLE, 4).minus(Coin.KNUT, 5);
		assertEquals(-30, amount.getQuantity(Coin.GALLEON));
		assertEquals(-4, amount.getQuantity(Coin.SICKLE));
		assertEquals(-5, amount.getQuantity(Coin.KNUT));
		assertTrue(amount.isNegative());
	}
	
	@Test
	void testMinusC_FromCtor1_Galleon() {
		var amount = new CoinAmount(Coin.GALLEON, 11).minus(Coin.SICKLE, 4).minus(Coin.KNUT, 5);
		assertEquals(11, amount.getQuantity(Coin.GALLEON));
		assertEquals(-4, amount.getQuantity(Coin.SICKLE));
		assertEquals(-5, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testMinusD_FromCtor1_Sickle() {
		var amount = new CoinAmount(Coin.SICKLE, 10).minus(Coin.SICKLE, 4).minus(Coin.KNUT, 5);
		assertEquals(0, amount.getQuantity(Coin.GALLEON));
		assertEquals(6, amount.getQuantity(Coin.SICKLE));
		assertEquals(-5, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
	
	@Test
	void testMinusE_FromCtor1_Sickle() {
		var amount = new CoinAmount(Coin.KNUT, 10).minus(Coin.SICKLE, 4).minus(Coin.GALLEON, 5).minus(Coin.KNUT, 5);
		assertEquals(-5, amount.getQuantity(Coin.GALLEON));
		assertEquals(-4, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		assertTrue(amount.isNegative());
	}
	
	@Test
	void testPlusMinusE_FromCtor1_Sickle() {
		var amount = new CoinAmount(Coin.KNUT, 5).minus(Coin.SICKLE, 4).plus(Coin.GALLEON, 5).minus(Coin.KNUT, 5);
		assertEquals(5, amount.getQuantity(Coin.GALLEON));
		assertEquals(-4, amount.getQuantity(Coin.SICKLE));
		assertEquals(0, amount.getQuantity(Coin.KNUT));
		assertFalse(amount.isNegative());
	}
}
