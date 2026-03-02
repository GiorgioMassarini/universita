package frutta.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public class MappaFrutti {
	private Map<String, MappaSottotipi> mappaFrutti;
	private SortedSet<LocalDate> elencoDate;
	
	public MappaFrutti() {
		this.mappaFrutti = new HashMap<>();
		this.elencoDate = new TreeSet<>();
	}
	
	public void aggiungi(String frutta, String sottotipo, Rilevazione rilevazione) {
		//validazione dati
		Objects.requireNonNull(frutta, "frutta è null");
		Objects.requireNonNull(sottotipo, "sottotipo è null");
		Objects.requireNonNull(rilevazione, "rilevazione è null");
		if(frutta.isBlank()) {
			throw new IllegalArgumentException("frutta è blank");
		}
		if(sottotipo.isBlank()) {
			throw new IllegalArgumentException("sottotipo è blank");
		}
		//
		MappaSottotipi mappaSottotipi = mappaFrutti.get(frutta);
		if(mappaSottotipi==null) {
			mappaSottotipi=new MappaSottotipi();
		}
		mappaSottotipi.aggiungi(sottotipo, rilevazione);
		mappaFrutti.put(frutta, mappaSottotipi);
		
		// *****DA REALIZZARE*****
		// Accetta come argomenti il tipo di frutta, il sottotipo e una Rilevazione: dopo la 
		// usuale fase di validazione degli argomenti, secondo le usuali linee guida, che nel caso 
		// lanciano le usuali eccezioni con adeguato messaggio d’errore, il metodo aggiunge la 
		// rilevazione alla mappa del corrispondente sottotipo. 
	}
	
	public void aggiungi(String frutta, String sottotipo, List<Rilevazione> listaRilevazioni) {
		listaRilevazioni.forEach(rilevazione -> this.aggiungi(frutta, sottotipo, rilevazione));
	}
	
	public OptionalDouble prezzoMedio(String frutta) {
		Objects.requireNonNull(frutta,      "Il tipo di frutta non può essere nullo");
		if (frutta.isBlank())    throw new IllegalArgumentException("Il tipo di frutta non può essere blank");
		if (mappaFrutti.get(frutta)==null) throw new NoSuchElementException("Tipo di frutta inesistente: " + frutta);
		return mappaFrutti.get(frutta).sottotipi().stream()
				  .flatMap(categoria -> mappaFrutti.get(frutta).rilevazioniPerSottotipo(categoria).stream())
				  .mapToDouble(Rilevazione::prezzo)
				  .filter(n -> !Double.isNaN(n))
				  .average();
	}
	
	public OptionalDouble prezzoMinimo(String frutta) {
		Objects.requireNonNull(frutta,      "Il tipo di frutta non può essere nullo");
		if (frutta.isBlank())    throw new IllegalArgumentException("Il tipo di frutta non può essere blank");
		if (mappaFrutti.get(frutta)==null) throw new NoSuchElementException("Tipo di frutta inesistente: " + frutta);
		return mappaFrutti.get(frutta).sottotipi().stream()
				  .flatMap(categoria -> mappaFrutti.get(frutta).rilevazioniPerSottotipo(categoria).stream())
				  .mapToDouble(Rilevazione::prezzo)
				  .filter(n -> !Double.isNaN(n))
				  .min();
	}
	
	public OptionalDouble prezzoMassimo(String frutta) {
		Objects.requireNonNull(frutta, "Il tipo di frutta non può essere nullo");
		if (frutta.isBlank()) throw new IllegalArgumentException("Il tipo di frutta non può essere blank");
		if (mappaFrutti.get(frutta)==null) throw new NoSuchElementException("Tipo di frutta inesistente: " + frutta);
		return mappaFrutti.get(frutta).sottotipi().stream()
				  .flatMap(categoria -> mappaFrutti.get(frutta).rilevazioniPerSottotipo(categoria).stream())
				  .mapToDouble(Rilevazione::prezzo)
				  .filter(n -> !Double.isNaN(n))
				  .max();
	}
	
	public OptionalDouble prezzoMedio(String frutta, String sottotipo) {
		Objects.requireNonNull(frutta, "Il tipo di frutta non può essere nullo");
		if (frutta.isBlank()) throw new IllegalArgumentException("Il tipo di frutta non può essere blank");
		Objects.requireNonNull(sottotipo, "Il sottotipo non può essere nullo");
		if (sottotipo.isBlank()) throw new IllegalArgumentException("Il sottotipo non può essere blank");
		//
		if (mappaFrutti.get(frutta)==null) throw new NoSuchElementException("Tipo di frutta inesistente: " + frutta);
		return mappaFrutti.get(frutta).prezzoMedio(sottotipo);
	}
	
	public OptionalDouble prezzoMinimo(String frutta, String sottotipo) {
		Objects.requireNonNull(frutta, "Il tipo di frutta non può essere nullo");
		if (frutta.isBlank()) throw new IllegalArgumentException("Il tipo di frutta non può essere blank");
		Objects.requireNonNull(sottotipo, "Il sottotipo non può essere nullo");
		if (sottotipo.isBlank()) throw new IllegalArgumentException("Il sottotipo non può essere blank");
		//
		if (mappaFrutti.get(frutta)==null) throw new NoSuchElementException("Tipo di frutta inesistente: " + frutta);
		return mappaFrutti.get(frutta).prezzoMinimo(sottotipo);
	}
	
	public OptionalDouble prezzoMassimo(String frutta, String sottotipo) {
		Objects.requireNonNull(frutta, "Il tipo di frutta non può essere nullo");
		if (frutta.isBlank()) throw new IllegalArgumentException("Il tipo di frutta non può essere blank");
		Objects.requireNonNull(sottotipo, "La categoria di frutta non può essere nullo");
		if (sottotipo.isBlank()) throw new IllegalArgumentException("La categoria di frutta non può essere blank");
		//
		if (mappaFrutti.get(frutta)==null) throw new NoSuchElementException("Tipo di frutta inesistente: " + frutta);
		return mappaFrutti.get(frutta).prezzoMassimo(sottotipo);
	}
	
	public SortedSet<String> tipiFrutta(){
		Set<String> setDisordinato =  mappaFrutti.keySet();
		SortedSet<String> result = new TreeSet<>(setDisordinato);
		// *****DA REALIZZARE *****
		//  restituisce l'insieme dei tipi di frutta, ordinati secondo l’ordine lessicografico naturale
		return result;
	}
	
	public SortedSet<String> sottotipi(String frutta) {
		MappaSottotipi mappaSottotipi = mappaFrutti.get(frutta);
		Set<String> setDisordinato= mappaSottotipi.sottotipi();
		SortedSet<String> result = new TreeSet<>(setDisordinato);
		// *****DA REALIZZARE *****
		// restituisce l'insieme dei sottotipi di un dato tipo frutta, 
		// ordinati secondo l’ordine lessicografico naturale
		return result;
	}
	
	public SortedSet<LocalDate> date(){
		var valoriMappa = mappaFrutti.entrySet();
		for(var e : valoriMappa) {
			MappaSottotipi mappaSottotipi = e.getValue();
			for(String s : mappaSottotipi.sottotipi()) {
				List<Rilevazione> listaRilevazioni = mappaSottotipi.rilevazioniPerSottotipo(s);
				for(Rilevazione r : listaRilevazioni) {
					elencoDate.add(r.data());
				}
			}
		}
		// *****DA REALIZZARE *****
		// restituisce l'insieme delle date, ordinato secondo l’ordine cronologico naturale
		return elencoDate;
	}
	
	public List<Rilevazione> rilevazioniPerSottotipo(String tipoFrutta, String sottotipo){
		if (mappaFrutti.get(tipoFrutta)==null) throw new NoSuchElementException("Tipo frutta inesistente: " + tipoFrutta);
		return mappaFrutti.get(tipoFrutta).rilevazioniPerSottotipo(sottotipo);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(mappaFrutti);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		MappaFrutti other = (MappaFrutti) obj;
		return Objects.equals(mappaFrutti, other.mappaFrutti);
	}

	@Override
	public String toString() {
		return mappaFrutti.toString();
	}

	public boolean isEmpty() {
		return mappaFrutti.isEmpty();
	}
	
	
}
