package frutta.persistence.test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import frutta.persistence.BadFileFormatException;
import frutta.persistence.MyFruttaReader;


public class MyFruttaReaderTest {

	@Test
	void testOK() throws IOException, BadFileFormatException {
		String fakefile = 
				      "Prodotti;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				      + "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n"
				      + "Ciliegie - Bigarreau; NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 8,00;€ 5,50;€ 5,50;€ 4,63\r\n"
				      + "Ciliegie - Celeste;   NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 7,50\r\n"
				      + "Ciliegie - Dure;      NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 4,88;€ 4,63;€ 4,25\r\n"
				      + "Ciliegie - Durone;    NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 3,85\r\n"
				      + "Ciliegie - Ferrovia;  NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 6,00\r\n"
				      + "Ciliegie - Giorgia;   NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 5,50;€ 5,75\r\n"
				      + "Ciliegie - Tenere;    NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 9,50;€ 3,33;€ 3,83;€ 3,40\r\n"
				      + "Fragole - Candonga;€ 5,00;€ 4,57;€ 4,10;€ 3,87;€ 3,59;€ 3,53;€ 3,15;€ 3,09;€ 3,09;€ 3,14;€ 3,09;€ 2,91;€ 2,84;€ 2,74;€ 2,61\r\n"
				      + "Fragole - nostrane;€ 3,82;€ 3,15;€ 2,85;€ 2,75;€ 2,65;€ 2,54;€ 2,29;€ 2,22;€ 2,22;€ 2,14;€ 2,13;€ 2,64;€ 2,59;€ 2,35;€ 2,21\r\n"
				      + "Kiwi - Gold (Polpa gialla);€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,45;€ 2,45;€ 2,45;€ 2,45\r\n"
				      + "Kiwi - Hayward;€ 1,83;€ 1,83;€ 1,82;€ 1,82;€ 1,82;€ 1,82;€ 1,82;€ 1,83;€ 1,83;€ 1,83;€ 1,83;€ 1,87;€ 1,87;€ 1,89;€ 1,86\r\n"
				      + "Mele - Annurca;€ 1,38;€ 1,38;€ 1,38;€ 1,38;€ 1,38;€ 1,38;€ 1,38;€ 1,58;€ 1,58;€ 1,58;€ 1,58;€ 1,58;€ 1,58;€ 1,58;€ 1,58\r\n"
				      + "Mele - Cripps Pink;€ 1,35;€ 1,35;€ 1,35;€ 1,36;€ 1,36;€ 1,36;€ 1,36;€ 1,36;€ 1,39;€ 1,39;€ 1,39;€ 1,39;€ 1,50;€ 1,50;€ 1,50\r\n"
				      + "Mele - Fuji;€ 0,98;€ 0,98;€ 0,98;€ 0,98;€ 0,98;€ 0,98;€ 0,98;€ 0,98;€ 0,96;€ 0,96;€ 0,96;€ 0,97;€ 0,97;€ 0,98;€ 0,98\r\n"
				      + "Mele - Golden delicious;€ 0,80;€ 0,80;€ 0,80;€ 0,81;€ 0,81;€ 0,81;€ 0,81;€ 0,81;€ 0,81;€ 0,82;€ 0,82;€ 0,82;€ 0,83;€ 0,83;€ 0,83\r\n"
				      + "Mele - Granny smith;€ 0,83;€ 0,83;€ 0,83;€ 0,83;€ 0,83;€ 0,83;€ 0,83;€ 0,84;€ 0,84;€ 0,84;€ 0,84;€ 0,84;€ 0,84;€ 0,84;€ 0,84\r\n"
				      + "Mele - Morgenduft(Imperatore);€ 0,69;€ 0,69;€ 0,69;€ 0,69;€ 0,69;€ 0,69;€ 0,69;€ 0,75;€ 0,77;€ 0,77;€ 0,77;€ 0,77;€ 0,83;€ 0,83;€ 0,83\r\n"
				      + "Mele - Renetta canada;€ 1,31;€ 1,31;€ 1,31;€ 1,31;€ 1,31;€ 1,31;€ 1,31;€ 1,31;€ 1,31;€ 1,31;€ 1,31;€ 1,32;€ 1,32;€ 1,32;€ 1,32\r\n"
				      + "Mele - Stark delicious;€ 0,73;€ 0,73;€ 0,73;€ 0,73;€ 0,73;€ 0,73;€ 0,73;€ 0,73;€ 0,73;€ 0,73;€ 0,73;€ 0,74;€ 0,74;€ 0,74;€ 0,74\r\n"
				      + "Nettarine - Polpa bianca;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 1,80\r\n"
				      + "Nettarine - Polpa gialla;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,10;€ 1,95;€ 1,78\r\n"
				      + "Pere - Conference;€ 1,16;€ 1,16;€ 1,16;€ 1,16;€ 1,16;€ 1,16;€ 1,16;€ 1,16;€ 1,16;€ 1,16;€ 1,09;€ 1,09;€ 1,12;€ 1,02;€ 1,02\r\n"
				      + "Pesche - Polpa bianca;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 1,70\r\n"
				      + "Pesche - Polpa gialla;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 1,90;€ 1,75;€ 1,63\r\n"
				      + "Albicocche - Mogador;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,40;€ 2,20;NaN\r\n"
				      + "Pere - Decana del comizio;€ 1,48;€ 1,48;€ 1,48;€ 1,48;€ 1,35;€ 1,35;€ 1,35;€ 1,35;€ 1,35;€ 1,35;€ 1,40;€ 1,18;NaN;NaN;NaN\r\n"
				      + "Pere - Kaiser;€ 1,51;€ 1,51;€ 1,51;€ 1,51;€ 1,52;€ 1,52;€ 1,52;€ 1,52;€ 1,52;€ 1,52;€ 1,52;NaN;NaN;NaN;NaN\r\n"
				      + "Mele - Gala Gruppo;€ 0,83;€ 0,83;€ 0,83;€ 0,83;€ 0,83;€ 0,83;€ 0,83;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN\r\n"
				      + "Pere - Abate fetel;€ 1,85;€ 1,81;€ 1,81;€ 1,81;€ 1,81;€ 1,81;€ 1,79;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN\r\n"
				      + "";
		var mappaFrutti = new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile));
		
		assertEquals(new TreeSet<>(Set.of(
				"Albicocche", "Kiwi", "Ciliegie", "Mele", "Pesche", "Fragole", "Nettarine", "Pere"
				)), mappaFrutti.tipiFrutta());
		
		assertEquals(new TreeSet<>(Set.of("nostrane", "Mogador")), mappaFrutti.sottotipi("Albicocche"));
		assertEquals(new TreeSet<>(Set.of("Gold (Polpa gialla)", "Hayward")), mappaFrutti.sottotipi("Kiwi"));
		assertEquals(new TreeSet<>(Set.of("Bigarreau", "Dure", "Celeste", "Ferrovia", "Giorgia", "Durone", "Tenere")), mappaFrutti.sottotipi("Ciliegie"));
		assertEquals(new TreeSet<>(Set.of("Cripps Pink", "Golden delicious", "Annurca", 
										  "Granny smith", "Fuji", "Renetta canada", "Stark delicious", 
										  "Morgenduft(Imperatore)", "Gala Gruppo")), mappaFrutti.sottotipi("Mele"));
		assertEquals(new TreeSet<>(Set.of("Polpa gialla", "Polpa bianca")), mappaFrutti.sottotipi("Pesche"));
		assertEquals(new TreeSet<>(Set.of("nostrane", "Candonga")), mappaFrutti.sottotipi("Fragole"));
		assertEquals(new TreeSet<>(Set.of("Polpa gialla", "Polpa bianca")), mappaFrutti.sottotipi("Nettarine"));
		assertEquals(new TreeSet<>(Set.of("Decana del comizio", "Abate fetel", "Conference", "Kaiser")), mappaFrutti.sottotipi("Pere"));
	}
	
	@Test
	void testKO_HeaderLineBlank() throws IOException, BadFileFormatException {
		String fakefile = 
				   " \r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_HeaderLineNotStartingWithRequiredKeyword() throws IOException, BadFileFormatException {
		String fakefile = 
				  "COSE;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_HeaderLineNotStartingWithRequiredKeyword_Missing() throws IOException, BadFileFormatException {
		String fakefile = 
				  ";10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_HeaderLineWithWrongDate_Day() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;00/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_HeaderLineWithWrongDate_Month() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/22/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_HeaderLineWithWrongDateFormat() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/0225;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_HeaderLineWithNoDates() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_HeaderLine_MissingSemicolon() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25 24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumberOfItemsNotCorrespondingToNumberOfDates_MissingDate() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumberOfItemsNotCorrespondingToNumberOfDates_MissingItem() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumberOfItemsNotCorrespondingToNumberOfDates_EmptyDate() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumberOfItemsNotCorrespondingToNumberOfDates_EmptyNumber() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumberOfItemsNotCorrespondingToNumberOfDates_WrongNumberNaN() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NNN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumberOfItemsNotCorrespondingToNumberOfDates_WrongPrice_MissingEuro() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumberOfItemsNotCorrespondingToNumberOfDates_WrongPrice_MissingSpaceBetweenEuroAndNumber() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumberOfItemsNotCorrespondingToNumberOfDates_WrongPrice_EuroPostponed() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;2,65€;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumberOfItemsNotCorrespondingToNumberOfDates_WrongPrice_EuroPostponedWithSpace() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;2,65 €;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongFruttaSubtypeFormat_WrongSymbolInsteadOfDash() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche; nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongFruttaSubtypeFormat_MissingDash() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongFruttaSubtypeFormat_TwoDashes() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche -- nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_MissingSemiColonBetweenPrices() throws IOException, BadFileFormatException {
		String fakefile = 
				  "PRODOTTI;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25\r\n"
				+ "Albicocche - nostrane;NaN;NaN;NaN;NaN NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyFruttaReader().leggiRilevazioniPrezzi(new StringReader(fakefile)));
	}
	
}
