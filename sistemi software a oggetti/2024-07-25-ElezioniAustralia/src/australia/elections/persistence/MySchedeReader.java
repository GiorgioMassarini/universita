package australia.elections.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import australia.elections.model.Scheda;

public class MySchedeReader implements SchedeReader {

	public List<Scheda> leggiSchede(Reader reader) throws IOException, BadFileFormatException {
		List<Scheda> result = new ArrayList<>();
		SortedMap<String,Integer> candidati = new TreeMap<>();
		String line;
		BufferedReader br = new BufferedReader(reader);
		while((line=br.readLine())!=null) {
			if(line.isBlank()) {
				continue;
			}
			String[] items=line.split(",");
			String currentCandidato="";
			Integer currentPreferenza=0;
			if(items.length%2!=0) {
				throw new BadFileFormatException("Numero di candidati e voti dispari a riga: "+line);
			}
			for(int i=0;i<items.length;i++) {
				if(i%2==0) {
					currentCandidato=items[i].trim();
					if(currentCandidato.matches("\\d+")) {
						throw new BadFileFormatException("Candidato composto da soli caratteri a riga: "+line);
					}
				}else {
					try {
						currentPreferenza=Integer.parseInt(items[i].trim());
					}catch(NumberFormatException e) {
						throw new BadFileFormatException("Errore nel parsing di una preferenza a riga: "+line);
					}
				}
				if(i%2!=0) {
					candidati.put(currentCandidato, currentPreferenza);
				}
			}
			try {
				result.add(new Scheda(candidati));
			}catch(IllegalArgumentException e) {
				throw new BadFileFormatException("Errore nel parsing dei candidati a riga: "+line);
			}
		}
		return result;
	}
}
