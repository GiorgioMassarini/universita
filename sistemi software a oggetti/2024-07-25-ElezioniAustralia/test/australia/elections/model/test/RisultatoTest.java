package australia.elections.model.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

import australia.elections.model.Risultato;


class RisultatoTest {

	@Test
	void testOK_4() {
		var mappaRes = new TreeMap<>(Map.of("Maria", 14L, "Ari", 9L, "Joe", 5L, "Lauren", 1L));
		var risultato = new Risultato(mappaRes);
		assertEquals(mappaRes, risultato.mappaRisultati());
		var vincitore = risultato.vincitore();
		assertEquals("Maria", vincitore.getKey());
		assertEquals(14, vincitore.getValue());
	}
	
	@Test
	void testOK_2() {
		var mappaRes = new TreeMap<>(Map.of("Maria", 14L, "Ari", 9L));
		var risultato = new Risultato(mappaRes);
		assertEquals(mappaRes, risultato.mappaRisultati());
		var vincitore = risultato.vincitore();
		assertEquals("Maria", vincitore.getKey());
		assertEquals(14, vincitore.getValue());
	}
	
	@Test
	void testOK_1() {
		var mappaRes = new TreeMap<>(Map.of("Maria", 20L));
		var risultato = new Risultato(mappaRes);
		assertEquals(mappaRes, risultato.mappaRisultati());
		var vincitore = risultato.vincitore();
		assertEquals("Maria", vincitore.getKey());
		assertEquals(20, vincitore.getValue());
	}
	
	@Test
	void testOK_Parita() {
		var mappaRes = new TreeMap<>(Map.of("Maria", 10L, "Ari", 10L));
		var risultato = new Risultato(mappaRes);
		assertEquals(mappaRes, risultato.mappaRisultati());
		var vincitore = risultato.vincitore();
		assertTrue(mappaRes.keySet().contains(vincitore.getKey()));
		assertEquals(10, vincitore.getValue());
	}
	
	@Test
	void testKO_MappaNulla() {
		assertThrows(NullPointerException.class, () -> new Risultato(null));
	}
	
	@Test
	void testKO_MappaVuota() {
		var mappaRes = new TreeMap<String,Long>(Map.of());
		assertEquals(0, mappaRes.size());
		assertThrows(IllegalArgumentException.class, () -> new Risultato(mappaRes));
	}
	
}
