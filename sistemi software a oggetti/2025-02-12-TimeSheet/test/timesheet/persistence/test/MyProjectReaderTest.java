package timesheet.persistence.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;
import timesheet.persistence.BadFileFormatException;
import timesheet.persistence.MyProjectReader;


public class MyProjectReaderTest {

	@Test
	void testOK() throws IOException, BadFileFormatException {
		String fakefile = 
				  "Lezioni di Analisi 2			ore previste: 60\r\n"
				+ "Seminari di Analisi			ore previste: 30\r\n"
				+ "Progetto Curve ellittiche	ore previste: 900\r\n"
				+ "Progetto Solidi				ore previste: 200\r\n";
		var rates = new MyProjectReader().projectHours(new StringReader(fakefile));
		assertEquals(4, rates.size());
	}
	
	@Test
	void testKO_NumItemsNon3_MissingTab() throws IOException, BadFileFormatException {
		String fakefile = 
				    "Lezioni di Analisi 2  ore previste: 60\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon3_MissingColon() throws IOException, BadFileFormatException {
		String fakefile = 
			    "Lezioni di Analisi 2		ore previste   60\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon3_TwoColons() throws IOException, BadFileFormatException {
		String fakefile = 
			    "Lezioni di Analisi 2		ore: previste: 60\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon3_TwoColonsBis() throws IOException, BadFileFormatException {
		String fakefile = 
			    "Lezioni di Analisi 2		ore previste:: 60\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon3_MissingOrePreviste() throws IOException, BadFileFormatException {
		String fakefile = 
			    "Lezioni di Analisi 2		: 60\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon3_ExtraTabInName() throws IOException, BadFileFormatException {
		String fakefile = 
				    "Lezioni	di Analisi 2		ore previste: 60\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongOrePreviste() throws IOException, BadFileFormatException {
		String fakefile = 
			    "Lezioni di Analisi 2		orepreviste: 60\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongOrePrevisteBis() throws IOException, BadFileFormatException {
		String fakefile = 
			    "Lezioni di Analisi 2		ore Previste: 60\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongNumZero() throws IOException, BadFileFormatException {
		String fakefile = 
				"Lezioni di Analisi 2		ore previste: 0\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongNumNeg() throws IOException, BadFileFormatException {
		String fakefile = 
				"Lezioni di Analisi 2		ore previste: -10\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WronNum() throws IOException, BadFileFormatException {
		String fakefile = 
				"Lezioni di Analisi 2		ore previste: ahahah\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyProjectReader().projectHours(new StringReader(fakefile)));
	}
	
}
