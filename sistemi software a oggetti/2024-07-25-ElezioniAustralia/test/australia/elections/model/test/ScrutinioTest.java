package australia.elections.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import australia.elections.model.Risultato;
import australia.elections.model.Scheda;
import australia.elections.model.Scrutinio;

class ScrutinioTest {

	@Test
	void testOK_senzaScorrimenti() {
		// Test ispirato a https://peo.gov.au/understand-our-parliament/having-your-say/elections-and-voting/federal-elections
		// 20 schede, prime preferenze: Maria = 8, Ari = 6, Joe = 4, Lauren = 2
		// ma seconde preferenze in favore di Ari
		var schede = List.of(
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 4, "Lauren", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 4, "Joe", 2, "Lauren", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 4, "Joe", 2, "Lauren", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4))),

				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 4, "Lauren", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 4, "Lauren", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 1, "Joe", 3, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 1, "Joe", 3, "Lauren", 4))),
				
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 1, "Lauren", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 1, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 1, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 3, "Joe", 1, "Lauren", 4))),
				
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 4, "Lauren", 1)))
				);
		
		var scrutinio = new Scrutinio(schede);
		assertEquals(schede.size(), scrutinio.getTotaleVoti());
		
		Risultato risultati = scrutinio.scrutina();
		//System.out.println(risultati);
				
		assertEquals(9, risultati.mappaRisultati().get("Maria"));
		assertEquals(11,risultati.mappaRisultati().get("Ari"));
		assertNull(risultati.mappaRisultati().get("Joe"));
		assertNull(risultati.mappaRisultati().get("Lauren"));
	}
	
	@Test
	void testOK_conScorrimenti() {
		// Test ispirato a https://peo.gov.au/understand-our-parliament/having-your-say/elections-and-voting/federal-elections
		// 20 schede, prime preferenze: Maria = 8, Ari = 6, Joe = 4, Lauren = 2
		// ma seconde preferenze in favore di Maria
		var schede = List.of(
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 4, "Lauren", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 4, "Joe", 2, "Lauren", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 4, "Joe", 2, "Lauren", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4))),

				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 4, "Lauren", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 4, "Lauren", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 1, "Joe", 3, "Lauren", 4))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 1, "Joe", 3, "Lauren", 4))),
				
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 1, "Lauren", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 4, "Joe", 1, "Lauren", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 4, "Joe", 1, "Lauren", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 3, "Joe", 1, "Lauren", 4))),
				
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 4, "Lauren", 1)))
				);
		
		var scrutinio = new Scrutinio(schede);
		assertEquals(schede.size(), scrutinio.getTotaleVoti());
		
		Risultato risultati = scrutinio.scrutina();
		//System.out.println(risultati);
				
		assertEquals(11, risultati.mappaRisultati().get("Maria"));
		assertEquals(9,risultati.mappaRisultati().get("Ari"));
		assertNull(risultati.mappaRisultati().get("Joe"));
		assertNull(risultati.mappaRisultati().get("Lauren"));
	}
	
	@Test
	void testOK_conScorrimenti_6candidati() {
		// Test ispirato a https://peo.gov.au/understand-our-parliament/having-your-say/elections-and-voting/federal-elections
		// 30 schede, prime preferenze: Maria = 8, Ari = 6, Joe = 4, Lauren = 2, Juliet = 5, Fred = 5 
		var schede = List.of(
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 6, "Joe", 3, "Lauren", 4, "Juliet", 2, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 4, "Lauren", 6, "Juliet", 3, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 6, "Joe", 2, "Lauren", 3, "Juliet", 4, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 4, "Joe", 2, "Lauren", 5, "Juliet", 6, "Fred", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 6, "Lauren", 4, "Juliet", 5, "Fred", 3))),

				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 1, "Joe", 4, "Lauren", 2, "Juliet", 5, "Fred", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 4, "Lauren", 2, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 1, "Joe", 2, "Lauren", 4, "Juliet", 3, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 6, "Juliet", 4, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 1, "Joe", 3, "Lauren", 4, "Juliet", 2, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 1, "Joe", 3, "Lauren", 4, "Juliet", 5, "Fred", 2))),
				
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 1, "Lauren", 3, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 4, "Joe", 1, "Lauren", 5, "Juliet", 2, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 4, "Joe", 1, "Lauren", 2, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 3, "Joe", 1, "Lauren", 6, "Juliet", 4, "Fred", 5))),
				
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 1, "Juliet", 5, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 4, "Lauren", 1, "Juliet", 6, "Fred", 5))),

				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 1, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 1, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 6, "Juliet", 1, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 6, "Lauren", 4, "Juliet", 1, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 6, "Joe", 4, "Lauren", 3, "Juliet", 1, "Fred", 5))),

				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 6, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 5, "Juliet", 4, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 6, "Juliet", 5, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 2, "Joe", 3, "Lauren", 4, "Juliet", 6, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 5, "Lauren", 4, "Juliet", 2, "Fred", 1)))
);
		
		var scrutinio = new Scrutinio(schede);
		assertEquals(schede.size(), scrutinio.getTotaleVoti());
		
		Risultato risultati = scrutinio.scrutina();
		//System.out.println(risultati);
				
		assertEquals(13, risultati.mappaRisultati().get("Maria"));
		assertEquals(17,risultati.mappaRisultati().get("Ari"));
		assertNull(risultati.mappaRisultati().get("Juliet"));
		assertNull(risultati.mappaRisultati().get("Lauren"));
		assertNull(risultati.mappaRisultati().get("Joe"));
		assertNull(risultati.mappaRisultati().get("Fred"));
	}
	
	@Test
	void testOK_conScorrimenti_6candidati_piuIterazioni() {
		// Test ispirato a https://peo.gov.au/understand-our-parliament/having-your-say/elections-and-voting/federal-elections
		// 30 schede, prime preferenze: Maria = 8, Ari = 6, Joe = 4, Lauren = 2, Juliet = 5, Fred = 5 
		// (cambiano le seconde e terze preferenze) 
		var schede = List.of(
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 6, "Joe", 3, "Lauren", 4, "Juliet", 2, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 4, "Lauren", 6, "Juliet", 3, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 6, "Joe", 2, "Lauren", 3, "Juliet", 4, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 4, "Joe", 2, "Lauren", 5, "Juliet", 6, "Fred", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 6, "Lauren", 4, "Juliet", 5, "Fred", 3))),

				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 1, "Joe", 4, "Lauren", 2, "Juliet", 5, "Fred", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 4, "Lauren", 2, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 1, "Joe", 2, "Lauren", 4, "Juliet", 3, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 6, "Juliet", 4, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 1, "Joe", 3, "Lauren", 4, "Juliet", 2, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 1, "Joe", 3, "Lauren", 4, "Juliet", 5, "Fred", 2))),
				
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 1, "Lauren", 3, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 4, "Joe", 1, "Lauren", 5, "Juliet", 2, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 4, "Joe", 1, "Lauren", 2, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 3, "Joe", 1, "Lauren", 6, "Juliet", 2, "Fred", 5))), //diff
				
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 5, "Joe", 3, "Lauren", 1, "Juliet", 2, "Fred", 6))), //diff 
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 4, "Lauren", 1, "Juliet", 6, "Fred", 5))),

				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 1, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 1, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 6, "Juliet", 1, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 6, "Lauren", 4, "Juliet", 1, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 6, "Joe", 4, "Lauren", 3, "Juliet", 1, "Fred", 5))),

				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 6, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 5, "Juliet", 4, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 6, "Juliet", 5, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 2, "Joe", 3, "Lauren", 4, "Juliet", 6, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 6, "Joe", 5, "Lauren", 4, "Juliet", 2, "Fred", 1))) //diff
);
		
		var scrutinio = new Scrutinio(schede);
		assertEquals(schede.size(), scrutinio.getTotaleVoti());

		Risultato risultati = scrutinio.scrutina();
		//System.out.println(risultati);
				
		assertEquals(14, risultati.mappaRisultati().get("Maria"));
		assertEquals(16,risultati.mappaRisultati().get("Ari"));
		assertNull(risultati.mappaRisultati().get("Juliet"));
		assertNull(risultati.mappaRisultati().get("Lauren"));
		assertNull(risultati.mappaRisultati().get("Joe"));
		assertNull(risultati.mappaRisultati().get("Fred"));
	}
	
	@Test
	void testOK__conScorrimenti_6candidati_3finalisti() {
		// Test ispirato a https://peo.gov.au/understand-our-parliament/having-your-say/elections-and-voting/federal-elections
		// 30 schede, prime preferenze: Maria = 8, Ari = 6, Joe = 4, Lauren = 2, Juliet = 5, Fred = 5 
		// (cambiano le seconde e terze preferenze)
		// (l'iterazione termina quando ci sono ancora tre candidati in gara perché uno raggiunge già il 50%+1)
		var schede = List.of(
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 6, "Joe", 3, "Lauren", 4, "Juliet", 2, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 4, "Lauren", 6, "Juliet", 3, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 6, "Joe", 2, "Lauren", 3, "Juliet", 4, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 4, "Joe", 2, "Lauren", 5, "Juliet", 6, "Fred", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 6, "Lauren", 4, "Juliet", 5, "Fred", 3))),

				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 1, "Joe", 4, "Lauren", 2, "Juliet", 5, "Fred", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 4, "Lauren", 2, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 1, "Joe", 2, "Lauren", 4, "Juliet", 3, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 6, "Juliet", 4, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 1, "Joe", 3, "Lauren", 4, "Juliet", 2, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 1, "Joe", 3, "Lauren", 4, "Juliet", 5, "Fred", 2))),
				
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 1, "Lauren", 3, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 1, "Lauren", 5, "Juliet", 4, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 3, "Joe", 1, "Lauren", 2, "Juliet", 6, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 1, "Lauren", 3, "Juliet", 6, "Fred", 5))), 
				
				//new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 3, "Joe", 2, "Lauren", 1, "Juliet", 5, "Fred", 6))), 
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 3, "Joe", 5, "Lauren", 1, "Juliet", 2, "Fred", 6))), //modified
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 4, "Lauren", 1, "Juliet", 6, "Fred", 5))),

				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 1, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 1, "Fred", 6))),
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 6, "Juliet", 1, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 6, "Lauren", 4, "Juliet", 1, "Fred", 5))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 6, "Joe", 4, "Lauren", 3, "Juliet", 1, "Fred", 5))),
				
				//new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 6, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 1))), //modified
				new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 5, "Juliet", 4, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 6, "Juliet", 5, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 2, "Joe", 3, "Lauren", 4, "Juliet", 6, "Fred", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 5, "Lauren", 4, "Juliet", 6, "Fred", 1))) 
				);
		
		var scrutinio = new Scrutinio(schede);
		assertEquals(schede.size(), scrutinio.getTotaleVoti());

		Risultato risultati = scrutinio.scrutina();
		// System.out.println(risultati);
		
		assertEquals(8, risultati.mappaRisultati().get("Maria")); //modified (was 9)
		assertEquals(16,risultati.mappaRisultati().get("Ari"));
		assertEquals(6,risultati.mappaRisultati().get("Juliet")); //modified (was 5)
		assertNull(risultati.mappaRisultati().get("Lauren"));
		assertNull(risultati.mappaRisultati().get("Joe"));
		assertNull(risultati.mappaRisultati().get("Fred"));

	}
	
	
	@Test
	void testOK__conScorrimenti_3candidati_paritaAssoluta_VincitoreSceltoCasualmente() {
		// Test ispirato a https://peo.gov.au/understand-our-parliament/having-your-say/elections-and-voting/federal-elections
		// 18 schede, prime preferenze: Maria = 6, Ari = 6, Joe = 6 
		// (cambiano le seconde e terze preferenze)
		// Risultato in parità, si elimina un candidato a caso a ogni iterazione
		var schede = List.of(
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3))),

				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 1, "Joe", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 1, "Joe", 3))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 1, "Joe", 3))),
				
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 3, "Joe", 1))),				
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 3, "Joe", 1))),
				new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 3, "Joe", 1)))
				);
		
		var scrutinio = new Scrutinio(schede);
		assertEquals(schede.size(), scrutinio.getTotaleVoti());
		
		Risultato risultati = scrutinio.scrutina();
		//System.out.println(risultati);

		risultati.mappaRisultati().entrySet().forEach(entry -> assertEquals(18, risultati.mappaRisultati().get(entry.getKey())));
		// A seconda di quale candidato viene casualmente eliminato, l'unica riga rimasta può essere una qualsiasi delle tre
	}
	
	@Test
	void testKO_listaNulla() {
		assertThrows(NullPointerException.class, () -> new Scrutinio(null));
	}
	
	@Test
	void testKO_listaVuota() {
		assertThrows(IllegalArgumentException.class, () -> new Scrutinio(List.of()));
	}

}
