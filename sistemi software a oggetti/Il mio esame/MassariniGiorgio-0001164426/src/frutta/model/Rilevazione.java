package frutta.model;

import java.time.LocalDate;
import java.util.Objects;

public class Rilevazione implements Comparable<Rilevazione> {
	
	private LocalDate data;
	private double prezzo;

	public Rilevazione(LocalDate data, double prezzo) {
		Objects.requireNonNull(data,  "L'orario della fermata non può essere nullo");
		if (prezzo<0) throw new IllegalArgumentException("Il prezzo non può essere negativo");
		if (!Double.isFinite(prezzo)) throw new IllegalArgumentException("Il prezzo non è un valore finito");
		this.data=data;
		this.prezzo=prezzo;
	}	
	
	public LocalDate data() {
		return data;
	}
	
	public double prezzo() {
		return prezzo;
	}
	
	@Override
	public String toString() {
		return " Rilevazione: " + Formatters.itDateFormatter.format(data) + Formatters.itPriceFormatter.format(prezzo);
	}

	@Override
	public int compareTo(Rilevazione other) {
		return this.data.compareTo(other.data);
	}

	@Override
	public int hashCode() {
		return Objects.hash(data);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rilevazione other = (Rilevazione) obj;
		return Objects.equals(data, other.data);
	}
	
	

}
