package timesheet.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.io.BufferedReader;


public class MyProjectReader implements ProjectReader {
	
	/* Ogni riga contiene: 
		- il nome del progetto (che può contenere spazi), seguito da una o più tabulazioni 
		- la frase “ore previste:”, seguita da uno o più spazi 
		- un numero intero, che rappresenta il totale di ore previste complessivamente sul progetto 
	 * */
	// Lezioni di Fondamenti T2		ore previste: 80
	// Lezioni di Linguaggi			ore previste: 64
	// Progetto Peonie				ore previste: 350
	// Progetto Innova				ore previste: 700
	
	public Map<String,Integer> projectHours(Reader reader) throws IOException, BadFileFormatException {
		Map<String,Integer> result=new HashMap<>();
		Objects.requireNonNull(reader,"reader è null");
		BufferedReader br = new BufferedReader(reader);
		String line;
		String projectName="";
		int minutiLavorati=0;
		while((line=br.readLine())!=null) {
			if(line.isBlank()) {
				continue;
			}
			String[] items = line.split("\\t+");
			if(items.length!=2) {
				throw new BadFileFormatException("Formato illegale, attesa una sola separazione per tabulazioni a riga: "+line);
			}
			if(items[0].trim().isBlank()) {
				throw new BadFileFormatException("Nome progetto mancante a riga: "+line);
			}
			projectName=items[0].trim();
			String[] altriItems=items[1].split(":");
			if(!altriItems[0].trim().equals("ore previste")) {
				throw new BadFileFormatException("parte fissa ore previste: mancante a riga: "+line);
			}
			try {
				minutiLavorati=Integer.parseInt(altriItems[1].trim());
			}catch(NumberFormatException e) {
				throw new BadFileFormatException("Errore nel parsing dei minuti lavorati a riga: "+line);
			}
			if(minutiLavorati<=0) {
				throw new BadFileFormatException("Minuti negativi a riga: "+line);
			}
			result.put(projectName, minutiLavorati);
		}
		return result;
		/*
		 * Questo metodo effettua le letture: lancia NullPointerException in caso di reader nullo, o BadFileFormatException 
		 * con dettagliato messaggio d’errore in caso di mancato rispetto del formato previsto, catturando eventuali altre 
		 * eccezioni interne. In particolare deve verificare:
			- che gli elementi in ogni riga siano esattamente tre 
			- che il secondo elemento sia la frase prevista
			- che l’ultimo elemento sia un intero strettamente positivo
		 * */
		// *****************************
		// DA FARE
		// *****************************		
	}
	
}
