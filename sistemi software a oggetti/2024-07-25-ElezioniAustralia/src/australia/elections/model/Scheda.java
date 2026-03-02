package australia.elections.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;

public record Scheda(SortedMap<String,Integer> candidati) {
	
	public Scheda {
		Objects.requireNonNull(candidati, "mappa candidati nulla");
		if (candidati.size()==0) throw new IllegalArgumentException("mappa candidati vuota");
		var numeroCandidati = candidati.size();
		var preferenzeOK = candidati.values().stream().distinct().filter(n -> n>=1 && n<=numeroCandidati).count();
		if (preferenzeOK!=numeroCandidati) throw new IllegalArgumentException("Preferenze errate per la scheda");
	}
	
	public List<String> candidatiInOrdineDiPreferenza(){
		return candidati.entrySet().stream().sorted(Comparator.comparing(Entry::getValue)).map(Entry::getKey).toList();
	}
	
	public int ordineDiPreferenza(String nomeCandidato) {
		Objects.requireNonNull(nomeCandidato, "nome candidato nullo");
		if (nomeCandidato.isEmpty() || nomeCandidato.isBlank()) throw new IllegalArgumentException("nome candidato vuoto");
		Integer pref = candidati.get(nomeCandidato);
		if (pref!=null) return pref;
		else throw new IllegalArgumentException("Candidato " + nomeCandidato + " non presente");
	}
	
	public Optional<String> successivo(String nomeCandidato) {
		var numeroCandidatoSuccessivo = ordineDiPreferenza(nomeCandidato)+1; // ordine logico = 1-based
		var posizioneCandidatoSuccessivo = numeroCandidatoSuccessivo -1; 	 // lista = 0-based
			//System.err.println(nomeCandidato + " = " + nomeCandidato + ", pos = " + ordineDiPreferenza(nomeCandidato));
			//System.err.println("posizione logica successivo = " + numeroCandidatoSuccessivo);
			//System.err.println("succ(" + nomeCandidato + ") = " + candidatiInOrdineDiPreferenza().get(posizioneCandidatoSuccessivo));
		return (posizioneCandidatoSuccessivo<candidati.size()) ? 
					Optional.of(candidatiInOrdineDiPreferenza().get(posizioneCandidatoSuccessivo)) : Optional.empty(); 
	}
	
	public Optional<String> successivoFra(String nomeCandidato, Set<String> possibili) {
			//System.err.println("Searching " + nomeCandidato + " fra " + possibili);
		Optional<String> result = successivo(nomeCandidato);
			//System.err.println("Hypothesis:" + result.get());
		if (result.isEmpty()) return result;
		while(!possibili.contains(result.get())) {
			result = successivo(result.get());
			if (result.isEmpty()) return result;
			//System.err.println("New hypothesis:" + result.get());
		}
			//System.err.println("Result:" + result.get());
		return result;
	}
	
	@Override
	public String toString() {
		var sb = new StringBuilder();
		candidati().entrySet().forEach(candidato -> sb.append( candidato.getKey() + "\t" + candidato.getValue() + System.lineSeparator()));
		return sb.toString();
	}

}
