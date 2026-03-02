package gringottbank.tests.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import gringottbank.model.Coin;
import gringottbank.model.CoinAmount;
import gringottbank.model.Withdrawal;

class WithdrawalTest {

	@Test
	void testOK_CoinAmount1() {
		var amount = new CoinAmount(Coin.KNUT, 5).plus(Coin.SICKLE, 14).plus(Coin.GALLEON, 5);
		var w = new Withdrawal("Harry", amount);
		assertEquals(5, amount.getQuantity(Coin.GALLEON));
		assertEquals(14, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		assertFalse(w.getAmount().isNegative());
		assertEquals(amount, w.getAmount());
		assertEquals("Harry", w.getName());
	}
	
	@Test
	void testOK_CoinAmount2() {
		var amount = new CoinAmount(Coin.KNUT, 5).plus(Coin.SICKLE, 4).plus(Coin.GALLEON, 5);
		var w = new Withdrawal("Harry", amount);
		assertEquals(5, amount.getQuantity(Coin.GALLEON));
		assertEquals(4, amount.getQuantity(Coin.SICKLE));
		assertEquals(5, amount.getQuantity(Coin.KNUT));
		assertFalse(w.getAmount().isNegative());
		assertEquals(amount, w.getAmount());
		assertEquals("Harry", w.getName());
	}

	@Test
	void testKO_CoinAmount() {
		var amount = new CoinAmount().minus(Coin.GALLEON, 5);
		assertThrows(IllegalArgumentException.class, () -> new Withdrawal("Harry", amount));
	}

}
