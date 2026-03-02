package tram.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;


public class Linea {
	
	private List<Fermata> fermate;
	private String nome;
	private Frequenza freq;
	private LocalTime primaCorsa, ultimaCorsa;
	
	public Linea(String nome, Frequenza freq, LocalTime primaCorsa, LocalTime ultimaCorsa, List<Fermata> fermate) {
		validazioneArgomenti(nome,freq,primaCorsa,ultimaCorsa);
		this.nome=nome;
		this.freq=freq;
		this.primaCorsa=primaCorsa;
		this.ultimaCorsa=ultimaCorsa;
		validazioneListaFermate(fermate);
		this.fermate=fermate;
		validazioneCongruenza(freq,primaCorsa,ultimaCorsa);
	}
	
	private void validazioneArgomenti(String nome, Frequenza freq, LocalTime inizio, LocalTime fine) {
		if(nome==null || nome.isBlank()) throw new IllegalArgumentException("nome linea nullo o vuoto");
		if(freq==null) throw new IllegalArgumentException("frequenza linea nulla");
		if(inizio==null || fine==null) throw new IllegalArgumentException("orario di inizio o fine nullo");
		if(fine.isBefore(inizio)) throw new IllegalArgumentException("orario ultima corsa precede l'orario della prima corsa");
	}

	private void validazioneListaFermate(List<Fermata> fermate) {
		var gruppiFermatePerNome = fermate.stream().collect(Collectors.groupingBy(Fermata::nome));
		gruppiFermatePerNome.values().forEach(l -> {if(l.size()>1) throw new IllegalArgumentException("nomi fermate duplicati");});
		var gruppiFermatePerMinuti = fermate.stream().collect(Collectors.groupingBy(Fermata::minutiDaCapolinea));
		gruppiFermatePerMinuti.values().forEach(l -> {if(l.size()>1) throw new IllegalArgumentException("minuti da capolinea duplicati");});
		if(gruppiFermatePerMinuti.get(0)==null) throw new IllegalArgumentException("mancanza del capolinea iniziale");
	}
	
	private void validazioneCongruenza(Frequenza freq, LocalTime inizio, LocalTime fine) {
		long durataServizioInMinuti = Duration.between(inizio, fine).toMinutes();
		int frequenzaNaturaleInMinuti=0;
		for(Fermata e : fermate) {
			if(e.minutiDaCapolinea()>frequenzaNaturaleInMinuti) {
				frequenzaNaturaleInMinuti=e.minutiDaCapolinea();
			}
		}
		if(durataServizioInMinuti%frequenzaNaturaleInMinuti!=0) {
			throw new IllegalArgumentException("Violato il vincolo 1"); 
		}
		if(frequenzaNaturaleInMinuti%freq.valore()!=0) {
			throw new IllegalArgumentException("Violato il vincolo 2"); 
		}
		
		
		//
		// ************ DA FARE ************ 
		// Verifica i due vincoli fondamentali: 
		// 1) che la durata del servizio sia coerente con la durata del tragitto (vincolo 1), 
		// 2) che la durata del tragitto sia coerente con la frequenza del servizio (vincolo 2)
		// lanciando IllegalArgumentException con opportuno messaggio d’errore in caso contrario
		// 
		// Dal dominio del problema, si ricordi chei due vincoli sono espressi come segue:
		// 1) La durata del servizio (differenza fra ultima e prima corsa) dev’essere multipla pari della durata del tragitto
		//    [oppure, detto altrimenti: dev'essere multipla della frequenza naturale del tragitto]
		// 2) La frequenza operativa del servizio dev’essere uguale o sottomultipla della frequenza naturale del tragitto
		// ********************************* 
		//
	}

	public List<Fermata> getFermate() {
		return fermate;
	}
	
	public Optional<Fermata> getFermata(String nome) {
		return fermate.stream().filter(f -> f.nome().equals(nome)).findFirst();
	}
	
	public OptionalInt getMinutiFermata(String nome) {
		return  fermate.stream().filter(f -> f.nome().equals(nome))
								.findFirst()
								.map(Fermata::minutiDaCapolinea)
								.map(OptionalInt::of)
								.orElse(OptionalInt.empty());
	}

	public String getNome() {
		return nome;
	}

	public Frequenza getFrequenza() {
		return freq;
	}

	public LocalTime getOrarioPrimaCorsaAlCapolineaIniziale() {
		return primaCorsa;
	}

	public LocalTime getOrarioUltimaCorsaAlCapolineaIniziale() {
		return ultimaCorsa;
	}
	
	public LocalTime getOrarioPrimaCorsaAllaFermata(String nome, Direzione dir) {		
		var delta = this.getMinutiFermata(nome);
		if (delta.isEmpty()) throw new IllegalArgumentException("Fermata inesistente: " + nome);
		return switch(dir) {
		 	case ANDATA   -> getOrarioPrimaCorsaAlCapolineaIniziale().plusMinutes(delta.getAsInt());
			case RITORNO -> getOrarioPrimaCorsaAlCapolineaFinale().plusMinutes(getDurataTragitto()-delta.getAsInt());
		};
	}

	public LocalTime getOrarioUltimaCorsaAllaFermata(String nome, Direzione dir) {
		var delta = this.getMinutiFermata(nome);
		if (delta.isEmpty()) throw new IllegalArgumentException("Fermata inesistente: " + nome);
		return switch(dir) {
	 		case ANDATA   -> getOrarioUltimaCorsaAlCapolineaIniziale().plusMinutes(delta.getAsInt());
	 		case RITORNO -> getOrarioUltimaCorsaAlCapolineaFinale().plusMinutes(getDurataTragitto()-delta.getAsInt());
		};
	}
	
	public LocalTime getOrarioPrimaCorsaAlCapolineaFinale() {
		return getOrarioPrimaCorsaAlCapolineaIniziale().plusMinutes(getDurataTragitto());
	}

	public LocalTime getOrarioUltimaCorsaAlCapolineaFinale() {
		return getOrarioUltimaCorsaAlCapolineaIniziale().plusMinutes(getDurataTragitto());
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Linea other = (Linea) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return nome;
	}
	
	public int getDurataTragitto() {
		return fermate.stream().max(Comparator.comparingInt(Fermata::minutiDaCapolinea)).get().minutiDaCapolinea();
	}
		
}
