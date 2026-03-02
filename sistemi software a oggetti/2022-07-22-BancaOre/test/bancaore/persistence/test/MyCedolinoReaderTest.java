package bancaore.persistence.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.time.Duration;
import java.time.Month;

import org.junit.jupiter.api.Test;

import bancaore.model.SettimanaLavorativa;
import bancaore.persistence.BadFileFormatException;
import bancaore.persistence.CedolinoReader;
import bancaore.persistence.MyCedolinoReader;

class MyCedolinoReaderTest {

	@Test
	void testOK() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n"
				+ "05 Mercoledì	08:30	14:30	Riposo Compensativo\r\n"
				+ "07 Venerdì	08:30	14:30	Riposo Compensativo\r\n"
				+ "10 Lunedì	07:30	13:42	\r\n"
				+ "10 Lunedì	13:42	13:53	Pausa Pranzo\r\n"
				+ "10 Lunedì	13:53	15:45	\r\n"
				+ "11 Martedì	07:30	13:28	\r\n"
				+ "11 Martedì	13:28	13:38	Pausa Pranzo\r\n"
				+ "11 Martedì	13:38	14:46	\r\n"
				+ "11 Martedì	14:46	16:40	Riposo Compensativo a Ore\r\n"
				+ "12 Mercoledì	07:30	13:49	\r\n"
				+ "12 Mercoledì	13:49	13:59	Pausa Pranzo\r\n"
				+ "12 Mercoledì	13:59	14:49	\r\n"
				+ "13 Giovedì	07:30	13:39	\r\n"
				+ "13 Giovedì	13:39	13:49	Pausa Pranzo\r\n"
				+ "13 Giovedì	13:49	16:44	\r\n"
				+ "14 Venerdì	07:30	13:35	\r\n"
				+ "17 Lunedì	07:30	14:00	\r\n"
				+ "17 Lunedì	14:00	14:10	Pausa Pranzo\r\n"
				+ "17 Lunedì	14:10	15:04	\r\n"
				+ "18 Martedì	07:31	13:42	\r\n"
				+ "18 Martedì	13:42	13:53	Pausa Pranzo\r\n"
				+ "18 Martedì	13:53	15:03	\r\n"
				+ "18 Martedì	15:03	16:42	Riposo Compensativo a Ore\r\n"
				+ "19 Mercoledì	07:30	13:44	\r\n"
				+ "19 Mercoledì	13:44	13:54	Pausa Pranzo\r\n"
				+ "19 Mercoledì	13:54	15:01	\r\n"
				+ "20 Giovedì	07:30	13:55	\r\n"
				+ "20 Giovedì	13:55	14:05	Pausa Pranzo\r\n"
				+ "20 Giovedì	14:05	14:51	\r\n"
				+ "20 Giovedì	14:51	16:40	Riposo Compensativo a Ore\r\n"
				+ "21 Venerdì	07:30	13:36	\r\n"
				+ "24 Lunedì	07:30	13:27	\r\n"
				+ "24 Lunedì	13:27	13:30	Riposo Compensativo a Ore\r\n"
				+ "25 Martedì	07:30	16:30\r\n"
				+ "26 Mercoledì	07:30	13:23	\r\n"
				+ "26 Mercoledì	13:23	13:30	Riposo Compensativo a Ore\r\n"
				+ "27 Giovedì	07:30	16:30\r\n"
				+ "28 Venerdì	07:30	13:13	\r\n"
				+ "28 Venerdì	13:13	13:30	Riposo Compensativo a Ore\r\n"
				+ "31 Lunedì	07:30	13:30";
		CedolinoReader cRdr = new MyCedolinoReader();
		var c = cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile)));
		assertEquals(Month.JANUARY, c.getData().getMonth());
		assertEquals(2022, c.getData().getYear());
		assertEquals("Mario Rossi", c.getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), c.getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, c.getSettimanaLavorativa());
		assertEquals(42, c.getVoci().size());
	}
	
	@Test
	void testOK_Minuscoli() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "mese di:	GEnnAIO 2022\r\n"
				+ "ore Previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "saldo prEcedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 lunedì	08:30	14:30\r\n"
				+ "04 marTedì	08:30	17:30\r\n"
				+ "05 meRcoledì	08:30	14:30	riposo compensativo\r\n"
				+ "07 VENerdì	08:30	14:30	RiposO COmpensativo\r\n"
				+ "10 Lunedì	07:30	13:42	\r\n"
				+ "10 LuNedì	13:42	13:53	Pausa PrAnzo\r\n"
				+ "10 LUnedì	13:53	15:45	\r\n"
				+ "11 MarteDì	07:30	13:28	\r\n"
				+ "11 Martedì	13:28	13:38	pausa PranZo\r\n"
				+ "11 Martedì	13:38	14:46	\r\n"
				+ "11 Martedì	14:46	16:40	Riposo Compensativo A Ore\r\n"
				+ "12 Mercoledì	07:30	13:49	\r\n"
				+ "12 Mercoledì	13:49	13:59	Pausa Pranzo\r\n"
				+ "12 Mercoledì	13:59	14:49	\r\n"
				+ "13 Giovedì	07:30	13:39	\r\n"
				+ "13 Giovedì	13:39	13:49	Pausa Pranzo\r\n"
				+ "13 Giovedì	13:49	16:44	\r\n"
				+ "14 Venerdì	07:30	13:35	\r\n"
				+ "17 Lunedì	07:30	14:00	\r\n"
				+ "17 Lunedì	14:00	14:10	Pausa Pranzo\r\n"
				+ "17 Lunedì	14:10	15:04	\r\n"
				+ "18 Martedì	07:31	13:42	\r\n"
				+ "18 Martedì	13:42	13:53	Pausa Pranzo\r\n"
				+ "18 Martedì	13:53	15:03	\r\n"
				+ "18 Martedì	15:03	16:42	Riposo Compensativo a ore\r\n"
				+ "19 Mercoledì	07:30	13:44	\r\n"
				+ "19 Mercoledì	13:44	13:54	Pausa Pranzo\r\n"
				+ "19 Mercoledì	13:54	15:01	\r\n"
				+ "20 Giovedì	07:30	13:55	\r\n"
				+ "20 Giovedì	13:55	14:05	Pausa Pranzo\r\n"
				+ "20 Giovedì	14:05	14:51	\r\n"
				+ "20 Giovedì	14:51	16:40	Riposo Compensativo a Ore\r\n"
				+ "21 Venerdì	07:30	13:36	\r\n"
				+ "24 Lunedì	07:30	13:27	\r\n"
				+ "24 Lunedì	13:27	13:30	Riposo Compensativo a Ore\r\n"
				+ "25 Martedì	07:30	16:30\r\n"
				+ "26 Mercoledì	07:30	13:23	\r\n"
				+ "26 Mercoledì	13:23	13:30	Riposo Compensativo a Ore\r\n"
				+ "27 Giovedì	07:30	16:30\r\n"
				+ "28 Venerdì	07:30	13:13	\r\n"
				+ "28 Venerdì	13:13	13:30	Riposo Compensativo a Ore\r\n"
				+ "31 Lunedì	07:30	13:30";
		CedolinoReader cRdr = new MyCedolinoReader();
		var c = cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile)));
		assertEquals(Month.JANUARY, c.getData().getMonth());
		assertEquals(2022, c.getData().getYear());
		assertEquals("Mario Rossi", c.getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), c.getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, c.getSettimanaLavorativa());
		assertEquals(42, c.getVoci().size());
	}
		
	@Test
	void testKO_WrongNumberOfItemsAtRow1_MissingSeparator() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_WrongNumberOfItemsAtRow1_ExtraItem() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario : Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongKeywordAtRow1() throws FileNotFoundException, IOException {
		String fakeFile = "Lavoratore:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongNumberOfItemsAtRow2_MissingSeparator() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_WrongNumberOfItemsAtRow2_ExtraItem() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese: di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongKeywordAtRow2_WrongFirstWord() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mmese di:		GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongKeywordAtRow2_WrongSecondWord() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese de:		GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongKeywordAtRow2_WrongMix() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese  di:			GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_WrongMonthNameAtRow2() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	BRUMAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongYearAtRow2() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO -2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_WrongNumberOfItemsAtRow3_MissingSeparator() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_WrongNumberOfItemsAtRow3_ExtraItem() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore : previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongKeywordAtRow3_WrongFirstWord() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "DURATE previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongKeywordAtRow3_WrongSecondWord() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "ore dovute: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongKeywordAtRow3_WrongMix() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "ore\tpreviste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongSeparatorDashBetweenDaysAtRow3() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H-9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongSeparatorBackSlashBetweenDaysAtRow3() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H\\6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO__WrongNumberOfItemsAtRow3_ExtraItemInWeek() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H/7H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO__WrongNumberOfItemsAtRow3_ExtraSpaceInWeek() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/ 5H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongNumberOfItemsAtRow4_MissingSeparator() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente		12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_WrongNumberOfItemsAtRow4_ExtraItem() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	:12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongKeywordAtRow4_WrongFirstWord() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Soldo precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_WrongKeywordAtRow4_WrongSecondWord() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo vecchio:		12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongKeywordAtRow4_WrongMix() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:	GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo  precedente:	12H07M\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_WrongLine_WrongNumberOfItems_WrongSeparatorBetweenTimes() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30 14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongNumberOfItems_WrongSeparatorBeforeTime() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì  08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongNumberOfItems_WrongSeparatorBetweenDayNumberAndDayName() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03	Lunedì 	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongNumberOfItems_ExtraTime() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	11:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongNumberOfItems_MissingTime() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	\r\n"
				+ "04 Martedì	08:30	17:30\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_BadlyFormattedTime() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14.30\r\n"
				+ "04 Martedì	08:30	17:30\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_BadlyFormattedTime2() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:030\r\n"
				+ "04 Martedì	08:30	17:30\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}		
		
	@Test
	void testKO_WrongLine_WrongCausale_RiposoCompensativo() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n"
				+ "05 Mercoledì	08:30	14:30	Riposso Compensativo\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongCausale_RiposoCompensativo2() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n"
				+ "05 Mercoledì	08:30	14:30	Riposo  Compensativo\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongCausale_PausaPranzo() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n"
				+ "05 Mercoledì	08:30	14:30	Riposo Compensativo\r\n"
				+ "07 Venerdì	08:30	14:30	Riposo Compensativo\r\n"
				+ "10 Lunedì	07:30	13:42	\r\n"
				+ "10 Lunedì	13:42	13:53	Pausa PPranzo\r\n"
				+ "10 Lunedì	13:53	15:45	\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongCausale_PausaPranzo2() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n"
				+ "05 Mercoledì	08:30	14:30	Riposo Compensativo\r\n"
				+ "07 Venerdì	08:30	14:30	Riposo Compensativo\r\n"
				+ "10 Lunedì	07:30	13:42	\r\n"
				+ "10 Lunedì	13:42	13:53	Pausa  pranzo\r\n"
				+ "10 Lunedì	13:53	15:45	\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongCausale_RiposoCompensativoAOre() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "11 Martedì	07:30	13:28	\r\n"
				+ "11 Martedì	13:28	13:38	Pausa Pranzo\r\n"
				+ "11 Martedì	13:38	14:46	\r\n"
				+ "11 Martedì	14:46	16:40	Riposo Compensativo aD Ore\r\n"
				+ "12 Mercoledì	07:30	13:49	\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongCausale_RiposoCompensativoAOre2() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "11 Martedì	07:30	13:28	\r\n"
				+ "11 Martedì	13:28	13:38	Pausa Pranzo\r\n"
				+ "11 Martedì	13:38	14:46	\r\n"
				+ "11 Martedì	14:46	16:40	Riposo Compensativo a  Ore\r\n"
				+ "12 Mercoledì	07:30	13:49	\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongCausale_RiposoCompensativoAOre3() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "11 Martedì	07:30	13:28	\r\n"
				+ "11 Martedì	13:28	13:38	Pausa Pranzo\r\n"
				+ "11 Martedì	13:38	14:46	\r\n"
				+ "11 Martedì	14:46	16:40	Riposo Compensativo  a Ore\r\n"
				+ "12 Mercoledì	07:30	13:49	\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongCausale_RiposoCompensativoAOre4() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "11 Martedì	07:30	13:28	\r\n"
				+ "11 Martedì	13:28	13:38	Pausa Pranzo\r\n"
				+ "11 Martedì	13:38	14:46	\r\n"
				+ "11 Martedì	14:46	16:40	Riposo  Compensativo a Ore\r\n"
				+ "12 Mercoledì	07:30	13:49	\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongCausale1() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n"
				+ "05 Mercoledì	08:30	14:30	Voglia di stare a casa\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongCausale2() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n"
				+ "05 Mercoledì	08:30	14:30	Riposo\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongDayNumber() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n"
				+ "35 Mercoledì	08:30	14:30	Riposo Compensativo\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_WrongLine_WrongDayName() throws FileNotFoundException, IOException {
		String fakeFile = "Dipendente:	Mario Rossi\r\n"
				+ "Mese di:				GENNAIO 2022\r\n"
				+ "Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H\r\n"
				+ "Saldo precedente:	12H07M\r\n"
				+ "\r\n"
				+ "03 Lunedì	08:30	14:30\r\n"
				+ "04 Martedì	08:30	17:30\r\n"
				+ "05 Soledì	08:30	14:30	Riposo Compensativo\r\n";
		CedolinoReader cRdr = new MyCedolinoReader();
		assertThrows(BadFileFormatException.class, () -> cRdr.leggiCedolino(new BufferedReader(new StringReader(fakeFile))));
	}


}
