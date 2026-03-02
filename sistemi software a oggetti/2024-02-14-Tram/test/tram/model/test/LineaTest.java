package tram.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tram.model.Fermata;
import tram.model.Frequenza;
import tram.model.Linea;
import tram.model.Direzione;


class LineaTest {
	
	// Linea(String nome, Frequenza freq, LocalTime primaCorsa, LocalTime ultimaCorsa, List<Fermata> fermate) {

	static List<Fermata> fermate;
	
	@BeforeAll
	static void creaFermate() {
		fermate = List.of(new Fermata("Parco Nord", 0), new Fermata("Parco Centro", 6), new Fermata("Giardino", 10), new Fermata("Parco Sud", 14));
	} 
	
	
	@Test
	void testOK_DatiDiBase() {
		var linea = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate);	
		assertEquals("Linea 1", linea.getNome());
		assertEquals(fermate, linea.getFermate());
		assertEquals(7, linea.getFrequenza().valore());
		assertEquals(LocalTime.of(6,30), linea.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20,30), linea.getOrarioUltimaCorsaAlCapolineaIniziale());
	}
	
	@Test
	void testOK_CasoLimiteOrariUguali() {
		var linea = new Linea("Linea 1", new Frequenza(7), LocalTime.of(20,30), LocalTime.of(20,30), fermate);	
		assertEquals("Linea 1", linea.getNome());
		assertEquals(fermate, linea.getFermate());
		assertEquals(7, linea.getFrequenza().valore());
		assertEquals(LocalTime.of(20,30), linea.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20,30), linea.getOrarioUltimaCorsaAlCapolineaIniziale());
	}
	
	@Test
	void testOK_CasoLimiteFermateDisordinate() {
		var fermateReversed = new ArrayList<>(fermate); Collections.reverse(fermateReversed);
		var linea = new Linea("Linea 1", new Frequenza(7), LocalTime.of(20,30), LocalTime.of(20,30), fermateReversed);	
		assertEquals("Linea 1", linea.getNome());
		assertEquals(fermateReversed, linea.getFermate());
		assertEquals(7, linea.getFrequenza().valore());
		assertEquals(LocalTime.of(20,30), linea.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20,30), linea.getOrarioUltimaCorsaAlCapolineaIniziale());
	}
	
	@Test
	void testOK_Capilinea() {
		var linea = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate);	
		assertEquals(LocalTime.of(6,30), linea.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20,30), linea.getOrarioUltimaCorsaAlCapolineaIniziale());
		assertEquals(14, linea.getDurataTragitto());
		assertEquals(LocalTime.of(6,30).plusMinutes(14),  linea.getOrarioPrimaCorsaAlCapolineaFinale());
		assertEquals(LocalTime.of(20,30).plusMinutes(14), linea.getOrarioUltimaCorsaAlCapolineaFinale());
	}
	
	@Test
	void testOK_GetFermata() {
		var linea = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate);	
		assertEquals(new Fermata("Parco Nord", 0),   linea.getFermata("Parco Nord").get());
		assertEquals(new Fermata("Parco Centro", 6), linea.getFermata("Parco Centro").get());
		assertEquals(new Fermata("Giardino", 10),    linea.getFermata("Giardino").get());
		assertEquals(new Fermata("Parco Sud", 14),   linea.getFermata("Parco Sud").get());
	}
	
	@Test
	void testOK_GetFermataAssente() {
		var linea = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate);	
		assertTrue(linea.getFermata("Giardinetti").isEmpty());
	}
	
	@Test
	void testOK_GetMinutiFermata() {
		var linea = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate);	
		assertEquals( 0, linea.getMinutiFermata("Parco Nord").getAsInt());
		assertEquals( 6, linea.getMinutiFermata("Parco Centro").getAsInt());
		assertEquals(10, linea.getMinutiFermata("Giardino").getAsInt());
		assertEquals(14, linea.getMinutiFermata("Parco Sud").getAsInt());
	}
	
	@Test
	void testOK_GetMinutiFermataAssente() {
		var linea = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate);	
		assertTrue(linea.getMinutiFermata("Giardinetti").isEmpty());
	}
	
	@Test
	void testOK_OrariAlleFermate() {
		var linea = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate);	
		assertEquals(LocalTime.of(6,30), linea.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20,30), linea.getOrarioUltimaCorsaAlCapolineaIniziale());
		assertEquals(14, linea.getDurataTragitto());
		assertEquals(LocalTime.of(6,30).plusMinutes(10),  linea.getOrarioPrimaCorsaAllaFermata("Giardino", Direzione.ANDATA));
		assertEquals(LocalTime.of(20,30).plusMinutes(10), linea.getOrarioUltimaCorsaAllaFermata("Giardino", Direzione.ANDATA));
		assertEquals(LocalTime.of(6,30).plusMinutes(18),  linea.getOrarioPrimaCorsaAllaFermata("Giardino", Direzione.RITORNO));
		assertEquals(LocalTime.of(20,30).plusMinutes(18), linea.getOrarioUltimaCorsaAllaFermata("Giardino", Direzione.RITORNO));
	}
	
	@Test
	void testOK_EqualsHashcode() {
		var linea1 = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate);
		var linea2 = new Linea("Linea 2", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate);
		var linea1bis = new Linea("Linea 1", new Frequenza(14), LocalTime.of(6,30), LocalTime.of(20,30), fermate);
		var linea1ter = new Linea("Linea 1", new Frequenza(7), LocalTime.of(7,30), LocalTime.of(20,34), fermate);
		var linea1quater = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,2), fermate);
		var fermateUlteriori = new ArrayList<>(fermate); fermateUlteriori.add(new Fermata("Stazione", 21));
		var linea1quinquies = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermateUlteriori);
		//
		assertEquals(14, linea1.getDurataTragitto());
		assertEquals(14, linea2.getDurataTragitto());
		assertEquals(14, linea1bis.getDurataTragitto());
		assertEquals(14, linea1ter.getDurataTragitto());
		assertEquals(14, linea1quater.getDurataTragitto());
		assertEquals(21, linea1quinquies.getDurataTragitto());
		//
		assertEquals(fermate, linea1.getFermate());
		assertEquals(fermate, linea2.getFermate());
		assertEquals(fermate, linea1bis.getFermate());
		assertEquals(fermate, linea1ter.getFermate());
		assertEquals(fermate, linea1quater.getFermate());
		assertNotEquals(fermate, linea1quinquies.getFermate());
		//
		assertEquals(7, linea1.getFrequenza().valore());
		assertEquals(7, linea2.getFrequenza().valore());
		assertEquals(14, linea1bis.getFrequenza().valore());
		assertEquals(7, linea1ter.getFrequenza().valore());
		assertEquals(7, linea1quater.getFrequenza().valore());
		assertEquals(7, linea1quinquies.getFrequenza().valore());
		//
		assertEquals(LocalTime.of(6,30), linea1.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(6,30), linea2.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(6,30), linea1bis.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(7,30), linea1ter.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(6,30), linea1quater.getOrarioPrimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(6,30), linea1quinquies.getOrarioPrimaCorsaAlCapolineaIniziale());
		//
		assertEquals(LocalTime.of(20,30), linea1.getOrarioUltimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20,30), linea2.getOrarioUltimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20,30), linea1bis.getOrarioUltimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20,34), linea1ter.getOrarioUltimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20, 2), linea1quater.getOrarioUltimaCorsaAlCapolineaIniziale());
		assertEquals(LocalTime.of(20,30), linea1quinquies.getOrarioUltimaCorsaAlCapolineaIniziale());
		//
		assertEquals(linea1, linea1bis);
		assertEquals(linea1, linea1ter);
		assertEquals(linea1, linea1quater);
		assertEquals(linea1, linea1quinquies);
		assertNotEquals(linea1, linea2);
		//
		assertEquals(linea1.hashCode(), linea1bis.hashCode());
		assertEquals(linea1.hashCode(), linea1ter.hashCode());
		assertEquals(linea1.hashCode(), linea1quater.hashCode());
		assertEquals(linea1.hashCode(), linea1quinquies.hashCode());
		assertNotEquals(linea1.hashCode(), linea2.hashCode());
	}
	
	@Test
	void testKO_ArgomentiInvalidi() {
		assertThrows(IllegalArgumentException.class, () -> new Linea(null, new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("  ", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("\t\n ", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", null, LocalTime.of(6,30), LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", new Frequenza(-2), LocalTime.of(6,30), LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", new Frequenza(0), LocalTime.of(6,30), LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", new Frequenza(67), LocalTime.of(6,30), LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", new Frequenza(7), null, LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), null, fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea Sbagliata", new Frequenza(7), LocalTime.of(21,30), LocalTime.of(8,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea Sbagliata", new Frequenza(7), LocalTime.of(7,30), LocalTime.of(20,30), fermate));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea Sbagliata", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,20), fermate));
	}
	
	@Test
	void testKO_ListaFermateInvalida() {
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), List.of(new Fermata("Parco Nord", 2), new Fermata("Parco Centro", 6), new Fermata("Giardino", 10), new Fermata("Parco Sud", 14))));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), List.of(new Fermata("Parco Nord", 0), new Fermata("Parco Centro", 0), new Fermata("Giardino", 10), new Fermata("Parco Sud", 14))));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), List.of(new Fermata("Parco Nord", 0), new Fermata("Parco Centro", 6), new Fermata("Giardino", 6), new Fermata("Parco Sud", 14))));
		assertThrows(IllegalArgumentException.class, () -> new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(20,30), List.of(new Fermata("Parco Nord", 0), new Fermata("Parco Centro", 6), new Fermata("Giardino", 10), new Fermata("Parco Nord", 14))));
	}
		
}
