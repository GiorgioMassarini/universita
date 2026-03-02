package gringottbank.tests.persistence;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;
import gringottbank.model.Ceiling;
import gringottbank.model.Coin;
import gringottbank.model.CoinAmount;
import gringottbank.persistence.BadFileFormatException;
import gringottbank.persistence.MyCeilingsReader;


public class MyCeilingsReaderTest {
	
	@Test
	void testOK1() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni, 20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28 zellini\n"
			    + "Ron			12 galeoni, 10 falci, 6 zellini\n"
				);	
		List<Ceiling> ceilings = new MyCeilingsReader().readCeilings(fakeReader);		
		assertEquals(6,  ceilings.size());
		//System.out.println(ceilings);
		List<Ceiling> expected = List.of(
					new Ceiling("Harry",	new CoinAmount().plus(Coin.GALLEON, 20)), 
					new Ceiling("Hermione",	new CoinAmount().plus(Coin.GALLEON, 19).plus(Coin.KNUT, 20)),
					new Ceiling("Enrico", 	new CoinAmount().plus(Coin.GALLEON, 200)),
					new Ceiling("Ambra", 	new CoinAmount().plus(Coin.GALLEON, 100).plus(Coin.KNUT, 20)),
					new Ceiling("Roberta", 	new CoinAmount().plus(Coin.GALLEON, 100).plus(Coin.KNUT, 28)),
					new Ceiling("Ron",		new CoinAmount().plus(Coin.GALLEON, 12).plus(Coin.SICKLE, 10).plus(Coin.KNUT, 6))
				);
		assertEquals(expected.size(), ceilings.size());
		for(int i=0; i<expected.size();i++) {
			assertEquals(expected.get(i), ceilings.get(i));
		}
	}
	
	@Test
	void testOK2_CheckSpaziETab() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry			20 galeoni\n"
			    + "Hermione		19 galeoni,     20 zellini\n"
			    + "Enrico	200 galeoni\n"
			    + "Ambra		100   galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28    zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		List<Ceiling> ceilings = new MyCeilingsReader().readCeilings(fakeReader);		
		assertEquals(6,  ceilings.size());
		//System.out.println(ceilings);
	}
	
	@Test
	void testOK3_NoSpacesAfterCommas() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni,20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni,20 zellini\n"
			    + "Roberta		100 galeoni,28 zellini\n"
			    + "Ron			12 galeoni,10 falci, 6 zellini\n"
			);	
		List<Ceiling> ceilings = new MyCeilingsReader().readCeilings(fakeReader);		
		assertEquals(6,  ceilings.size());
		//System.out.println(ceilings);
	}
	
	@Test
	void testOK4_UpperLowerCaseInCoinNames() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 Galeoni\n"
			    + "Hermione		19 gaLEoni, 20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni, 20 Zellini\n"
			    + "Roberta		100 galeoni, 28 zellINI\n"
			    + "Ron			12 galeoni, 10 fALCi, 6 zellini\n"
			);	
		List<Ceiling> ceilings = new MyCeilingsReader().readCeilings(fakeReader);		
		assertEquals(6,  ceilings.size());
		//System.out.println(ceilings);
	}
	
	//---------------test KO------------------
	
	@Test
	void testKO_MissingOneItemInExpectedList() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni, 20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28 zellini\n"
			    + "Ron			12 galeoni, 10 falci, 6 zellini\n"
				);	
		List<Ceiling> ceilings = new MyCeilingsReader().readCeilings(fakeReader);		
		assertEquals(6,  ceilings.size());
		//System.out.println(ceilings);
		List<Ceiling> expected = List.of(
					new Ceiling("Harry",	new CoinAmount().plus(Coin.GALLEON, 20)), 
					//new Ceiling("Hermione",	new CoinAmount().plus(Coin.GALLEON, 19).plus(Coin.KNUT, 20)),
					new Ceiling("Enrico", 	new CoinAmount().plus(Coin.GALLEON, 200)),
					new Ceiling("Ambra", 	new CoinAmount().plus(Coin.GALLEON, 100).plus(Coin.KNUT, 20)),
					new Ceiling("Roberta", 	new CoinAmount().plus(Coin.GALLEON, 100).plus(Coin.KNUT, 28)),
					new Ceiling("Ron",		new CoinAmount().plus(Coin.GALLEON, 12).plus(Coin.SICKLE, 10).plus(Coin.KNUT, 6))
				);
		assertNotEquals(expected.size(), ceilings.size());
	}
	
	@Test
	void testKO1_MissingNameInFirstLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "				20 galeoni\n"
			    + "Hermione		19 galeoni,     20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100   galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28    zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO2_MissingNameInOtherLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni,     20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "		100   galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28    zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO3_MissingTabInFirstLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry 20 galeoni\n"
			    + "Hermione		19 galeoni,     20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100   galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28    zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO4_MissingTabInOtherLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni,     20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra     100   galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28    zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO5_SpaceInsteadOfTabInFirstLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20	galeoni\n"
			    + "Hermione		19 galeoni,     20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100   galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28    zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO6_SpaceInsteadOfTabInOtherLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19	galeoni,	20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100   galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28    zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO7_MissingSpaceInFirstLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20galeoni\n"
			    + "Hermione		19 galeoni, 20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28  zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}

	@Test
	void testKO8_MissingSpaceInOtherLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni, 20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni, 20zellini\n"
			    + "Roberta		100 galeoni, 28  zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO9_WrongNumberInSomeLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		XX galeoni\n"
			    + "Hermione		19 galeoni, 20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28  zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO10_WrongNumberInSomeOtherLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni, AA zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28  zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO11_NegativeNumberInSomeLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni, 20 zellini\n"
			    + "Enrico		-200 galeoni\n"
			    + "Ambra		100 galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28  zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO12_NegativeNumberInSomeOtherLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni, 20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni, -20 zellini\n"
			    + "Roberta		100 galeoni, 28  zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO13_WrongCoinNameInFirstLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 libbre\n"
			    + "Hermione		19 galeoni, 20 zellini\n"
			    + "Enrico		200 galeoni\n"
			    + "Ambra		100 galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28  zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
	
	@Test
	void testKO14_WrongCoinNameInOtherLine() throws IOException, BadFileFormatException {
		StringReader fakeReader = new StringReader(
			      "Harry		20 galeoni\n"
			    + "Hermione		19 galeoni, 20 zellini\n"
			    + "Enrico		200 euro\n"
			    + "Ambra		100 galeoni, 20 zellini\n"
			    + "Roberta		100 galeoni, 28  zellini\n"
			    + "Ron			12 galeoni, 10   falci,   6 zellini\n"
			);	
		assertThrows(BadFileFormatException.class, () -> new MyCeilingsReader().readCeilings(fakeReader));
	}
}
