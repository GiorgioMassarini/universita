package tram.model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tram.model.Fermata;

class FermataTest {

	@Test
	void testOK() {
		var f = new Fermata("Parco", 12);
		assertEquals("Parco", f.nome());
		assertEquals(12, f.minutiDaCapolinea());
	}
	
	@Test
	void testOK_Capolinea() {
		var f = new Fermata("Capolinea", 0);
		assertEquals("Capolinea", f.nome());
		assertEquals(0, f.minutiDaCapolinea());
	}
	
	@Test
	void testOK_Equals() {
		var f1 = new Fermata("Parco", 12);
		var f2 = new Fermata("Parco", 12);
		var f3 = new Fermata("Giardino", 12);
		var f4 = new Fermata("Parco", 27);
		assertEquals(f1, f1);
		assertEquals(f1, f2);
		assertNotEquals(f1, f3);
		assertNotEquals(f1, f4);
	}
	
	@Test
	void testKO_NomeFermataNull() {
		assertThrows(IllegalArgumentException.class, () -> new Fermata(null, 12));
	}
	
	@Test
	void testKO_NomeFermataBlank1() {
		assertThrows(IllegalArgumentException.class, () -> new Fermata("", 12));
	}

	@Test
	void testKO_NomeFermataBlank2() {
		assertThrows(IllegalArgumentException.class, () -> new Fermata(" ", 12));
	}
	
	@Test
	void testKO_NomeFermataBlank3() {
		assertThrows(IllegalArgumentException.class, () -> new Fermata("\t", 12));
	}
	
	@Test
	void testKO_NomeFermataBlank4() {
		assertThrows(IllegalArgumentException.class, () -> new Fermata("  ", 12));
	}
	
	@Test
	void testKO_NomeFermataBlank5() {
		assertThrows(IllegalArgumentException.class, () -> new Fermata("  \t", 12));
	}
	
	@Test
	void testKO_NomeFermataBlank6() {
		assertThrows(IllegalArgumentException.class, () -> new Fermata(" \n", 12));
	}

	@Test
	void testKO_MinutiNegativi() {
		assertThrows(IllegalArgumentException.class, () -> new Fermata("Capolinea", -2));
	}

}
