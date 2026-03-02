package australia.elections.model;

import java.util.Map.Entry;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;


public class Risultato {
	
	private final Map<String, Long> risultati;

	public Risultato (Map<String,Long> risultati){
		Objects.requireNonNull(risultati, "mappa risultati nulla");
		if (risultati.size()==0) throw new IllegalArgumentException("mappa risultati vuota");
		this.risultati=risultati;
	}
	
	public Map<String, Long> mappaRisultati(){
		return risultati;
	}
	
	@Override
	public String toString() {
		var sb = new StringBuilder();
		mappaRisultati().entrySet().forEach(candidato -> sb.append( candidato.getKey() + "\t" + candidato.getValue() + System.lineSeparator()));
		return sb.toString();
	}
	
	public Entry<String,Long> vincitore(){
		return mappaRisultati().entrySet().stream().max(Comparator.comparing(Entry::getValue)).get();
	}

}
