package australia.elections.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Scrutinio {

	private Map<String,List<Scheda>> mappa;
	private long totaleVoti;
	private StringBuilder logger;
	
	public Scrutinio(List<Scheda> schedeLette) {
		Objects.requireNonNull(schedeLette, "schede lette non possono essere null");
		if (schedeLette.isEmpty()) throw new IllegalArgumentException("La lista delle schede lette non può essere vuota");
		this.mappa = schedeLette.stream().collect(Collectors.groupingBy(
												scheda -> scheda.candidatiInOrdineDiPreferenza().get(0)
											));
		this.totaleVoti = schedeLette.size();
		this.logger = new StringBuilder();
	}
	
	public Risultato scrutina() {
		Map<String,Long> result = new TreeMap<String,Long>();
		while(true) {
			logger.append("INIZIO ITERAZIONE, MAPPA ATTUALE: "+mappa);
			String primo = "";
		    long votiPrimo = -1;
		    String ultimo = "";
		    long votiUltimo = Long.MAX_VALUE;
			var valoriMappa = mappa.entrySet();
			for(var e : valoriMappa) {
				long voti=e.getValue().size();
			    if(voti>votiPrimo) {
			    	votiPrimo=voti;
			    	primo=e.getKey();
			    }
			    if(voti<votiUltimo) {
			    	votiUltimo=voti;
			    	ultimo=e.getKey();
			    }
			}
			if(noMaggioranza(votiPrimo)) { //SE NON C'è MAGGIORANZA
				List<Scheda> schedeUltimo = mappa.get(ultimo); //recupero le schede dell'ultimo
				mappa.remove(ultimo); //rimuovo l'ultimo dalla mappa
				logger.append("\n E' STATO RIMOSSO IL CANDIDATO: "+ultimo+"\n");
				Optional<String> successivo = Optional.empty();
				for(Scheda e : schedeUltimo) { //itero su tutte le schede del candidato eliminato
					successivo=e.successivoFra(ultimo, mappa.keySet()); //trovo a chi dale la scheda
					List<Scheda> aCuiAggiungere=mappa.get(successivo.get());//lista a cui aggiungere la scheda
					aCuiAggiungere.add(e); //aggiungo la scheda
					mappa.put(successivo.get(), aCuiAggiungere); //aggiorno il valore nella mappa
				}
			}else { //SE C'è MAGGIORANZA
				var coppieMappa = mappa.entrySet(); 
				for(var e : coppieMappa) { //itero sulla mappa
					long voti=e.getValue().size();
					result.put(e.getKey(), voti); //riempio la mappa result
				}
				logger.append("\n RISULATO FINALE: "+result);
				return new Risultato(result); //return del risultato
			}
		}
	}

	private boolean noMaggioranza(long maxVoti) {
		return maxVoti< totaleVoti/2 + 1;
	}
	
	public long getTotaleVoti() {
		return totaleVoti;
	}
	
	public String getLog() {
		return logger.toString();
	}
}
