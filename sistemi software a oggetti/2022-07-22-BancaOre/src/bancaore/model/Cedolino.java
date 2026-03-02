package bancaore.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Cedolino {

	private List<VoceCedolino> voci;
	private String nomeDipendente;
	private LocalDate data;
	private SettimanaLavorativa settimana;
	private Duration saldoPrecedente;
	
	public Cedolino(String nome, LocalDate data, SettimanaLavorativa settimana, Duration saldoPrecedente) {
		if (nome==null) throw new IllegalArgumentException("Nome dipendente nullo");
		this.nomeDipendente=nome;
		if (data==null) throw new IllegalArgumentException("Data nulla");
		this.data=data;
		if (settimana==null) throw new IllegalArgumentException("Settimana lavorativa nulla");
		this.settimana=settimana;
		if (saldoPrecedente==null) throw new IllegalArgumentException("Saldo precedente nullo");
		this.saldoPrecedente=saldoPrecedente;
		//
		voci = new ArrayList<>();
	}
	
	public void aggiungi(VoceCedolino m) {
		//
		if(m==null) {
			throw new IllegalArgumentException("m è null");
		}
		Comparator<VoceCedolino> cmp = Comparator.comparing(VoceCedolino::getData) //primo criterio di ordinamento
									   .thenComparing(VoceCedolino::getOraEntrata); //secondo criterio ordinamento
		voci.add(m);
		voci.sort(cmp);
		//
		// la lista dev'essere sempre ordinata per data e in subordine per ora di entrata delle varie voci
		// *** DA FARE *** 
	}

	public List<VoceCedolino> getVoci() {
		return Collections.unmodifiableList(voci);
	}

	public String getNomeDipendente() {
		return nomeDipendente;
	}

	public LocalDate getData() {
		return data;
	}
	
	public SettimanaLavorativa getSettimanaLavorativa() {
		return settimana;
	}

	public Duration getSaldoOrePrecedente() {
		return saldoPrecedente;
	}

	@Override
	public String toString() {
		return "Cedolino [voci=" + voci + ", nomeDipendente=" + nomeDipendente + ", data=" + data + ", settimana="
				+ settimana + ", saldoPrecedente=" + saldoPrecedente + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, nomeDipendente, saldoPrecedente, settimana, voci);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Cedolino other = (Cedolino) obj;
		return Objects.equals(data, other.data) && Objects.equals(nomeDipendente, other.nomeDipendente)
				&& Objects.equals(saldoPrecedente, other.saldoPrecedente) && Objects.equals(settimana, other.settimana)
				&& Objects.equals(voci, other.voci);
	}

}
