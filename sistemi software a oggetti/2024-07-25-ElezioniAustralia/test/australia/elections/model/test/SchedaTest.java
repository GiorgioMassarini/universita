package australia.elections.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import australia.elections.model.Scheda;

class SchedaTest {

	@Test
	void testOK_1() {
		var candidati = new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 4));
		assertEquals(4, candidati.size());
		var scheda = new Scheda(candidati);
		assertEquals(List.of("Ari", "Joe", "Maria", "Lauren"), scheda.candidatiInOrdineDiPreferenza());
		assertEquals(1, scheda.ordineDiPreferenza("Ari"));
		assertEquals(2, scheda.ordineDiPreferenza("Joe"));
		assertEquals(3, scheda.ordineDiPreferenza("Maria"));
		assertEquals(4, scheda.ordineDiPreferenza("Lauren"));
	}
	
	@Test
	void testKO_PreferenzaDuplicata() {
		var candidati = new TreeMap<>(Map.of("Maria", 3, "Ari", 3, "Joe", 2, "Lauren", 4));
		assertEquals(4, candidati.size());
		assertThrows(IllegalArgumentException.class, () -> new Scheda(candidati));
	}
	
	@Test
	void testKO_PreferenzaFuoriRangeOltreMax() {
		var candidati = new TreeMap<>(Map.of("Maria", 3, "Ari", 5, "Joe", 2, "Lauren", 4));
		assertEquals(4, candidati.size());
		assertThrows(IllegalArgumentException.class, () -> new Scheda(candidati));
	}
	
	@Test
	void testKO_PreferenzaFuoriRangeMinoreDi1() {
		var candidati = new TreeMap<>(Map.of("Maria", 0, "Ari", 1, "Joe", 2, "Lauren", 4));
		assertEquals(4, candidati.size());
		assertThrows(IllegalArgumentException.class, () -> new Scheda(candidati));
	}
	
	@Test
	void testKO_MappaNulla() {
		assertThrows(NullPointerException.class, () -> new Scheda(null));
	}
	
	@Test
	void testKO_MappaVuota() {
		var candidati = new TreeMap<String,Integer>(Map.of());
		assertEquals(0, candidati.size());
		assertThrows(IllegalArgumentException.class, () -> new Scheda(candidati));
	}
	
	@Test
	void testKO_OrdineDiPreferenza_NomeNull() {
		var candidati = new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 4));
		assertEquals(4, candidati.size());
		var scheda = new Scheda(candidati);
		assertThrows(NullPointerException.class, () -> scheda.ordineDiPreferenza(null));
	}
	
	@Test
	void testKO_OrdineDiPreferenza() {
		var candidati = new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 4));
		assertEquals(4, candidati.size());
		var scheda = new Scheda(candidati);
		assertThrows(IllegalArgumentException.class, () -> scheda.ordineDiPreferenza(""));
	}
	
	@Test
	void testOK_successivo() {
		var candidati = new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 4));
		assertEquals(4, candidati.size());
		var scheda = new Scheda(candidati);
		assertEquals(List.of("Ari", "Joe", "Maria", "Lauren"), scheda.candidatiInOrdineDiPreferenza());
		assertEquals(1, scheda.ordineDiPreferenza("Ari"));
		assertEquals(2, scheda.ordineDiPreferenza("Joe"));
		assertEquals(3, scheda.ordineDiPreferenza("Maria"));
		assertEquals(4, scheda.ordineDiPreferenza("Lauren"));
		
		assertEquals(Optional.of("Joe"),    scheda.successivo("Ari"));
		assertEquals(Optional.of("Maria"),  scheda.successivo("Joe"));
		assertEquals(Optional.of("Lauren"), scheda.successivo("Maria"));
		assertEquals(Optional.empty(),      scheda.successivo("Lauren"));
		
		assertEquals(Optional.of("Joe"),    scheda.successivoFra("Ari",    Set.of("Ari", "Joe", "Maria", "Lauren")));
		assertEquals(Optional.of("Maria"),  scheda.successivoFra("Joe",    Set.of("Ari", "Joe", "Maria", "Lauren")));
		assertEquals(Optional.of("Lauren"), scheda.successivoFra("Maria",  Set.of("Ari", "Joe", "Maria", "Lauren")));
		assertEquals(Optional.empty(),      scheda.successivoFra("Lauren", Set.of("Ari", "Joe", "Maria", "Lauren")));
		
		assertEquals(Optional.of("Maria"),  scheda.successivoFra("Ari",    Set.of("Ari", "Maria", "Lauren")));
		assertEquals(Optional.of("Lauren"), scheda.successivoFra("Ari",    Set.of("Ari", "Lauren")));
		assertEquals(Optional.empty(),  	scheda.successivoFra("Ari",    Set.of("Ari")));
		
		assertEquals(Optional.of("Maria"),  scheda.successivoFra("Joe",    Set.of("Ari", "Maria", "Lauren")));
		assertEquals(Optional.of("Lauren"), scheda.successivoFra("Joe",    Set.of("Ari", "Lauren")));
		assertEquals(Optional.empty(),  	scheda.successivoFra("Joe",    Set.of("Ari")));
		
		assertEquals(Optional.of("Lauren"), scheda.successivoFra("Maria",  Set.of("Ari", "Maria", "Lauren")));
		assertEquals(Optional.of("Lauren"), scheda.successivoFra("Maria",  Set.of("Ari", "Lauren")));
		assertEquals(Optional.empty(),  	scheda.successivoFra("Maria",  Set.of("Ari")));
		assertEquals(Optional.empty(),  	scheda.successivoFra("Maria",  Set.of()));
		
	}
}
