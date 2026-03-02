package bancaore.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class VoceCedolino {
	private LocalDate data;
	private LocalTime oraEntrata, oraUscita;
	private Optional<Causale> causale;
	
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
		
	public VoceCedolino(LocalDate data, LocalTime oraEntrata, LocalTime oraUscita, Optional<Causale> causale) {
		if(data==null || oraEntrata==null || oraUscita==null ||causale==null)
			throw new IllegalArgumentException("Argomento nullo nella voce cedolino");
		if(oraEntrata.isAfter(oraUscita))
			throw new IllegalArgumentException("L'ora di uscita precede l'ora di entrata");
		this.data = data;
		this.oraEntrata = oraEntrata;
		this.oraUscita = oraUscita;
		this.causale = causale;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public Optional<Causale> getCausale() {
		return causale;
	}

	public LocalTime getOraEntrata() {
		return oraEntrata;
	}

	public LocalTime getOraUscita() {
		return oraUscita;
	}

	@Override
	public String toString() {
		return dateFormatter.format(this.data) + "\t" + timeFormatter.format(oraEntrata) + "-" + timeFormatter.format(oraUscita) + 
				", " + (causale.isPresent() ? causale.get() : "lavoro");
	}

	@Override
	public int hashCode() {
		return Objects.hash(causale, data, oraEntrata, oraUscita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		VoceCedolino other = (VoceCedolino) obj;
		return Objects.equals(causale, other.causale) && Objects.equals(data, other.data)
				&& Objects.equals(oraEntrata, other.oraEntrata) && Objects.equals(oraUscita, other.oraUscita);
	}

}
