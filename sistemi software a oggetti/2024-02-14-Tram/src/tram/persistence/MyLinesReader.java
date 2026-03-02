package tram.persistence;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tram.model.Fermata;
import tram.model.Formatters;
import tram.model.Frequenza;
import tram.model.Linea;

import java.io.BufferedReader;


public class MyLinesReader implements LinesReader {
	
	String nome;
	Frequenza freq;
	LocalTime inizioServizio, fineServizio;
	List<Fermata> fermate;
	
	@Override
	public Set<Linea> leggiLinee(Reader reader) throws IOException, BadFileFormatException {
		if (reader==null) throw new IllegalArgumentException("Input reader null in leggiLinee");
		Linea linea;
		var linee = new HashSet<Linea>(); 
		var bReader = new BufferedReader(reader);
		while ((linea = leggiLinea(bReader)) != null) {
			linee.add(linea);
		}
		return linee;
	}

	private Linea leggiLinea(BufferedReader bufferedReader) throws IOException, BadFileFormatException {
		String riga1, riga2;
		while((riga1=bufferedReader.readLine())!=null && riga1.isBlank());
		if(riga1==null) return null;
		riga2=bufferedReader.readLine();
		if (riga2==null || riga2.isBlank()) throw new BadFileFormatException("Riga vuota in specifica linea: " + riga2);
		//
		elaboraRiga1(riga1); // parsing e inizializzazione di nome, frequenza e orari
		elaboraRiga2(riga2); // parsing e inizializzazione della lista delle fermate		
		//
		return new Linea(nome, freq, inizioServizio, fineServizio, fermate);
	}
	
	private void elaboraRiga1(String riga1) throws BadFileFormatException {
		
		String[] tokens=riga1.split(",");
		if(!tokens[0].trim().startsWith("Linea ")) {
			throw new BadFileFormatException("la seguente riga NON inizia con 'Linea ':"+riga1);
		}
		String[] primaParte=tokens[0].trim().split("\\s+");
		this.nome=primaParte[1].trim();
		tokens[1]=tokens[1].trim();
		String[] secondaParte=tokens[1].split("\\s+");
		if(!secondaParte[0].equals("frequenza")) {
			throw new BadFileFormatException("parola 'frequenza' mancante a riga: "+riga1);
		}
		if(!secondaParte[2].equals("minuti")) {
			throw new BadFileFormatException("parola 'minuti' mancante a riga: "+riga1);
		}
		if(secondaParte.length!=3) {
			throw new BadFileFormatException("formato della frequenza sbagliato a riga: "+riga1);
		}
		int freqAsInt = Integer.parseInt(secondaParte[1].trim());
		if(freqAsInt<0||freqAsInt>60) {
			throw new BadFileFormatException("frequenza illegale a riga: "+riga1);
		}
		this.freq=new Frequenza(freqAsInt);
		tokens[2]=tokens[2].trim();
		String[] terzaParte = tokens[2].split("-");
		this.inizioServizio=LocalTime.parse(terzaParte[0].trim(), Formatters.timeFormatter);
		this.fineServizio=LocalTime.parse(terzaParte[1].trim(), Formatters.timeFormatter);
		
		
		//Linea Rossa, frequenza 5 minuti, 06:30-21:30
		// parsing e inizializzazione di nome, frequenza e orari
		//
		// ************ DA FARE ************ 
		// La prima riga specifica il nome della linea, la frequenza e gli orari di servizio al capolinea iniziale: 
		// il nome è preceduto dalla parola “Linea” seguita da spazi, la frequenza è un valore intero non negativo 
		// e non superiore a 60, ricompreso fra le due parole “frequenza” e “minuti”, separate da spazi, mentre gli 
		// orari della prima e dell’ultima corsa al capolinea iniziale sono espressi nel formato italiano short HH:MM,
		// separati fra loro da un trattino (senza spazi intermedi)
		// ********************************* 
}
	
	private void elaboraRiga2(String riga2) throws BadFileFormatException {
		this.fermate=new ArrayList<>();
		String nomeFermata;
		int minutiFermata;
		String[] tokens = riga2.split(",");
		for(int i=0;i<tokens.length;i++) {
			String[] partiDellaFermata = tokens[i].split("\\(");
			nomeFermata=partiDellaFermata[0].trim();
			partiDellaFermata[1]=partiDellaFermata[1].replace(')', ' ');
			minutiFermata=Integer.parseInt(partiDellaFermata[1].trim());
			fermate.add(new Fermata(nomeFermata, minutiFermata));
		}
		
		
		//Piazza Celestini (0), via Amendola (3), …, porta Fiorentina (25)
		// parsing e inizializzazione della lista delle fermate
		//
		// ************ DA FARE ************ 
		// La seconda riga elenca invece le varie fermate, indicando per ciascuna:
		// - il nome, eventualmente seguito da spazi per comodità di lettura
		// - la distanza temporale in minuti dal capolinea iniziale (un intero non negativo), indicata fra parentesi tonde
		// ********************************* 
	}

}
