package bancaore.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import bancaore.model.Cedolino;
import bancaore.model.SettimanaLavorativa;


public class MyCedolinoReader implements CedolinoReader {

	/*  Dipendente:	Rossi Mario
		Mese di:	GENNAIO 2022
		Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H
		Saldo precedente:	12H07M
		
		03 Lunedì	08:30	14:30
		04 Martedì	08:30	17:30
		05 Mercoledì	08:30	14:30	Riposo Compensativo
		07 Venerdì	08:30	14:30	Riposo Compensativo
		10 Lunedì	07:30	13:42	
		10 Lunedì	13:42	13:53	Pausa Pranzo
		10 Lunedì	13:53	15:45	
		11 Martedì	07:30	13:28		
		...
	 **/

	@Override
	public Cedolino leggiCedolino(Reader rdr) throws IOException {
		if (rdr==null) throw new IllegalArgumentException("reader is null");
		BufferedReader reader = new BufferedReader(rdr);
		
		// Lettura e validazione intestazione
		String nomeDipendente  		  = analizzaIntestazioneRiga1(reader.readLine()); // fornito
		LocalDate dataCedolino 		  = analizzaIntestazioneRiga2(reader.readLine()); // *** DA FARE *** 
		SettimanaLavorativa settimana = analizzaIntestazioneRiga3(reader.readLine()); // fornito
		Duration saldoPrecedente 	  = analizzaIntestazioneRiga4(reader.readLine()); // fornito
		
		// creazione del cedolino inizialmente vuoto
		Cedolino cedolino = new Cedolino(nomeDipendente,dataCedolino,settimana,saldoPrecedente);

		// Lettura ed elaborazione delle righe con le singole voci
		
		String line;
		while((line=reader.readLine())!=null) {
			if(line.isBlank()) {
				continue; //salto le righe vuote
			}
			String[] items = line.split("\\t+");
			if(items.length!=3 && items.length!=4) {
				throw new BadFileFormatException("errore, numero errato di elementi nella riga: "+line);
			}
			//
			String[] primaParte = items[0].trim().split("\\s+");
			try {
				int giornoDelMese = Integer.parseInt(primaParte[0]);
				if(giornoDelMese<1 || giornoDelMese>31) {
					throw new BadFileFormatException("Giorno del mese illegale, fuori dal range 1-31 a riga: "+line);
				}
			}catch(NumberFormatException e) {
				throw new BadFileFormatException("Errore, giorno del mese illegale a riga: "+line);
			}
			DayOfWeek giornoDellaSettimanaLetto = DayOfWeek.valueOf(primaParte[1].trim());
		}

		/* il ciclo di lettura deve verificare che: 
			1) la riga contenga o tre o quattro elementi 
			2) il primo elemento sia costituito da un numero intero fra 1 e 31, seguito da uno o più spazi e dal 
			   nome (case-insensitive) del giorno della settimana corrispondente 
			   SUGGERIMENTO: a partire dalla data del cedolino, che contiene già mese e anno, modificare il 
			   giorno della settimana con quello dato, verificando poi, a parte, che il nome del giorno della nuova 
			   data così ottenuta sia quello atteso – sfruttando il metodo CedolinoReader.getDayOfWeekName 
			   OPPURE: combinare gli elementi relativi al giorno con quelli relativi a mese e anno (forniti 
			   nell’intestazione del cedolino) per ottenere una data in formato full, di cui sia facile fare il parse 
			3) il secondo e il terzo elemento siano orari correttamente formattati secondo la cultura italiana 
			   NB: provvede VoceCedolino a verificare che l’orario di entrata sia antecedente all’orario di uscita 
			4) l’eventuale quarto elemento, se presente, contenga una delle stringhe (case-insensitive) 
			   "PAUSA PRANZO", "RIPOSO COMPENSATIVO" o "RIPOSO COMPENSATIVO A ORE", da trasformare nella 
			   corrispondente Causale.
		*/
		return cedolino;
	}

	private Duration analizzaIntestazioneRiga4(String line) throws BadFileFormatException {
		// Elaborazione quarta riga di intestazione
		// Saldo precedente:	12H07M
		String[] items = line.split(":");
		// validazione input quarta riga di intestazione
		if (items.length!=2)
			throw new BadFileFormatException("Numero elementi errato nella quarta riga di intestazione: " + line);
		if (!items[0].toLowerCase().equals("saldo precedente"))
			throw new BadFileFormatException("La riga di intestazione non inizia con 'Saldo precedente:'\nRiga: " + items[0]);
		String strSaldoPrec = items[1].trim().toUpperCase();
		return Duration.parse("PT"+strSaldoPrec);
	}

	private SettimanaLavorativa analizzaIntestazioneRiga3(String line) throws BadFileFormatException {
		// Elaborazione terza riga di intestazione
		// Ore previste: 9H/6H/9H/6H/9H/0H/0H
		String[] items = line.split(":");
		if (items.length!=2)
			throw new BadFileFormatException("Numero elementi errato nella terza riga di intestazione: " + line);
		if (!items[0].toLowerCase().equals("ore previste"))
			throw new BadFileFormatException("La riga di intestazione non inizia con 'Ore previste:'\nRiga: " + items[0]);
		String[] strSettLav = items[1].trim().toUpperCase().split("/+");
		
		try {
			return new SettimanaLavorativa(strSettLav);
		}
		catch(IllegalArgumentException | DateTimeParseException e) {
			throw new BadFileFormatException("La riga di intestazione non contiene una settimana lavorativa correttamente formattata\nRiga: " + items[1].trim());
		}
	}

	private LocalDate analizzaIntestazioneRiga2(String line) throws BadFileFormatException {
		line=line.toLowerCase();
		int indice = line.indexOf(":");
		line=line.substring(indice+2);
		line=line.trim();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ITALIAN);
		LocalDate data = LocalDate.parse("1 " + line, formatter);
		return data;
	}

	private String analizzaIntestazioneRiga1(String line) throws BadFileFormatException {
		// Elaborazione prima riga di intestazione
		// Dipendente:	Rossi Mario
		String[] items = line.split(":");
		if (items.length!=2)
			throw new BadFileFormatException("Numero elementi errato nella prima riga di intestazione: " + line);
		if (!items[0].toLowerCase().equals("dipendente"))
			throw new BadFileFormatException("La riga di intestazione non inizia con 'Dipendente:'\nRiga: " + line);
		return items[1].trim();
	}
	
}