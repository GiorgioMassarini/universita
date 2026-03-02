package bancaore.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BancaOre {

	private Cedolino cedolino;
	private Duration saldoOrePrecedente, totalePermessiAOre, totalePermessi;
	private List<VoceBancaOre> voci;
	
	public BancaOre(Cedolino cedolino) {
		if (cedolino==null) throw new IllegalArgumentException("cedolino nullo");
		this.cedolino = cedolino;
		this.saldoOrePrecedente = cedolino.getSaldoOrePrecedente();
		this.totalePermessi=Duration.ofHours(0);
		this.totalePermessiAOre=Duration.ofHours(0);
		voci = popolaLista();
	}
	
	private List<VoceBancaOre> popolaLista() {
		List<VoceBancaOre> listaVoci = new ArrayList<>();
		var primoGiornoDelMese  = cedolino.getData().withDayOfMonth(1);
		var ultimoGiornoDelMese = cedolino.getData().with(TemporalAdjusters.lastDayOfMonth());
		for (LocalDate d=primoGiornoDelMese; !d.isAfter(ultimoGiornoDelMese); d=d.plusDays(1)) {
			listaVoci.add(sintetizzaVoceBancaOre(d));
		}
		return listaVoci;
	}
	

	private VoceBancaOre sintetizzaVoceBancaOre(LocalDate d) {
		Duration orePreviste = cedolino.getSettimanaLavorativa().getOreLavorative(d.getDayOfWeek());
		Duration oreEffettuate = Duration.ofHours(0);
		Duration diffOre = Duration.ofHours(0).minus(orePreviste);
		saldoOrePrecedente = saldoOrePrecedente.plus(diffOre);
		
		for (VoceCedolino v : cedolino.getVoci()) {
			if(v.getData().equals(d)) {
				Duration durata = Duration.between(v.getOraEntrata(), v.getOraUscita());
				if(!v.getCausale().isPresent()) { // sono ore lavorative
					oreEffettuate = oreEffettuate.plus(durata);
					saldoOrePrecedente = saldoOrePrecedente.plus(durata);
				}
				else switch(v.getCausale().get()) {
					case RIPOSO_COMPENSATIVO: 
						totalePermessi = totalePermessi.plus(durata);
						break;
					case RIPOSO_COMPENSATIVO_A_ORE: 
						totalePermessiAOre = totalePermessiAOre.plus(durata);
						break;
					case PAUSA_PRANZO: continue; 
				}
			}
		}
		return new VoceBancaOre(d, orePreviste, oreEffettuate, saldoOrePrecedente);
	}

	public List<VoceCedolino> getDettagli(LocalDate d) {
		var result = new ArrayList<VoceCedolino>();
		for (VoceCedolino v : cedolino.getVoci())
			if(v.getData().equals(d)) result.add(v);
		return result;
	}
	
	public Duration getSaldoOrePrecedente() {
		return saldoOrePrecedente;
	}

	public Cedolino getCedolino() {
		return cedolino;
	}

	public List<VoceBancaOre> getVoci() {
		return Collections.unmodifiableList(voci);
	}

	@Override
	public String toString() {
		return "BancaOre [cedolino=" + cedolino + ", saldoOre=" + saldoOrePrecedente + ", voci=" + voci + "]";
	}

	public Duration getTotalePermessiAOre() {
		return totalePermessiAOre;
	}

	public Duration getTotalePermessi() {
		return totalePermessi;
	}
		
}


