package pacchi.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;
import pacchi.model.Territorio;
import pacchi.model.Formatters;
import pacchi.model.Valore;
import java.io.BufferedReader;


public class MyConfigReader implements ConfigReader {
	
	@Override
	public Set<Territorio> leggiTerritori(Reader reader) throws IOException, BadFileFormatException {
		if (reader==null) throw new IllegalArgumentException("Input reader null in leggiTerritori");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line;
		var territori = new HashSet<Territorio>(); 
		while ((line = bufferedReader.readLine()) != null) {
			territori.add(new Territorio(line.trim()));
		}
		return territori;
	}

	@Override
	public Set<Valore> leggiPremi(Reader reader) throws IOException, BadFileFormatException {
		if (reader==null) throw new IllegalArgumentException("Input reader null in leggiPremi");
		BufferedReader br = new BufferedReader(reader);
		String line = br.readLine();
		Set<Valore> result = new HashSet<>();
		Set<Valore> bassi = elaboraRiga(line, ":", "PREMI BASSI");
		line = br.readLine();
		Set<Valore> alti = elaboraRiga(line, ":", "PREMI ALTI");
		if(alti.size()!=bassi.size()) {
			throw new BadFileFormatException("Numero di premi alti e bassi NON presenti in egual quantità");
		}
		result.addAll(bassi);
		result.addAll(alti);
		return result;
		
		
		// ***************
		// ****DA FARE****
		// - legge i dati del file Premi.txt, delegando al metodo privato ausiliario elaboraRiga il parsing della singola riga-premi;
		//   da specifica, per prima ci dev'essere la riga coi PREMI ALTI, per seconda quella coi PREMI BASSI
		// - restituisce il Set dei Valori letti complessivamente dalle due righe
		// - lancia IllegalArgumentException con opportuno messaggio d’errore SOLO in caso di argomento (reader) nullo,
		//   altrimenti lancia BadFileFormatException con messaggio d’errore appropriato in caso di problemi nel formato del file 
		//   (quali ad esempio mancanza/eccesso di elementi, errori nel formato dei numeri, etc.), ivi incluso il caso in cui premi 
		//   alti e premi bassi non siano presenti in egual quantità.
		//   Lascia fluire in esterno IOException in caso di altri problemi di I/O.
		// ***************
	}

	private Set<Valore> elaboraRiga(String rigaPremi, String separatore, String header) throws BadFileFormatException {
		String[] tokens = rigaPremi.split(separatore);
		Set<Valore> result = new HashSet<>();
		if(!tokens[0].trim().equals(header)) {
			throw new BadFileFormatException("Header non rispettato a riga: "+rigaPremi);
		}
		String valoriString = tokens[1].trim();
		String[] valoriTokens = valoriString.split(",");
		for(int i=0; i<valoriTokens.length;i++) {
			int numero=0;
			try {
				numero = Formatters.parse(valoriTokens[i].trim());
			}catch(IllegalArgumentException e) {
				throw new BadFileFormatException("Errore nel parsing a riga: "+rigaPremi);
			}
			if(numero<0) {
				throw new BadFileFormatException("Numero negativo a riga: "+rigaPremi);
			}
			result.add(new Valore(numero));
		}
		return result;
		
		// ***************
		// ****DA FARE****
		// - elabora la singola riga premi (letta in precedenza dal metodo leggiPremi) e ne fa il parsing utilizzando
		//   come separatore dei valori la stringa separatore ricevuta come argomento, e come intestazione della riga
		//   la stringa header ricevuta come terzo argomento
		// - lancia BadFileFormatException con idoneo messaggio d’errore nel caso di numero di elementi errato o di
		//   header errato.
		// ***************
	}

}
