package mediaesami.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import mediaesami.model.AttivitaFormativa;
import mediaesami.model.Carriera;
import mediaesami.model.Esame;
import mediaesami.model.Voto;


public class MyCarrieraReader implements CarrieraReader {

	/*
	 * 	27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT
		27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22
		28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24
		29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26
		26337	LINGUA INGLESE B-2					6,0
		27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE
		27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT
		28006	FONDAMENTI DI INFORMATICA T-2		12,0
		28011	RETI LOGICHE T						6,0
		...
	* */

	@Override
	public Carriera leggiCarriera(Reader rdr) throws IOException {
		if(rdr==null) {
			throw new IllegalArgumentException("Reader è null");
		}
		BufferedReader br = new BufferedReader(rdr);
		String line;
		Carriera result=new Carriera();
		while((line=br.readLine())!=null) {
			if(line.isBlank()) {
				continue;
			}
			String[] items=line.split("\\t+");
			if(items.length!=3 && items.length!=5) {
				throw new BadFileFormatException("sbagliato numero di elementi a riga: "+line);
			}
			long id;
			try {
				id=Long.parseLong(items[0].trim());
			}catch(NumberFormatException e) {
				throw new BadFileFormatException("Errore nel parsing dell'id a riga: "+line);
			}
			NumberFormat formattatoreCfu = NumberFormat.getInstance(Locale.ITALY);
			double cfu;
			if(items[2].contains(".")) {
				throw new BadFileFormatException("Errore, cfu decimale con punto illegale, ci si aspetta la virgola come separatore a riga: "+line);
			}
			try {
				cfu=formattatoreCfu.parse(items[2]).doubleValue();
			}catch(ParseException e) {
				throw new BadFileFormatException("Errore nel parsing dei cfu a riga: "+line);
			}
			if(items[1].isBlank() || items[1].isEmpty()) {
				throw new BadFileFormatException("Nome dell'esame mancante a riga: "+line);
			}
			if(items.length==5) {
				LocalDate data;
				Voto voto;
				final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy").withLocale(Locale.ITALY);
				try {
					data=LocalDate.parse(items[3], formatter);
					voto=Voto.of(items[4]);
				}catch(IllegalArgumentException e) {
					throw new BadFileFormatException("Voto illegale a riga: "+line);
				}catch(DateTimeParseException e) {
					throw new BadFileFormatException("Data illegale a riga: "+line);
				}
				AttivitaFormativa current = new AttivitaFormativa(id, items[1], cfu);
				Esame currentEsame = new Esame(current, data, voto);
				try {
					result.inserisci(currentEsame);
				}catch(IllegalArgumentException e) {
					throw new BadFileFormatException("Errore, PROVA FINALE prima di aver finito tutti gli esami");
				}
			}
		}
		return result;
	}
}