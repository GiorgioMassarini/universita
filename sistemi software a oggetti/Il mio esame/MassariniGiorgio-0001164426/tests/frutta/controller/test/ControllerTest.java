package frutta.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import frutta.controller.Controller;
import frutta.model.MappaFrutti;
import frutta.model.Rilevazione;


class ControllerTest {
	
	static MappaFrutti mappa = new MappaFrutti();
	static Controller controller;
	
	@BeforeAll static void setup() {
		//
		// NB: le righe con NaN non vanno inserite come Rilevazioni
		//
		mappa.aggiungi("Albicocche", "nostrane", new Rilevazione(LocalDate.of(2025,3,24), 2.65));
		mappa.aggiungi("Albicocche", "nostrane", new Rilevazione(LocalDate.of(2025,3,31), 2.45));
		mappa.aggiungi("Albicocche", "nostrane", new Rilevazione(LocalDate.of(2025,4, 7), 2.45));
		mappa.aggiungi("Albicocche", "nostrane", new Rilevazione(LocalDate.of(2025,4,14), 2.05));
		//
		mappa.aggiungi("Ciliegie", "Bigarreau", new Rilevazione(LocalDate.of(2025,3,24), 8.00));
		mappa.aggiungi("Ciliegie", "Bigarreau", new Rilevazione(LocalDate.of(2025,3,31), 5.50));
		mappa.aggiungi("Ciliegie", "Bigarreau", new Rilevazione(LocalDate.of(2025,4, 7), 5.50));
		mappa.aggiungi("Ciliegie", "Bigarreau", new Rilevazione(LocalDate.of(2025,4,14), 4.63));
		//
		mappa.aggiungi("Ciliegie", "Celeste", new Rilevazione(LocalDate.of(2025,4,14), 7.50));
		//
		mappa.aggiungi("Ciliegie", "Dure", new Rilevazione(LocalDate.of(2025,3,31), 4.88));
		mappa.aggiungi("Ciliegie", "Dure", new Rilevazione(LocalDate.of(2025,4, 7), 4.63));
		mappa.aggiungi("Ciliegie", "Dure", new Rilevazione(LocalDate.of(2025,4,14), 4.25));
		//
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,2,10), 3.82));		
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,2,17), 3.15));
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,2,24), 2.85));
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3, 3), 2.75));
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3,10), 2.65));
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3,17), 2.54));
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3,24), 2.29));
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3,31), 2.22));
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,4, 7), 2.22));
		mappa.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,4,14), 2.14));
		//
		controller = new Controller(mappa);
		assertEquals(mappa, controller.mappaFrutti());
	}

	
	@Test
	void testOK() {
		assertEquals(new TreeSet<>(Set.of("Albicocche", "Ciliegie", "Fragoline")), controller.tipiFrutta());
				
		assertEquals(1, controller.sottotipi("Albicocche").size());
		assertEquals(3, controller.sottotipi("Ciliegie").size());
		assertEquals(1, controller.sottotipi("Fragoline").size());
		assertEquals(new TreeSet<>(Set.of("nostrane")), controller.sottotipi("Albicocche"));
		assertEquals(new TreeSet<>(Set.of("Dure","Bigarreau","Celeste")), controller.sottotipi("Ciliegie"));
		assertEquals(new TreeSet<>(Set.of("polpose")), controller.sottotipi("Fragoline"));
		
		assertEquals(List.of(
						new Rilevazione(LocalDate.of(2025,3,24), 2.65),
						new Rilevazione(LocalDate.of(2025,3,31), 2.45),
						new Rilevazione(LocalDate.of(2025,4, 7), 2.45),
						new Rilevazione(LocalDate.of(2025,4,14), 2.05)
					 ), 
					controller.rilevazioni("Albicocche", "nostrane"));	
		
		assertEquals(List.of(
						new Rilevazione(LocalDate.of(2025,3,24), 8.00),
						new Rilevazione(LocalDate.of(2025,3,31), 5.50), 
						new Rilevazione(LocalDate.of(2025,4, 7), 5.50),
						new Rilevazione(LocalDate.of(2025,4,14), 4.63)
					), 
					controller.rilevazioni("Ciliegie", "Bigarreau"));	
		
		assertEquals(List.of(
						new Rilevazione(LocalDate.of(2025,4,14), 7.50)
					), 
					controller.rilevazioni("Ciliegie", "Celeste"));	
		
		assertEquals(List.of(
						new Rilevazione(LocalDate.of(2025,3,31), 4.88),
						new Rilevazione(LocalDate.of(2025,4, 7), 4.63),
						new Rilevazione(LocalDate.of(2025,4,14), 4.25)
					), 
					controller.rilevazioni("Ciliegie", "Dure"));	
		
		assertEquals(List.of(
						new Rilevazione(LocalDate.of(2025,2,10), 3.82),
						new Rilevazione(LocalDate.of(2025,2,17), 3.15),
						new Rilevazione(LocalDate.of(2025,2,24), 2.85),
						new Rilevazione(LocalDate.of(2025,3, 3), 2.75),
						new Rilevazione(LocalDate.of(2025,3,10), 2.65),
						new Rilevazione(LocalDate.of(2025,3,17), 2.54),
						new Rilevazione(LocalDate.of(2025,3,24), 2.29),
						new Rilevazione(LocalDate.of(2025,3,31), 2.22),
						new Rilevazione(LocalDate.of(2025,4, 7), 2.22),
						new Rilevazione(LocalDate.of(2025,4,14), 2.14)
					), 
					controller.rilevazioni("Fragoline", "polpose"));	

		assertThrows(NoSuchElementException.class, () -> controller.rilevazioni("Ciliegie", "succose"));
		assertThrows(NoSuchElementException.class, () -> controller.rilevazioni("Pere", "succose"));
		
		assertEquals(new TreeSet<>(Set.of(
				LocalDate.of(2025,2,10),
				LocalDate.of(2025,2,17),
				LocalDate.of(2025,2,24),
				LocalDate.of(2025,3, 3),
				LocalDate.of(2025,3,10),
				LocalDate.of(2025,3,17),
				LocalDate.of(2025,3,24),
				LocalDate.of(2025,3,31),
				LocalDate.of(2025,4, 7),
				LocalDate.of(2025,4,14)
				)), 
				controller.date());
	}
	
	@Test
	void testOK_PrezzoMin() {
		assertEquals(2.05, controller.prezzoMinimo("Albicocche", "nostrane").getAsDouble(),0.01);
		assertEquals(4.63, controller.prezzoMinimo("Ciliegie", "Bigarreau").getAsDouble(), 0.01);
		assertEquals(7.50, controller.prezzoMinimo("Ciliegie", "Celeste").getAsDouble(),   0.01);
		assertEquals(4.25, controller.prezzoMinimo("Ciliegie", "Dure").getAsDouble(),      0.01);
		assertEquals(2.14, controller.prezzoMinimo("Fragoline", "polpose").getAsDouble(),  0.01);
		
		assertEquals(2.05, controller.prezzoMinimo("Albicocche").getAsDouble(),  0.01);
		assertEquals(4.25, controller.prezzoMinimo("Ciliegie").getAsDouble(),    0.01);
		assertEquals(2.14, controller.prezzoMinimo("Fragoline").getAsDouble(),   0.01);
		
		assertThrows(NoSuchElementException.class, () -> controller.prezzoMinimo("Pere"));
		assertThrows(NoSuchElementException.class, () -> controller.prezzoMinimo("Pere", "succose"));
		assertThrows(NoSuchElementException.class, () -> controller.prezzoMinimo("Albicocche", "succose"));

		assertThrows(NullPointerException.class, () -> controller.prezzoMinimo(null));
		assertThrows(NullPointerException.class, () -> controller.prezzoMinimo(null,null));
		assertThrows(IllegalArgumentException.class, () -> controller.prezzoMinimo(" "));
		assertThrows(IllegalArgumentException.class, () -> controller.prezzoMinimo(" ",null));
	}
	
	@Test
	void testOK_PrezzoMedio() {
		assertEquals(2.40, controller.prezzoMedio("Albicocche", "nostrane").getAsDouble(),0.01);
		assertEquals(5.90, controller.prezzoMedio("Ciliegie", "Bigarreau").getAsDouble(), 0.01);
		assertEquals(5.91, controller.prezzoMedio("Ciliegie", "Bigarreau").getAsDouble(), 0.01);
		assertEquals(7.50, controller.prezzoMedio("Ciliegie", "Celeste").getAsDouble(),   0.01);
		assertEquals(4.58, controller.prezzoMedio("Ciliegie", "Dure").getAsDouble(),      0.01);
		assertEquals(4.59, controller.prezzoMedio("Ciliegie", "Dure").getAsDouble(),      0.01);
		assertEquals(2.66, controller.prezzoMedio("Fragoline", "polpose").getAsDouble(),  0.01);
		
		assertEquals(2.40, controller.prezzoMedio("Albicocche").getAsDouble(),  0.01);
		assertEquals(5.61, controller.prezzoMedio("Ciliegie").getAsDouble(),    0.01);
		assertEquals(2.66, controller.prezzoMedio("Fragoline").getAsDouble(),   0.01);
		
		assertThrows(NoSuchElementException.class, () -> controller.prezzoMedio("Pere"));
		assertThrows(NoSuchElementException.class, () -> controller.prezzoMedio("Pere", "succose"));
		assertThrows(NoSuchElementException.class, () -> controller.prezzoMedio("Albicocche", "succose"));

		assertThrows(NullPointerException.class, () -> controller.prezzoMedio(null));
		assertThrows(NullPointerException.class, () -> controller.prezzoMedio(null,null));
		assertThrows(IllegalArgumentException.class, () -> controller.prezzoMedio(" "));
		assertThrows(IllegalArgumentException.class, () -> controller.prezzoMedio(" ",null));
	}
	
	@Test
	void testOK_PrezzoMax() {
		assertEquals(2.65, controller.prezzoMassimo("Albicocche", "nostrane").getAsDouble(),0.01);
		assertEquals(8.00, controller.prezzoMassimo("Ciliegie", "Bigarreau").getAsDouble(), 0.01);
		assertEquals(7.50, controller.prezzoMassimo("Ciliegie", "Celeste").getAsDouble(),   0.01);
		assertEquals(4.88, controller.prezzoMassimo("Ciliegie", "Dure").getAsDouble(),      0.01);
		assertEquals(3.82, controller.prezzoMassimo("Fragoline", "polpose").getAsDouble(),  0.01);
		
		assertEquals(2.65, controller.prezzoMassimo("Albicocche").getAsDouble(),  0.01);
		assertEquals(8.00, controller.prezzoMassimo("Ciliegie").getAsDouble(),    0.01);
		assertEquals(3.82, controller.prezzoMassimo("Fragoline").getAsDouble(),   0.01);
		
		assertThrows(NoSuchElementException.class, () -> controller.prezzoMassimo("Pere"));
		assertThrows(NoSuchElementException.class, () -> controller.prezzoMassimo("Pere", "succose"));
		assertThrows(NoSuchElementException.class, () -> controller.prezzoMassimo("Albicocche", "succose"));		
		
		assertThrows(NullPointerException.class, () -> controller.prezzoMassimo(null));
		assertThrows(NullPointerException.class, () -> controller.prezzoMassimo(null,null));
		assertThrows(IllegalArgumentException.class, () -> controller.prezzoMassimo(" "));
		assertThrows(IllegalArgumentException.class, () -> controller.prezzoMassimo(" ",null));
	}
	
	




}
