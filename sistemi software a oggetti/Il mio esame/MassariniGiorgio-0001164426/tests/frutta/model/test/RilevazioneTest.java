package frutta.model.test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import frutta.model.Rilevazione;

class RilevazioneTest {

	@Test
	void testOK() {
		var rilevazione = new Rilevazione(LocalDate.of(2025,10,29), 8.35);
		assertEquals(LocalDate.of(2025,10,29), rilevazione.data());
		assertEquals(8.35, rilevazione.prezzo(), 0.01);
	}
	
	@Test
	void testOK_CompareTo() {
		var rilevazione1 = new Rilevazione(LocalDate.of(2025,10,29),  8.35);
		var rilevazione2 = new Rilevazione(LocalDate.of(2025,10,29), 18.35);
		var rilevazione3 = new Rilevazione(LocalDate.of(2025,10,20), 18.35);
		assertEquals(LocalDate.of(2025,10,29), rilevazione1.data());
		assertEquals(8.35, rilevazione1.prezzo(), 0.01);
		assertEquals(LocalDate.of(2025,10,29), rilevazione2.data());
		assertEquals(18.35, rilevazione2.prezzo(), 0.01);
		assertEquals(LocalDate.of(2025,10,20), rilevazione3.data());
		assertEquals(18.35, rilevazione3.prezzo(), 0.01);
		//
		assertTrue(rilevazione1.compareTo(rilevazione2)==0);
		assertEquals(rilevazione1, rilevazione2);
		assertTrue(rilevazione1.compareTo(rilevazione3)>0);
		assertTrue(rilevazione3.compareTo(rilevazione2)<0);
	}
	
	@Test
	void testKO_Arg1null() {
		assertThrows(NullPointerException.class, () -> new Rilevazione(null, 8.35));
	}
	
	@Test
	void testKO_Arg2Neg() {
		assertThrows(IllegalArgumentException.class, () -> new Rilevazione(LocalDate.of(2025,10,29), -0.35));
	}
	
	@Test
	void testKO_Arg2NaN() {
		assertThrows(IllegalArgumentException.class, () -> new Rilevazione(LocalDate.of(2025,10,29), Double.NaN));
	}
	
	@Test
	void testKO_Arg2NegInf() {
		assertThrows(IllegalArgumentException.class, () -> new Rilevazione(LocalDate.of(2025,10,29), Double.NEGATIVE_INFINITY));
	}
	
	@Test
	void testKO_Arg2PosInf() {
		assertThrows(IllegalArgumentException.class, () -> new Rilevazione(LocalDate.of(2025,10,29), Double.POSITIVE_INFINITY));
	}
	
		
}
