package tram.model;

public record Frequenza (int valore) {
		public Frequenza {
			if (valore<1 || valore>60) throw new IllegalArgumentException("La frequenza dev'essere compresa fra 1 e 60 minuti");
		}
}
