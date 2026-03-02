package tram.persistence.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

import tram.persistence.BadFileFormatException;
import tram.persistence.MyLinesReader;


public class MyLinesReaderTest {

	@Test
	void testOK() throws IOException, BadFileFormatException {
		String fakefile = 
				  "Linea Rossa, frequenza 5 minuti, 06:30-21:30\r\n"
				+ "Piazza Celestini (0), via Amendola (3), via Morandi (7), largo Augusto (10), corso Roma (12), piazza S.Silvestro (16), piazzetta Galvani (21), porta Fiorentina (25)\r\n"
				+ "\r\n"
				+ "Linea Verde, frequenza 8 minuti, 06:00-22:00\r\n"
				+ "Porta Romana (0), via Garibaldi (4), via Morandi (7), largo Augusto (10), corso Roma (12), piazza Duomo (17), teatro Verdi (22), Stazione FS (24)\r\n"
				+ "\r\n"
				+ "Linea Gialla, frequenza 10 minuti, 06:00-23:20\r\n"
				+ "Autostazione (0), via dei Frati (2), via delle Querce (5), largo Augusto (8), corso Roma (10), piazza S.Silvestro (14), Università (18), largo Nuvolari (20)";	
		
		var linee = new MyLinesReader().leggiLinee(new StringReader(fakefile));
		assertEquals(3, linee.size());
	}
	
}
