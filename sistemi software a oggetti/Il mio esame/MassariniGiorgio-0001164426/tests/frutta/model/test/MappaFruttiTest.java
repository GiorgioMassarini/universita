package frutta.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import frutta.model.MappaFrutti;
import frutta.model.Rilevazione;


class MappaFruttiTest {

	static MappaFrutti mappa = new MappaFrutti();
	
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
	}

	
	@Test
	void testOK() {
		assertEquals(new TreeSet<>(Set.of("Albicocche", "Ciliegie", "Fragoline")), mappa.tipiFrutta());
		
		assertEquals(1, mappa.sottotipi("Albicocche").size());
		assertEquals(3, mappa.sottotipi("Ciliegie").size());
		assertEquals(1, mappa.sottotipi("Fragoline").size());
		
		assertEquals(new TreeSet<>(Set.of("nostrane")), mappa.sottotipi("Albicocche"));
		assertEquals(new TreeSet<>(Set.of("Dure","Bigarreau","Celeste")), mappa.sottotipi("Ciliegie"));
		assertEquals(new TreeSet<>(Set.of("polpose")), mappa.sottotipi("Fragoline"));
		
		assertEquals(List.of(
						new Rilevazione(LocalDate.of(2025,3,24), 2.65),
						new Rilevazione(LocalDate.of(2025,3,31), 2.45),
						new Rilevazione(LocalDate.of(2025,4, 7), 2.45),
						new Rilevazione(LocalDate.of(2025,4,14), 2.05)
					 ), 
					 mappa.rilevazioniPerSottotipo("Albicocche", "nostrane"));	
		
		assertEquals(List.of(
				new Rilevazione(LocalDate.of(2025,3,24), 8.00),
				new Rilevazione(LocalDate.of(2025,3,31), 5.50), 
				new Rilevazione(LocalDate.of(2025,4, 7), 5.50),
				new Rilevazione(LocalDate.of(2025,4,14), 4.63)
			 ), 
			 mappa.rilevazioniPerSottotipo("Ciliegie", "Bigarreau"));	
		
		assertEquals(List.of(
				new Rilevazione(LocalDate.of(2025,4,14), 7.50)
			 ), 
			 mappa.rilevazioniPerSottotipo("Ciliegie", "Celeste"));	
		
		assertEquals(List.of(
				new Rilevazione(LocalDate.of(2025,3,31), 4.88),
				new Rilevazione(LocalDate.of(2025,4, 7), 4.63),
				new Rilevazione(LocalDate.of(2025,4,14), 4.25)
			 ), 
			 mappa.rilevazioniPerSottotipo("Ciliegie", "Dure"));	
		
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
			 mappa.rilevazioniPerSottotipo("Fragoline", "polpose"));	

		assertThrows(NoSuchElementException.class, () -> mappa.rilevazioniPerSottotipo("Ciliegie", "succose"));
		assertThrows(NoSuchElementException.class, () -> mappa.rilevazioniPerSottotipo("Pere", "succose"));
		
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
				)), mappa.date());
	}
	
	@Test
	void testOK_PrezzoMin() {
		assertEquals(2.05, mappa.prezzoMinimo("Albicocche", "nostrane").getAsDouble(),0.01);
		assertEquals(4.63, mappa.prezzoMinimo("Ciliegie", "Bigarreau").getAsDouble(), 0.01);
		assertEquals(7.50, mappa.prezzoMinimo("Ciliegie", "Celeste").getAsDouble(),   0.01);
		assertEquals(4.25, mappa.prezzoMinimo("Ciliegie", "Dure").getAsDouble(),      0.01);
		assertEquals(2.14, mappa.prezzoMinimo("Fragoline", "polpose").getAsDouble(),  0.01);
		
		assertEquals(2.05, mappa.prezzoMinimo("Albicocche").getAsDouble(),  0.01);
		assertEquals(4.25, mappa.prezzoMinimo("Ciliegie").getAsDouble(),    0.01);
		assertEquals(2.14, mappa.prezzoMinimo("Fragoline").getAsDouble(),   0.01);
		
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMinimo("Pere"));
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMinimo("Pere", "succose"));
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMinimo("Albicocche", "succose"));

		assertThrows(NullPointerException.class, () -> mappa.prezzoMinimo(null));
		assertThrows(NullPointerException.class, () -> mappa.prezzoMinimo(null,null));
		assertThrows(IllegalArgumentException.class, () -> mappa.prezzoMinimo(" "));
		assertThrows(IllegalArgumentException.class, () -> mappa.prezzoMinimo(" ",null));
	}
	
	@Test
	void testOK_PrezzoMedio() {
		assertEquals(2.40, mappa.prezzoMedio("Albicocche", "nostrane").getAsDouble(),0.01);
		assertEquals(5.90, mappa.prezzoMedio("Ciliegie", "Bigarreau").getAsDouble(), 0.01);
		assertEquals(5.91, mappa.prezzoMedio("Ciliegie", "Bigarreau").getAsDouble(), 0.01);
		assertEquals(7.50, mappa.prezzoMedio("Ciliegie", "Celeste").getAsDouble(),   0.01);
		assertEquals(4.58, mappa.prezzoMedio("Ciliegie", "Dure").getAsDouble(),      0.01);
		assertEquals(4.59, mappa.prezzoMedio("Ciliegie", "Dure").getAsDouble(),      0.01);
		assertEquals(2.66, mappa.prezzoMedio("Fragoline", "polpose").getAsDouble(),  0.01);
		
		assertEquals(2.40, mappa.prezzoMedio("Albicocche").getAsDouble(),  0.01);
		assertEquals(5.61, mappa.prezzoMedio("Ciliegie").getAsDouble(),    0.01);
		assertEquals(2.66, mappa.prezzoMedio("Fragoline").getAsDouble(),   0.01);
		
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMedio("Pere"));
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMedio("Pere", "succose"));
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMedio("Albicocche", "succose"));

		assertThrows(NullPointerException.class, () -> mappa.prezzoMedio(null));
		assertThrows(NullPointerException.class, () -> mappa.prezzoMedio(null,null));
		assertThrows(IllegalArgumentException.class, () -> mappa.prezzoMedio(" "));
		assertThrows(IllegalArgumentException.class, () -> mappa.prezzoMedio(" ",null));

	}
	
	@Test
	void testOK_PrezzoMax() {
		assertEquals(2.65, mappa.prezzoMassimo("Albicocche", "nostrane").getAsDouble(),0.01);
		assertEquals(8.00, mappa.prezzoMassimo("Ciliegie", "Bigarreau").getAsDouble(), 0.01);
		assertEquals(7.50, mappa.prezzoMassimo("Ciliegie", "Celeste").getAsDouble(),   0.01);
		assertEquals(4.88, mappa.prezzoMassimo("Ciliegie", "Dure").getAsDouble(),      0.01);
		assertEquals(3.82, mappa.prezzoMassimo("Fragoline", "polpose").getAsDouble(),  0.01);
		
		assertEquals(2.65, mappa.prezzoMassimo("Albicocche").getAsDouble(),  0.01);
		assertEquals(8.00, mappa.prezzoMassimo("Ciliegie").getAsDouble(),    0.01);
		assertEquals(3.82, mappa.prezzoMassimo("Fragoline").getAsDouble(),   0.01);
		
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMassimo("Pere"));
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMassimo("Pere", "succose"));
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMassimo("Albicocche", "succose"));

		assertThrows(NullPointerException.class, () -> mappa.prezzoMassimo(null));
		assertThrows(NullPointerException.class, () -> mappa.prezzoMassimo(null,null));
		assertThrows(IllegalArgumentException.class, () -> mappa.prezzoMassimo(" "));
		assertThrows(IllegalArgumentException.class, () -> mappa.prezzoMassimo(" ",null));

	}
	
	@Test
	void testKO_AggiungiArg1null() {
		assertThrows(NullPointerException.class, () -> mappa.aggiungi(null, "nostrane", new Rilevazione(LocalDate.of(2025,3,24), 2.65)));
	}
	
	@Test
	void testKO_AggiungiArg2null() {
		assertThrows(NullPointerException.class, () -> mappa.aggiungi("Ciliegie", null, new Rilevazione(LocalDate.of(2025,3,24), 2.65)));
	}
	@Test
	void testKO_AggiungiArg3null() {
		assertThrows(NullPointerException.class, () -> mappa.aggiungi("Ciliegie", "Vignola", (Rilevazione)null));
	}
	
	@Test
	void testKO_AggiungiArg1Blank() {
		assertThrows(IllegalArgumentException.class, () -> mappa.aggiungi(" ", "nostrane", new Rilevazione(LocalDate.of(2025,3,24), 2.65)));
	}
	
	@Test
	void testKO_AggiungiArg2Blank() {
		assertThrows(IllegalArgumentException.class, () -> mappa.aggiungi("Ciliegie", " ", new Rilevazione(LocalDate.of(2025,3,24), 2.65)));
	}
	
	@Test
	void testOK_EqualsHashCode() {
		var mappa2 = new MappaFrutti();
		mappa.tipiFrutta().stream().forEach(tipoFrutta -> {
			var sottotipi = mappa.sottotipi(tipoFrutta);
			sottotipi.stream().forEach(sottotipo -> mappa2.aggiungi(tipoFrutta, sottotipo, mappa.rilevazioniPerSottotipo(tipoFrutta, sottotipo)));
			});
		assertEquals(mappa, mappa2);
		assertEquals(mappa.hashCode(), mappa2.hashCode());
	}

}
