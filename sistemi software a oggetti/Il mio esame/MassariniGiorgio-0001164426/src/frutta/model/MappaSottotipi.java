package frutta.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Set;


public class MappaSottotipi {
	private Map<String, List<Rilevazione>> mappaSottotipi;
	
	public MappaSottotipi() {
		this.mappaSottotipi = new HashMap<>();
	}
	
	public void aggiungi(String sottotipo, Rilevazione rilevazione) {
		//validazione dati
		Objects.requireNonNull(sottotipo,"sottotipo è null");
		Objects.requireNonNull(rilevazione,"rilevazione è null");
		if(sottotipo.isBlank()) {
			throw new IllegalArgumentException("sottotipo è blank");
		}
		List<Rilevazione> listaRilevazioni= mappaSottotipi.get(sottotipo);
		if(listaRilevazioni==null) {
			listaRilevazioni=new ArrayList<>();
		}
		listaRilevazioni.add(rilevazione);
		mappaSottotipi.put(sottotipo, listaRilevazioni);
		
		// *****DA REALIZZARE*****
		// Accetta come argomenti un sottotipo (stringa) e una Rilevazione: il metodo 
		// aggiunge la rilevazione alla lista del corrispondente sottotipo. 
		// Effettua preventivamente la validazione degli argomenti di ingresso secondo le usuali 
		// linee guida, lanciando NullPointerException o IllegalArgumentException con idonei e 
		// specifici messaggi d’errore secondo necessità
	}
	
	public void aggiungi(String sottotipo, List<Rilevazione> listaRilevazioni) {
		listaRilevazioni.forEach(rilevazione -> this.aggiungi(sottotipo, rilevazione));
	}

	public Set<String> sottotipi(){
		return mappaSottotipi.keySet();
	}
	
	public List<Rilevazione> rilevazioniPerSottotipo(String sottotipo){
		if (mappaSottotipi.get(sottotipo)==null) throw new NoSuchElementException("Categoria inesistente: " + sottotipo);
		else return mappaSottotipi.get(sottotipo).stream().sorted().toList();
	}
	
	public OptionalDouble prezzoMedio(String sottotipo) {
		List<Rilevazione> prodottiDiInteresse = rilevazioniPerSottotipo(sottotipo);
		double totale=0;
		for(Rilevazione e : prodottiDiInteresse) {
			totale=totale+e.prezzo();
		}
		double result= (totale/prodottiDiInteresse.size());
		return OptionalDouble.of(result);
		// *****DA REALIZZARE*****
		// Restituisce il prezzo medio di un dato sottotipo di frutta
	}
	
	public OptionalDouble prezzoMinimo(String sottotipo) {
		List<Rilevazione> prodottiDiInteresse = rilevazioniPerSottotipo(sottotipo);
		double result=Double.MAX_VALUE;
		for(Rilevazione e : prodottiDiInteresse) {
			if(e.prezzo()<result) {
				result=e.prezzo();
			}
		}
		return OptionalDouble.of(result);
		// *****DA REALIZZARE*****
		// Restituisce il prezzo minimo di un dato sottotipo di frutta
	}
	
	public OptionalDouble prezzoMassimo(String sottotipo) {
		List<Rilevazione> prodottiDiInteresse = rilevazioniPerSottotipo(sottotipo);
		double result=Double.MIN_VALUE;
		for(Rilevazione e : prodottiDiInteresse) {
			if(e.prezzo()>result) {
				result=e.prezzo();
			}
		}
		return OptionalDouble.of(result);
		// *****DA REALIZZARE*****
		// Restituisce il prezzo massimo di un dato sottotipo di frutta
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(mappaSottotipi);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		MappaSottotipi other = (MappaSottotipi) obj;
		return Objects.equals(mappaSottotipi, other.mappaSottotipi);
	}

	@Override
	public String toString() {
		return mappaSottotipi.toString();
	}
	
}
