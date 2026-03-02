package frutta.persistence;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import frutta.model.Formatters;
import frutta.model.MappaFrutti;
import frutta.model.Rilevazione;
import java.io.BufferedReader;


public class MyFruttaReader implements FruttaReader {
	
	/*
	  	Prodotti;10/02/25;17/02/25;24/02/25;03/03/25;10/03/25;17/03/25;24/03/25;31/03/25;07/04/25;14/04/25;21/04/25;28/04/25;02/05/25;03/05/25;04/05/25
		Albicocche - nostrane;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 2,65;€ 2,45;€ 2,21;€ 2,00
		Ciliegie - Bigarreau; NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;NaN;€ 8,00;€ 5,50;€ 5,50;€ 4,63
		Fragole - Candonga;€ 5,00;€ 4,57;€ 4,10;€ 3,87;€ 3,59;€ 3,53;€ 3,15;€ 3,09;€ 3,09;€ 3,14;€ 3,09;€ 2,91;€ 2,84;€ 2,74;€ 2,61
		Fragole - nostrane;€ 3,82;€ 3,15;€ 2,85;€ 2,75;€ 2,65;€ 2,54;€ 2,29;€ 2,22;€ 2,22;€ 2,14;€ 2,13;€ 2,64;€ 2,59;€ 2,35;€ 2,21
		Kiwi - Gold (Polpa gialla);€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,85;€ 2,45;€ 2,45;€ 2,45;€ 2,45
		Kiwi - Hayward;€ 1,83;€ 1,83;€ 1,82;€ 1,82;€ 1,82;€ 1,82;€ 1,82;€ 1,83;€ 1,83;€ 1,83;€ 1,83;€ 1,87;€ 1,87;€ 1,89;€ 1,86
		Mele - Annurca;€ 1,38;€ 1,38;€ 1,38;€ 1,38;€ 1,38;€ 1,38;€ 1,38;€ 1,58;€ 1,58;€ 1,58;€ 1,58;€ 1,58;€ 1,58;€ 1,58;€ 1,58
		...
	 */
	
	public final String INTESTAZIONE = "Prodotti";
	
	public MappaFrutti leggiRilevazioniPrezzi(Reader reader) throws IOException, BadFileFormatException {
		MappaFrutti result = new MappaFrutti();
		Objects.requireNonNull(reader,"il reader è null");
		BufferedReader br = new BufferedReader(reader); 
		String lineaIntestazione = br.readLine(); //leggo la prima riga di intestazione
		String[] tokensIntestazione =lineaIntestazione.split(";"); //tokenizzo la riga di intestazione
		SortedSet<LocalDate> elencoDate = new TreeSet<>();
		if(tokensIntestazione[0].trim().isBlank()) {
			throw new BadFileFormatException("Manca l'intestazione!");
		}
		if(!tokensIntestazione[0].trim().equals(INTESTAZIONE)) {
			throw new BadFileFormatException("Intestazione non rispettata!! nella riga: "+lineaIntestazione);
		}
		for(int i=1;i<tokensIntestazione.length;i++) { //partendo dal secondo token (indice=1) faccio il parsing di tutte le date
			LocalDate data;
			try {
				data = LocalDate.parse(tokensIntestazione[i], Formatters.itDateFormatter);
			}catch(DateTimeParseException e) {
				throw new BadFileFormatException("Errore nel parsing della data numero: "+(i-1)+" nella riga di intestazione");
			}
			elencoDate.add(data); //aggiungo la data appena letta
		}
		ArrayList<LocalDate> dateAsList = new ArrayList<>(elencoDate); //più comodo lavorare su una lista ordinata
		String line;
		while((line=br.readLine())!=null) { //qui leggo tutte le restanti righe del file
			if(line.isBlank()) {
				continue; //salto eventuali righe vuote
			}
			String[] items = line.split(";"); //splitto prima sui "punto e virgola" per avere tutti gli items
			String[] informazioni = items[0].trim().split("-"); //adesso splitto il primo item sul trattino "-" per ricavare il tipo e sottotipo di frutto
			if(informazioni.length!=2) { //qui entra se manca il trattino puure se ce ne dovesse essere più di uno (più "tagli" di quello che ci aspettiamo)
				throw new BadFileFormatException("trattino separatore [-] mancante (o di troppo) a riga: "+line);
			}
			String frutto = informazioni[0].trim();
			String sottotipo = informazioni[1].trim();
			if((items.length-1)!=dateAsList.size()) { //caso in cui numero di date != numero di prezzi
				throw new BadFileFormatException("numero di date e di prezzi non sono uguali!");
			}
			for(int i=1; i<items.length;i++) {
				if(items[i].trim().equals("NaN")) {
					continue; //salto i NaN
				}
				double prezzoRilevazione;
				try { //faccio il parsing dei prezzi usando il formattatore fornito
					prezzoRilevazione = Formatters.itPriceFormatter.parse(items[i].trim()).doubleValue();
				}catch(NumberFormatException e) {
					throw new BadFileFormatException("Errore nel parsing del prezzo numero "+(i-1)+" a riga: "+line);
				}
				LocalDate dataRilevazione = dateAsList.get(i-1); //prendo la data dalla lista ordinata (indice = i-1)
				Rilevazione rilevazioneEffettiva = new Rilevazione(dataRilevazione,prezzoRilevazione); //creo la rilevazione appena letta
				result.aggiungi(frutto, sottotipo, rilevazioneEffettiva); //aggiungo i dati nella mappaFrutti
			}
		}
		// **** DA REALIZZARE ****
		// Il metodo lancia NullPointerException in caso di reader nullo, o BadFileFormatException con 
		// dettagliato messaggio d’errore in caso di mancato rispetto del formato previsto, catturando 
		// eventuali altre eccezioni interne. In particolare deve verificare: 
		// - per quanto riguarda la riga di intestazione: 
		//    a) che abbia come primo elemento la parole chiave prevista 
		//    b) che le date successive, di cui occorre memorizzare il numero ai fini delle verifiche 
		//       successive, siano tutte nel corretto formato italiano SHORT 
		// - per quanto riguarda le righe successive: 
		//    a) che siano presenti tanti prezzi (o “NaN”) quante le date specificate nella riga di 
		//       intestazione 
		//    b) che inizino con l’identificativo univoco della specifica frutta, nel formato previsto. 

		return result;
	}
	
}
