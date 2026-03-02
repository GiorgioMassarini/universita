package pacchi.model;

import java.util.Random;
import java.util.Set;

public class Dottore {

	private StatoPartita statoPartita;
	
	public Dottore(StatoPartita statoPartita) {
		this.statoPartita = statoPartita;
	}
	
	public Risposta interpella() {
		int media = media();
		int massimo = media;
		int minimo = Math.round(media/4);
		Random rd = new Random();
		int offerta = rd.nextInt(massimo-minimo+1) + minimo;
		return new Risposta(new Valore(offerta));
		
		// ***************
		// ****DA FARE****
		// Specifica: il Dottore offre sempre fra 1/4 della media e la media dei premi rimasti
		// Sintetizzare la risposta del Dottore che incapsula il Valore offerto
		// ***************
	}

	public StatoPartita getStatoPartita() { // metodo presente solo per motivi di test
		return statoPartita;
	}
	
	public int media() { // metodo reso pubblico solo per motivi di test
		Set<Pacco> pacchiDaAprire = statoPartita.getPacchiDaAprire();
		Pacco paccoConcorrente = statoPartita.getPaccoConcorrente();
		int somma=0;
		for(Pacco e : pacchiDaAprire) {
			somma=somma+e.premio().valore();
		}
		somma=somma+paccoConcorrente.premio().valore();
		int media = Math.round(somma / (float)(pacchiDaAprire.size() + 1));
		return media;
		
		
		// ***************
		// ****DA FARE****
		// Restituisce la media dei premi dei pacchi ancora da aprire, trasformata in int
		// L'elenco dei pacchi da aprire è recuperabile tramite i metodi di StatoPartita:
		// - getPacchiDaAprire, che restituisce l'insieme dei pacchi ancora da aprire in possesso
		//   dei partecipanti (escluso quindi quello in mano al concorrente)
		// - getPaccoConcorrente, che restituisce ovviamente il solo pacco del concorrente.
		// ***************		
	}
			
}
