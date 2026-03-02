package bancaore.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SettimanaLavorativa {
	
	private Map<DayOfWeek, Duration> oreLavorative;
	private Duration oreSettimanali;
	public static final SettimanaLavorativa STANDARD = new SettimanaLavorativa(
			new String[] {"6H","9H","6H","9H","6H","0H","0H"} );
	
	/** Settimana lavorativa standard di 5 gg, lunedì-venerdì 
	 */
	public SettimanaLavorativa(Duration oreSettimanali) {
		if (oreSettimanali==null || oreSettimanali.isNegative() || oreSettimanali.compareTo(Duration.ofDays(7))>0) 
			throw new IllegalArgumentException("Ore settimanali nulle nel costruttore di SettimanaLavorativa");
		this.oreSettimanali=oreSettimanali;
		oreLavorative = new HashMap<>();
		long minuti = oreSettimanali.toMinutes();
		long minutiGiornalieri = minuti/5;
		for (DayOfWeek d : DayOfWeek.values()) {
			if(d==DayOfWeek.SATURDAY || d==DayOfWeek.SUNDAY) oreLavorative.put(d, Duration.ofHours(0));
			else oreLavorative.put(d, Duration.ofHours(minutiGiornalieri/60).plusMinutes(minutiGiornalieri%60));			
		}
	}
	
	/** Settimana lavorativa generica 
	 */
	public SettimanaLavorativa(final Map<DayOfWeek, Duration> oreLavorative) {
		if (oreLavorative==null || oreLavorative.size()!=7) 
			throw new IllegalArgumentException("Mappa ore lavorative nulla nel costruttore di SettimanaLavorativa");
		this.oreLavorative = oreLavorative;
		Duration sum = Duration.ofHours(0);
		this.oreSettimanali = oreLavorative.values().stream().reduce(sum, (s,d) -> s.plus(d));
	}
	
	/** Settimana lavorativa generica, argomenti = 7 stringhe della forma nHnM 
	 */
	public SettimanaLavorativa(String tracciaOre[]) {
		if (tracciaOre==null || tracciaOre.length!=7) 
			throw new IllegalArgumentException("Traccia ore errata nel costruttore di SettimanaLavorativa");
		oreLavorative = new HashMap<>();
		int i=0;
		for (DayOfWeek d : DayOfWeek.values()) {
			oreLavorative.put(d, Duration.parse("PT"+tracciaOre[i]));
			i++;			
		}
		Duration sum = Duration.ofHours(0);
		this.oreSettimanali = oreLavorative.values().stream().reduce(sum, (s,d) -> s.plus(d));
	}
	
	public Duration getOreLavorative(DayOfWeek day){
		return oreLavorative.get(day);
	}

	public Duration getOreSettimanali() {
		return oreSettimanali;
	}

	@Override
	public String toString() {
		return "SettimanaLavorativa [oreLavorative=" + oreLavorative + ", oreSettimanali=" + oreSettimanali + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(oreLavorative, oreSettimanali);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		SettimanaLavorativa other = (SettimanaLavorativa) obj;
		return Objects.equals(oreLavorative, other.oreLavorative)
				&& Objects.equals(oreSettimanali, other.oreSettimanali);
	}	
	
}
