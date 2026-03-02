package pacchi.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Partita {

	private Set<Territorio> territori;
	private Set<Valore> premi;
	private Set<Pacco> pacchi;
	private StatoPartita statoPartita;
	private Dottore dottore;
	
	public Partita(Set<Territorio> territori, Set<Valore> premi) {
		if (territori==null) throw new IllegalArgumentException("Lista territori nulla");
		if (premi==null) throw new IllegalArgumentException("Lista premi nulla");
		if (territori.size()<=0) throw new IllegalArgumentException("Lista territori di lunghezza inammissibile");
		if (premi.size()<=0) throw new IllegalArgumentException("Liste premi di lunghezza inammissibile");
		if (territori.size()!=premi.size()) throw new IllegalArgumentException("Liste territori e valori di lunghezza diversa");
		//
		this.territori = territori;
		this.premi = new HashSet<>(premi);
		this.pacchi = generaPacchi(territori, premi);
		//
		int indexPaccoConcorrente = (int) Math.floor(Math.random()*pacchi.size());
		var paccIterator = pacchi.iterator();
		for(int k=0; k<indexPaccoConcorrente;k++) paccIterator.next(); // ne salta un certo numero
		Pacco paccoConcorrente = paccIterator.next();
		paccIterator.remove();
		
		this.statoPartita = new StatoPartita(pacchi, paccoConcorrente);
		this.dottore = new Dottore(this.statoPartita);
	}

	public Set<Pacco> generaPacchi(Set<Territorio> territori, Set<Valore> premi) {
	    Set<Pacco> result = new HashSet<>();

	    int N = premi.size();
	    
	    Territorio[] territoriAsArray = territori.toArray(new Territorio[0]);
	    Valore[] premiAsArray = premi.toArray(new Valore[0]);

	    // Genera i numeri da 1 a N senza ripetizioni
	    List<Integer> numeri = IntStream.rangeClosed(1, N).boxed().collect(Collectors.toList());
	    Collections.shuffle(numeri);

	    for (int i = 0; i < N; i++) {
	        Numero numeroAssegnato = new Numero(numeri.get(i));
	        result.add(new Pacco(territoriAsArray[i], numeroAssegnato, premiAsArray[i]));
	    }
	    return result;
	}
		// ***************
		// ****DA FARE****
		// NB: questo metodo dovrebbe essere privato: viene reso pubblico solo per motivi di test
		//     Per lo stesso motivo non effettua validaazione degli argomenti, in quanto già validati dal costruttore
		// Specifica: il metodo itera sugli insiemi ricevuti accoppiando un territorio, un premio e un numero di pacco
		// sorteggiato fra 1 e N, senza ripetizioni e senza escluderne alcuno

	public Set<Territorio> getTerritori() {
		return territori;
	}

	public Set<Valore> getPremi() {
		return premi;
	}

	public Set<Pacco> getPacchi() {
		return pacchi;
	}

	public StatoPartita getStatoPartita() {
		return statoPartita;
	}

	public Risposta interpellaDottore() {
		return dottore.interpella();
	}
	
	public double media() {
		return dottore.media();
	}
	
	public Valore apriPacco(Numero numeroPacco) {
		Valore result=null;
		int numero = numeroPacco.valore();
		int totPacchi = pacchi.size();
		if(numero<1 || numero>totPacchi+1) {
			throw new IllegalArgumentException("numero pacco illegale");
		}
		for(Pacco e : pacchi) {
			if(e.numero().equals(numeroPacco)) {
				result = statoPartita.apriPacco(e);
			}
		}
		return result;
		// ***************
		// ****DA FARE****
		// Preliminarmente verifica che il numero del pacco sia nel range 1..N, lanciando altrimenti una IEA con opportuno messaggio 
		// Se tutto è ok, recupera dall'insieme dei pacchi quello con il numero richiesto, lo apre tramite il metodo apriPacco di StatoPartita
		// e ne restituisce il valore del premio in esso racchiuso
		// ***************
	}
	
	public Pacco getPacco(Numero numeroPacco) {
		if(numeroPacco.valore()<0 || numeroPacco.valore()>this.pacchi.size()+1) throw new IllegalArgumentException("Impossibile aprire pacco n." + numeroPacco + ", pacco inesistente");
		return this.pacchi.stream().filter(p -> p.numero().equals(numeroPacco)).toList().get(0);
	}

	@Override
	public String toString() {
		return "Partita [listaRegioni=" + territori + ", listaValori=" + premi + ", listaPacchi=" + pacchi
				+ ", statoPartita=" + statoPartita + ", dottore=" + dottore + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(premi, territori);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Partita other = (Partita) obj;
		return Objects.equals(premi, other.premi) && Objects.equals(territori, other.territori);
	}
			
}
