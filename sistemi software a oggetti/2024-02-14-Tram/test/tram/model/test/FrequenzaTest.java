package tram.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import tram.model.Frequenza;


class FrequenzaTest {

	@Test
	void testOK_15() {
		var freq = new Frequenza(15);
		assertEquals(15, freq.valore());
	}

	@Test
	void testOK_1() {
		var freq = new Frequenza(1);
		assertEquals(1, freq.valore());
	}
	
	@Test
	void testOK_59() {
		var freq = new Frequenza(59);
		assertEquals(59, freq.valore());
	}
	
	@Test
	void testOK_60() {
		var freq = new Frequenza(60);
		assertEquals(60, freq.valore());
	}
	
	@Test
	void testKO_Neg() {
		assertThrows(IllegalArgumentException.class, () -> new Frequenza(-4));
	}

	@Test
	void testKO_Zero() {
		assertThrows(IllegalArgumentException.class, () -> new Frequenza(0));
	}
	
	@Test
	void testKO_MoreThan60() {
		assertThrows(IllegalArgumentException.class, () -> new Frequenza(61));
	}

}
