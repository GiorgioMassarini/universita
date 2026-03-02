package frutta.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import frutta.model.MappaSottotipi;
import frutta.model.Rilevazione;


class MappaSottotipiTest {

	static MappaSottotipi mappa = new MappaSottotipi();
	
	@BeforeAll static void setup() {
		//
		mappa.aggiungi( "nostrane", new Rilevazione(LocalDate.of(2025,3,24), 2.65));
		mappa.aggiungi( "nostrane", new Rilevazione(LocalDate.of(2025,3,31), 2.45));
		mappa.aggiungi( "nostrane", new Rilevazione(LocalDate.of(2025,4, 7), 2.45));
		mappa.aggiungi( "nostrane", new Rilevazione(LocalDate.of(2025,4,14), 2.05));
		//
		mappa.aggiungi( "Bigarreau", new Rilevazione(LocalDate.of(2025,3,24), 8.00));
		mappa.aggiungi( "Bigarreau", new Rilevazione(LocalDate.of(2025,3,31), 5.50));
		mappa.aggiungi( "Bigarreau", new Rilevazione(LocalDate.of(2025,4, 7), 5.50));
		mappa.aggiungi( "Bigarreau", new Rilevazione(LocalDate.of(2025,4,14), 4.63));
		//
		mappa.aggiungi( "Celeste", new Rilevazione(LocalDate.of(2025,4,14), 7.50));
		//
		mappa.aggiungi( "Dure", new Rilevazione(LocalDate.of(2025,3,31), 4.88));
		mappa.aggiungi( "Dure", new Rilevazione(LocalDate.of(2025,4, 7), 4.63));
		mappa.aggiungi( "Dure", new Rilevazione(LocalDate.of(2025,4,14), 4.25));
		//
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,2,10), 3.82));		
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,2,17), 3.15));
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,2,24), 2.85));
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,3, 3), 2.75));
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,3,10), 2.65));
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,3,17), 2.54));
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,3,24), 2.29));
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,3,31), 2.22));
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,4, 7), 2.22));
		mappa.aggiungi("polpose", new Rilevazione(LocalDate.of(2025,4,14), 2.14));
	}

	
	@Test
	void testOK() {
		assertEquals(Set.of("nostrane", "Bigarreau", "Celeste", "Dure", "polpose"), mappa.sottotipi());
		
		assertEquals(List.of(
						new Rilevazione(LocalDate.of(2025,3,24), 2.65),
						new Rilevazione(LocalDate.of(2025,3,31), 2.45),
						new Rilevazione(LocalDate.of(2025,4, 7), 2.45),
						new Rilevazione(LocalDate.of(2025,4,14), 2.05)
					 ), 
					 mappa.rilevazioniPerSottotipo("nostrane"));	
		
		assertEquals(List.of(
				new Rilevazione(LocalDate.of(2025,3,24), 8.00),
				new Rilevazione(LocalDate.of(2025,3,31), 5.50), 
				new Rilevazione(LocalDate.of(2025,4, 7), 5.50),
				new Rilevazione(LocalDate.of(2025,4,14), 4.63)
			 ), 
			 mappa.rilevazioniPerSottotipo("Bigarreau"));	
		
		assertEquals(List.of(
				new Rilevazione(LocalDate.of(2025,4,14), 7.50)
			 ), 
			 mappa.rilevazioniPerSottotipo("Celeste"));	
		
		assertEquals(List.of(
				new Rilevazione(LocalDate.of(2025,3,31), 4.88),
				new Rilevazione(LocalDate.of(2025,4, 7), 4.63),
				new Rilevazione(LocalDate.of(2025,4,14), 4.25)
			 ), 
			 mappa.rilevazioniPerSottotipo("Dure"));	
		
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
			 mappa.rilevazioniPerSottotipo("polpose"));	

		assertThrows(NoSuchElementException.class, () -> mappa.rilevazioniPerSottotipo("succose"));
	}
	
	@Test
	void testOK_PrezzoMin() {
		assertEquals(2.05, mappa.prezzoMinimo("nostrane").getAsDouble(),  0.01);
		assertEquals(4.63, mappa.prezzoMinimo("Bigarreau").getAsDouble(), 0.01);
		assertEquals(7.50, mappa.prezzoMinimo("Celeste").getAsDouble(),   0.01);
		assertEquals(4.25, mappa.prezzoMinimo("Dure").getAsDouble(),      0.01);
		assertEquals(2.14, mappa.prezzoMinimo("polpose").getAsDouble(),   0.01);
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMinimo("succose"));
	}
	
	@Test
	void testOK_PrezzoMedio() {
		assertEquals(2.40, mappa.prezzoMedio("nostrane").getAsDouble(),  0.01);
		assertEquals(5.90, mappa.prezzoMedio("Bigarreau").getAsDouble(), 0.01);
		assertEquals(5.91, mappa.prezzoMedio("Bigarreau").getAsDouble(), 0.01);
		assertEquals(7.50, mappa.prezzoMedio("Celeste").getAsDouble(),   0.01);
		assertEquals(4.58, mappa.prezzoMedio("Dure").getAsDouble(),      0.01);
		assertEquals(4.59, mappa.prezzoMedio("Dure").getAsDouble(),      0.01);
		assertEquals(2.66, mappa.prezzoMedio("polpose").getAsDouble(),   0.01);
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMedio("succose"));
	}
	
	@Test
	void testOK_PrezzoMax() {
		assertEquals(2.65, mappa.prezzoMassimo("nostrane").getAsDouble(),  0.01);
		assertEquals(8.00, mappa.prezzoMassimo("Bigarreau").getAsDouble(), 0.01);
		assertEquals(7.50, mappa.prezzoMassimo("Celeste").getAsDouble(),   0.01);
		assertEquals(4.88, mappa.prezzoMassimo("Dure").getAsDouble(),      0.01);
		assertEquals(3.82, mappa.prezzoMassimo("polpose").getAsDouble(),   0.01);
		assertThrows(NoSuchElementException.class, () -> mappa.prezzoMassimo("succose"));
	}
	
	@Test
	void testKO_AggiungiArg1null() {
		assertThrows(NullPointerException.class, () -> mappa.aggiungi(null, new Rilevazione(LocalDate.of(2025,3,24), 2.65)));
	}
	
	@Test
	void testKO_AggiungiArg2null() {
		assertThrows(NullPointerException.class, () -> mappa.aggiungi("Vignola", (Rilevazione)null));
	}
	
	@Test
	void testKO_AggiungiArg1Blank() {
		assertThrows(IllegalArgumentException.class, () -> mappa.aggiungi(" ", new Rilevazione(LocalDate.of(2025,3,24), 2.65)));
	}
	
	@Test
	void testOK_EqualsHashCode() {
		var mappa2 = new MappaSottotipi();
		mappa.sottotipi().stream().forEach(sottitipo -> mappa2.aggiungi(sottitipo, mappa.rilevazioniPerSottotipo(sottitipo)));
		assertEquals(mappa, mappa2);
		assertEquals(mappa.hashCode(), mappa2.hashCode());
	}

}
