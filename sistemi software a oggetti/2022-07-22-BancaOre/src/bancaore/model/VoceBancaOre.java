package bancaore.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

// Giorno		Prev.	Efft.	(Diff).	Saldo

public class VoceBancaOre {
	private Duration prev, eff, diff, saldo;
	private LocalDate giorno;
	
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);
		
	public VoceBancaOre(LocalDate giorno, Duration prev, Duration eff, Duration saldo) {
		if(giorno==null)
			throw new IllegalArgumentException("Giorno nullo nella voce banca ore");
		this.giorno=giorno;
		if(prev==null)
			throw new IllegalArgumentException("Durata prevista prev nulla nella voce banca ore");
		this.prev=prev;
		if(eff==null)
			throw new IllegalArgumentException("Durata effettiva nulla nella voce banca ore");
		this.eff=eff;
		if(saldo==null)
			throw new IllegalArgumentException("Saldo ore nullo nella voce banca ore");
		this.saldo=saldo;
		this.diff = eff.minus(prev);
	}

	public Duration getOrePreviste() {
		return prev;
	}

	public Duration getSaldo() {
		return saldo;
	}

	public Duration getOreEffettuate() {
		return eff;
	}

	public Duration getDifferenzaOre() {
		return diff;
	}

	private String formattaDuration(Duration d) {
		int minuti = Math.abs(d.toMinutesPart());
		long ore   = d.toHours();
		return (ore<0? "-" : "") + (Math.abs(ore)<10 ? "0" : "") + Math.abs(ore) +":"+ (minuti<10 ? "0" : "") + minuti;
	}
	
	@Override
	public String toString() {
		return dateFormatter.format(this.getGiorno()) + "\t prev=" + formattaDuration(prev) + ", eff=" + formattaDuration(eff) + 
				", diff=" + formattaDuration(diff)
				+ ", saldo=" +formattaDuration(saldo);
	}

	public LocalDate getGiorno() {
		return giorno;
	}
	
}
