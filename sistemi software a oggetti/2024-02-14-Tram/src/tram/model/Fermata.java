package tram.model;

public record Fermata(String nome, int minutiDaCapolinea) {
	public Fermata {
		if (nome==null || nome.isBlank()) throw new IllegalArgumentException("Il nome della fermata non può essere nullo o vuoto");
		if (minutiDaCapolinea<0) throw new IllegalArgumentException("La distanza in minuti dal capolinea non può essere negativa");
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
