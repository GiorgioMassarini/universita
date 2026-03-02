package australia.elections.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import australia.elections.controller.ControllerMock;
import australia.elections.model.Risultato;
import australia.elections.persistence.BadFileFormatException;


class ControllerTest {

	@Test
	void testOK() throws IOException, BadFileFormatException {
		var controller = new ControllerMock();
		
		@SuppressWarnings("unused")
		String schedeAsString = controller.leggiSchede(null); // il controller mock ignora il file ricevuto
		
		Risultato risultati = controller.scrutina();
		assertEquals(30,controller.getTotaleVoti());
		
		assertEquals(8, risultati.mappaRisultati().get("Maria")); //modified (was 9)
		assertEquals(16,risultati.mappaRisultati().get("Ari"));
		assertEquals(6,risultati.mappaRisultati().get("Juliet")); //modified (was 5)
		assertNull(risultati.mappaRisultati().get("Lauren"));
		assertNull(risultati.mappaRisultati().get("Joe"));
		assertNull(risultati.mappaRisultati().get("Fred"));
	}
	
}
