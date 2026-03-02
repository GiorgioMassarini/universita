package tram.model;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

public class Analizzatore {
	
	private Set<Linea> linee;

	public Analizzatore(Set<Linea> linee) {
		if (linee==null) throw new IllegalArgumentException("Insieme linee nullo");
		if (linee.size()<=0) throw new IllegalArgumentException("Insieme linee vuoto");
		this.linee = linee;
	}

	public Set<Linea> getLinee() {
		return linee;
	}
	
	public Optional<Linea> getLinea(String nome) {
		return linee.stream().filter(linea -> linea.getNome().equals(nome)).findFirst();
	}
	
	public LocalTime prossimoPassaggio(String nomeLinea, String nomeFermata, Direzione dir, LocalTime orario) {
		if(nomeLinea==null) {
			throw new IllegalArgumentException("nome della linea è null");
		}
		if(nomeFermata==null) {
			throw new IllegalArgumentException("nome della fermata è null");
		}
		if(dir==null) {
			throw new IllegalArgumentException("la direzione specificata è null");
		}
		if(orario==null) {
			throw new IllegalArgumentException("orario è null");
		}
		if(nomeLinea.isBlank()) {
			throw new IllegalArgumentException("nome della linea è vuoto");
		}
		if(nomeFermata.isBlank()) {
			throw new IllegalArgumentException("nome della fermata è vuoto");
		}
		boolean esiste=false;
		Linea currentLinea=null;
		for(Linea e : linee) {
			if(e.getNome().equals(nomeLinea)) {
				esiste=true;
				currentLinea=e;
			}
		}
		if(!esiste) {
			throw new IllegalArgumentException("linea inesistente");
		}
		LocalTime primaCorsa=currentLinea.getOrarioPrimaCorsaAllaFermata(nomeFermata, dir);
		LocalTime ultimaCorsa=currentLinea.getOrarioUltimaCorsaAllaFermata(nomeFermata, dir);
		LocalTime temp=primaCorsa;
		if(orario.isAfter(ultimaCorsa)||orario.isBefore(primaCorsa)) {
			return primaCorsa;
		}else {
			while(temp.isBefore(orario)) {
				temp=temp.plusMinutes(currentLinea.getFrequenza().valore());
			}
		}
		return temp;
		
		//
		// ************ DA FARE ************ 
		//
		// Il metodo deve preventivamente verificare, lanciando IllegalArgumentException in caso di violazione:
		// - che gli argomenti non siano nulli, o, nel caso di stringhe, anche blank
		// - che una linea di nome uguale a quello specificato esista realmente
		//   NB: non occorre analoga verifica per la fermata perché tale controllo è già svolto nei metodi getOrarioXXX di Linea
		// Successivamente, il metodo:
		// - calcola l’orario di servizio (prima/ultima corsa) alla fermata richiesta nella direzione indicata
		// - se l’orario richiesto è esterno all’orario di servizio calcolato, restituisce l’orario della prima corsa
		// - se invece l’orario richiesto è ricompreso nell’orario di servizio odierno, calcola, tenendo conto della frequenza del servizio, 
		//   l’orario della prima corsa di orario uguale o successivo a quello richiesto, e lo restituisce come risultato
		//
		// ********************************* 
	}	
}
