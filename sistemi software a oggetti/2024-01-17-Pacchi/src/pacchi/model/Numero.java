package pacchi.model;

public record Numero (int valore) {
		public Numero {
			if (valore<0) throw new IllegalArgumentException("Un pacco non può avere numero negativo");
		}
}
