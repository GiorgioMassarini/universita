package tram.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tram.controller.Controller;
import tram.model.Direzione;
import tram.model.Fermata;
import tram.model.Frequenza;
import tram.model.Linea;


class ControllerTest {
	
	static List<Fermata> fermateLinea1, fermateLinea2, fermateLinea3;
	static Linea linea1, linea2, linea3;
	static Controller controller;
	
	@BeforeAll
	static void initialSetup() {
		fermateLinea1 = List.of(new Fermata("Parco Nord", 0), new Fermata("Parco Centro", 6), new Fermata("Giardino", 10), new Fermata("Parco Sud", 14));
		fermateLinea2 = List.of(new Fermata("Terminal", 0), new Fermata("Via Bologna", 5), new Fermata("corso Cairoli", 9), new Fermata("Piazza Bra", 16));
		fermateLinea3 = List.of(new Fermata("Aeroporto", 0), new Fermata("Parcheggio interscambio", 5), new Fermata("Porta Nuova", 12), new Fermata("Stazione FS", 20));
		linea1 = new Linea("Linea 1", new Frequenza(7),  LocalTime.of(6,30), LocalTime.of(21,26), fermateLinea1);	
		linea2 = new Linea("Linea 2", new Frequenza(8),  LocalTime.of(6,00), LocalTime.of(22,00), fermateLinea2);	
		linea3 = new Linea("Linea 3", new Frequenza(10), LocalTime.of(6,00), LocalTime.of(23,20), fermateLinea3);	
		controller = new Controller(Set.of(linea1,linea2,linea3));
	}
	
	@Test
	void testOK_Ctor() {
		assertEquals(Set.of(linea1,linea2,linea3), controller.getLinee());
		assertEquals(fermateLinea1, controller.fermateLinea(linea1));
		assertEquals(fermateLinea2, controller.fermateLinea(linea2));
		assertEquals(fermateLinea3, controller.fermateLinea(linea3));
	}
	
	@Test
	void testOK_ProssimoPassaggio_Linea1_ParcoNord_Avanti() {
		// Linea1: 6:30-21:26 frequenza 7' durata 14'
		assertEquals(LocalTime.of( 6,30), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.ANDATA, LocalTime.of( 6,30)));
		assertEquals(LocalTime.of( 6,37), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.ANDATA, LocalTime.of( 6,32)));
		assertEquals(LocalTime.of(13,23), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.ANDATA, LocalTime.of(13,20)));
		assertEquals(LocalTime.of(13,30), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.ANDATA, LocalTime.of(13,25)));
		assertEquals(LocalTime.of(20, 2), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.ANDATA, LocalTime.of(20, 0)));
		assertEquals(LocalTime.of(20,30), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.ANDATA, LocalTime.of(20,30)));
		assertEquals(LocalTime.of(20,37), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.ANDATA, LocalTime.of(20,35)));
		assertEquals(LocalTime.of( 6,30), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.ANDATA, LocalTime.of(21,35)));
		assertEquals(LocalTime.of( 6,30), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.ANDATA, LocalTime.of( 5,45)));
	}
	
	@Test
	void testOK_ProssimoPassaggio_Linea1_ParcoNord_Indietro() {
		// Linea1: 6:30-20:30 frequenza 7' durata 14' -> da capolinea finale 6:44-20:44 -> ritorno capolinea iniziale 6:58-20:58
		assertEquals(LocalTime.of( 6,58), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of( 6,30)));
		assertEquals(LocalTime.of( 7, 5), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of( 7, 0)));
		assertEquals(LocalTime.of( 7, 5), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of( 7, 5)));
		assertEquals(LocalTime.of(13,23), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of(13,20)));
		assertEquals(LocalTime.of(13,30), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of(13,25)));
		assertEquals(LocalTime.of(20, 2), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of(20, 0)));
		assertEquals(LocalTime.of(20,30), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of(20,30)));
		assertEquals(LocalTime.of(20,37), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of(20,35)));
		assertEquals(LocalTime.of(20,44), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of(20,40)));
		assertEquals(LocalTime.of(20,51), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of(20,50)));
		assertEquals(LocalTime.of(20,58), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of(20,52)));
		assertEquals(LocalTime.of( 6,58), controller.prossimaCorsa("Linea 1", "Parco Nord", Direzione.RITORNO, LocalTime.of( 5,45)));
	}
	
	@Test
	void testOK_ProssimoPassaggio_Linea1_Giardino_Avanti() {
		// Linea1: 6:30-21:26 frequenza 7' durata 14' -> da Giardino (+10) 6:40-20:40
		assertEquals(LocalTime.of( 6,40), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of( 6,30)));
		assertEquals(LocalTime.of( 6,40), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of( 6,32)));
		assertEquals(LocalTime.of(13,26), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of(13,20)));
		assertEquals(LocalTime.of(13,26), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of(13,26)));
		assertEquals(LocalTime.of(20, 5), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of(20, 0)));
		assertEquals(LocalTime.of(20,33), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of(20,30)));
		assertEquals(LocalTime.of(20,40), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of(20,35)));
		assertEquals(LocalTime.of(20,47), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of(20,45)));
		assertEquals(LocalTime.of( 6,40), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of(21,45)));
		assertEquals(LocalTime.of( 6,40), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.ANDATA, LocalTime.of( 5,45)));
	}
	
	@Test
	void testOK_ProssimoPassaggio_Linea1_Giardino_Indietro() {
		// Linea1: 6:30-21:26 frequenza 7' durata 14' -> da capolinea finale 6:44-21:40 -> da Giardino (+4) 6:48-21:44
		assertEquals(LocalTime.of( 6,48), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of( 6,30)));
		assertEquals(LocalTime.of( 7, 2), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of( 7, 0)));
		assertEquals(LocalTime.of( 7, 9), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of( 7, 5)));
		assertEquals(LocalTime.of(13,20), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(13,20)));
		assertEquals(LocalTime.of(13,27), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(13,25)));
		assertEquals(LocalTime.of(19,59), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(19,59)));
		assertEquals(LocalTime.of(20, 6), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(20, 0)));
		assertEquals(LocalTime.of(20,27), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(20,22)));
		assertEquals(LocalTime.of(20,34), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(20,30)));
		assertEquals(LocalTime.of(20,41), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(20,40)));
		assertEquals(LocalTime.of(20,48), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(20,48)));
		assertEquals(LocalTime.of(20,55), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(20,50)));
		assertEquals(LocalTime.of( 6,48), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of(21,50)));
		assertEquals(LocalTime.of( 6,48), controller.prossimaCorsa("Linea 1", "Giardino", Direzione.RITORNO, LocalTime.of( 5,45)));
	}
	
	@Test
	void testOK_ProssimoPassaggio_Linea2() {
		// Linea2: 6:00-22:00 frequenza 8' durata 16' -> giunge al capolinea finale 6:16-22:16
		assertEquals(LocalTime.of( 6,16), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of( 6, 0)));
		assertEquals(LocalTime.of( 6,16), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of( 6,10)));
		assertEquals(LocalTime.of( 6,16), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of( 6,16)));
		assertEquals(LocalTime.of( 6,24), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of( 6,20)));
		assertEquals(LocalTime.of( 7, 4), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of( 7, 0)));
		assertEquals(LocalTime.of( 7,12), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of( 7, 5)));
		assertEquals(LocalTime.of( 8, 0), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of( 8, 0)));
		assertEquals(LocalTime.of( 8,16), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of( 8,10)));
		assertEquals(LocalTime.of( 9, 4), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of( 9, 0)));
		assertEquals(LocalTime.of(22, 0), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of(22, 0)));
		assertEquals(LocalTime.of(22,16), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of(22,10)));
		assertEquals(LocalTime.of( 6,16), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.ANDATA, LocalTime.of(22,20)));
		// Linea2: 6:00-22:00 frequenza 8' durata 16' -> riparte da capolinea finale 6:16-22:16
		assertEquals(LocalTime.of( 6,16), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.RITORNO, LocalTime.of( 6, 0)));
		assertEquals(LocalTime.of( 6,16), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.RITORNO, LocalTime.of( 6,16)));
		assertEquals(LocalTime.of( 6,32), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.RITORNO, LocalTime.of( 6,30)));
		assertEquals(LocalTime.of( 7, 4), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.RITORNO, LocalTime.of( 7, 0)));
		assertEquals(LocalTime.of( 7,12), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.RITORNO, LocalTime.of( 7, 5)));
		assertEquals(LocalTime.of(13,20), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.RITORNO, LocalTime.of(13,19)));
		assertEquals(LocalTime.of(13,28), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.RITORNO, LocalTime.of(13,25)));
		assertEquals(LocalTime.of(20, 0), controller.prossimaCorsa("Linea 2", "Piazza Bra", Direzione.RITORNO, LocalTime.of(19,59)));
	}
	
	@Test
	void testOK_ProssimoPassaggio_Linea3() {
		// Linea3: 6:00-23:20 frequenza 10' durata 20' -> da Parcheggio interscambio (+5) 6:05-23:25
		assertEquals(LocalTime.of( 6, 5), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 6, 0)));
		assertEquals(LocalTime.of( 6,15), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 6,10)));
		assertEquals(LocalTime.of( 6,15), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 6,15)));
		assertEquals(LocalTime.of( 6,25), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 6,20)));
		assertEquals(LocalTime.of( 7, 5), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 7, 0)));
		assertEquals(LocalTime.of( 7, 5), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 7, 5)));
		assertEquals(LocalTime.of( 7,15), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 7, 6)));
		assertEquals(LocalTime.of( 8, 5), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 8, 0)));
		assertEquals(LocalTime.of( 8,15), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 8,10)));
		assertEquals(LocalTime.of(20, 5), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of(20, 5)));
		assertEquals(LocalTime.of(20,25), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of(20,22)));
		assertEquals(LocalTime.of(20,35), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of(20,30)));
		assertEquals(LocalTime.of(20,45), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of(20,40)));
		assertEquals(LocalTime.of(20,55), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of(20,48)));
		assertEquals(LocalTime.of(23,25), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of(23,25)));
		assertEquals(LocalTime.of( 6, 5), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of(23,30)));
		assertEquals(LocalTime.of( 6, 5), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.ANDATA, LocalTime.of( 5,45)));
		// Linea3: 6:00-23:20 frequenza 10' durata 20' -> giunge al capolinea finale 6:20-23:40 -> da Parcheggio interscambio (+15) 6:35-23:55		
		assertEquals(LocalTime.of(20, 5), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.RITORNO, LocalTime.of(20, 0)));
		assertEquals(LocalTime.of(20,25), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.RITORNO, LocalTime.of(20,22)));
		assertEquals(LocalTime.of(20,35), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.RITORNO, LocalTime.of(20,30)));
		assertEquals(LocalTime.of(20,45), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.RITORNO, LocalTime.of(20,45)));
		assertEquals(LocalTime.of(20,55), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.RITORNO, LocalTime.of(20,48)));
		assertEquals(LocalTime.of(20,55), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.RITORNO, LocalTime.of(20,55)));
		assertEquals(LocalTime.of(23,55), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.RITORNO, LocalTime.of(23,50)));
		assertEquals(LocalTime.of( 6,35), controller.prossimaCorsa("Linea 3", "Parcheggio interscambio", Direzione.RITORNO, LocalTime.of( 5,45)));
	}

	
}
