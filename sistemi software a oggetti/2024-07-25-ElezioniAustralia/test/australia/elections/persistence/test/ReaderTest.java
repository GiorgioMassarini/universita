package australia.elections.persistence.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

import australia.elections.persistence.BadFileFormatException;
import australia.elections.persistence.MySchedeReader;


public class ReaderTest {

	@Test
	public void testOK_4candidati() throws IOException, BadFileFormatException {
		String fakefile = 
				  "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 2, Joe, 3\n"
				+ "Maria, 1, Ari, 2, Joe, 3\n"
				+ "Maria, 1, Ari, 2, Joe, 3\n"
				+ "Maria, 3, Ari, 1, Joe, 2\n"
				+ "Maria, 3, Ari, 1, Joe, 2\n"
				+ "Maria, 3, Ari, 1, Joe, 2\n"
				+ "Maria, 2, Ari, 1, Joe, 3\n"
				+ "Maria, 2, Ari, 1, Joe, 3\n"
				+ "Maria, 2, Ari, 1, Joe, 3\n"
				+ "Maria, 3, Ari, 2, Joe, 1\n"
				+ "Maria, 3, Ari, 2, Joe, 1\n"
				+ "Maria, 3, Ari, 2, Joe, 1\n"
				+ "Maria, 2, Ari, 3, Joe, 1\n"
				+ "Maria, 2, Ari, 3, Joe, 1\n"
				+ "Maria, 2, Ari, 3, Joe, 1";
		
		@SuppressWarnings("unused")
		var schedeLette = new MySchedeReader().leggiSchede(new StringReader(fakefile));
		//System.out.println(schedeLette);
	}
	
	@Test
	public void testOK_6candidati() throws IOException, BadFileFormatException {
		String fakefile = 
				    "Maria, 1, Ari, 3, Joe, 2, Lauren, 4, Juliet, 5, Fred, 6\n"
				  + "Maria, 1, Ari, 3, Joe, 2, Lauren, 4, Juliet, 5, Fred, 6\n"
				  + "Maria, 1, Ari, 2, Joe, 3, Lauren, 4, Juliet, 6, Fred, 5\n"
				  + "Maria, 1, Ari, 6, Joe, 3, Lauren, 4, Juliet, 2, Fred, 5\n"
				  + "Maria, 1, Ari, 2, Joe, 4, Lauren, 6, Juliet, 3, Fred, 5\n"
				  + "Maria, 1, Ari, 6, Joe, 2, Lauren, 3, Juliet, 4, Fred, 5\n"
				  + "Maria, 1, Ari, 4, Joe, 2, Lauren, 5, Juliet, 6, Fred, 3\n"
				  + "Maria, 1, Ari, 2, Joe, 6, Lauren, 4, Juliet, 5, Fred, 3\n"
				  + "Maria, 6, Ari, 1, Joe, 4, Lauren, 2, Juliet, 5, Fred, 3\n"
				  + "Maria, 3, Ari, 1, Joe, 4, Lauren, 2, Juliet, 6, Fred, 5\n"
				  + "Maria, 5, Ari, 1, Joe, 2, Lauren, 4, Juliet, 3, Fred, 6\n"
				  + "Maria, 3, Ari, 1, Joe, 2, Lauren, 6, Juliet, 4, Fred, 5\n"
				  + "Maria, 5, Ari, 1, Joe, 3, Lauren, 4, Juliet, 2, Fred, 6\n"
				  + "Maria, 6, Ari, 1, Joe, 3, Lauren, 4, Juliet, 5, Fred, 2\n"
				  + "Maria, 4, Ari, 2, Joe, 1, Lauren, 3, Juliet, 6, Fred, 5\n"
				  + "Maria, 3, Ari, 2, Joe, 1, Lauren, 5, Juliet, 4, Fred, 6\n"
				  + "Maria, 4, Ari, 3, Joe, 1, Lauren, 2, Juliet, 6, Fred, 5\n"
				  + "Maria, 4, Ari, 2, Joe, 1, Lauren, 3, Juliet, 6, Fred, 5 \n"
				  + "Maria, 4, Ari, 3, Joe, 2, Lauren, 1, Juliet, 5, Fred, 6 \n"
				  + "Maria, 3, Ari, 2, Joe, 4, Lauren, 1, Juliet, 6, Fred, 5\n"
				  + "Maria, 6, Ari, 3, Joe, 2, Lauren, 4, Juliet, 1, Fred, 5\n"
				  + "Maria, 5, Ari, 3, Joe, 2, Lauren, 4, Juliet, 1, Fred, 6\n"
				  + "Maria, 4, Ari, 2, Joe, 3, Lauren, 6, Juliet, 1, Fred, 5\n"
				  + "Maria, 3, Ari, 2, Joe, 6, Lauren, 4, Juliet, 1, Fred, 5\n"
				  + "Maria, 2, Ari, 6, Joe, 4, Lauren, 3, Juliet, 1, Fred, 5\n"
				  + "Maria, 3, Ari, 6, Joe, 2, Lauren, 4, Juliet, 5, Fred, 1\n"
				  + "Maria, 6, Ari, 3, Joe, 2, Lauren, 5, Juliet, 4, Fred, 1\n"
				  + "Maria, 4, Ari, 2, Joe, 3, Lauren, 6, Juliet, 5, Fred, 1\n"
				  + "Maria, 5, Ari, 2, Joe, 3, Lauren, 4, Juliet, 6, Fred, 1\n"
				  + "Maria, 3, Ari, 2, Joe, 5, Lauren, 4, Juliet, 6, Fred, 1";
		
		@SuppressWarnings("unused")
		var schedeLette = new MySchedeReader().leggiSchede(new StringReader(fakefile));
		//System.out.println(schedeLette);
	}
	
	@Test
	public void testKO_RigheDiLunghezzaDiversa() throws IOException, BadFileFormatException {
		String fakefile = 
				  "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2, Henry, 5\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 2, Joe, 3\n"
				+ "Maria, 2, Ari, 3, Joe, 1";
		assertThrows(BadFileFormatException.class, () -> new MySchedeReader().leggiSchede(new StringReader(fakefile)));
	}
	
	@Test
	public void testKO_RigheConNumeroDispariDiElementi() throws IOException, BadFileFormatException {
		String fakefile = 
				  "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 2, Joe, 3, Harry\n"
				+ "Maria, 2, Ari, 3, Joe, 1";
		assertThrows(BadFileFormatException.class, () -> new MySchedeReader().leggiSchede(new StringReader(fakefile)));
	}
	
	@Test
	public void testKO_RigheSenzaAlternanzaStringheNumeri_casoA() throws IOException, BadFileFormatException {
		String fakefile = 
				  "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 2, Joe, Karen\n"
				+ "Maria, 2, Ari, 3, Joe, 1";
		assertThrows(BadFileFormatException.class, () -> new MySchedeReader().leggiSchede(new StringReader(fakefile)));
	}
	
	@Test
	public void testKO_RigheSenzaAlternanzaStringheNumeri_casoB() throws IOException, BadFileFormatException {
		String fakefile = 
				  "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 2, 7,   3\n"
				+ "Maria, 2, Ari, 3, Joe, 1";
		assertThrows(BadFileFormatException.class, () -> new MySchedeReader().leggiSchede(new StringReader(fakefile)));
	}

	@Test
	public void testKO_RigheConNomiDiCandidatiDiversi() throws IOException, BadFileFormatException {
		String fakefile = 
				  "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 3, Jim, 2\n"
				+ "Maria, 1, Ari, 3, Joe, 2\n"
				+ "Maria, 1, Ari, 2, Joe, 3\n"
				+ "Maria, 2, Ari, 3, Joe, 1";
		assertThrows(BadFileFormatException.class, () -> new MySchedeReader().leggiSchede(new StringReader(fakefile)));
	}
	
	@Test
	public void testKO_RigheConNomiDiCandidatiNumerici() throws IOException, BadFileFormatException {
		String fakefile = 
				  "Maria, 1, 222, 3, Joe, 2\n"
				+ "Maria, 1, 222, 3, Joe, 2\n"
				+ "Maria, 1, 222, 3, Joe, 2\n"
				+ "Maria, 1, 222, 2, Joe, 3\n"
				+ "Maria, 2, 222, 3, Joe, 1";
		assertThrows(BadFileFormatException.class, () -> new MySchedeReader().leggiSchede(new StringReader(fakefile)));
	}

}
