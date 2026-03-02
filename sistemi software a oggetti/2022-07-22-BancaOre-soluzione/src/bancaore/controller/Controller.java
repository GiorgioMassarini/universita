package bancaore.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import bancaore.model.BancaOre;
import bancaore.model.Cedolino;
import bancaore.model.VoceBancaOre;
import bancaore.model.VoceCedolino;


public class Controller {
	
	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	//--------------
	
	private Cedolino cedolino;
	private BancaOre banca;
	private Duration oreLavorate;
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);

	public Controller(BancaOre banca) {
		this.banca=banca;
		this.cedolino=banca.getCedolino();
		calcolaOreLavorate();
	}

	public List<VoceCedolino> getVociCedolino() {
		return Collections.unmodifiableList(cedolino.getVoci());
	}
	
	public List<VoceBancaOre> getVociBancaOre() {
		return banca.getVoci();
	}

	public String getNomeDipendente() {
		return cedolino.getNomeDipendente();
	}
	
	public String getMeseAnno() {
		return cedolino.getData().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.ITALY).toUpperCase()
				+ " " + cedolino.getData().getYear();
	}
	
	private String formattaDuration(Duration d) {
		int minuti = Math.abs(d.toMinutesPart());
		long ore   = d.toHours();
		return (ore<0? "-" : "") + (Math.abs(ore)<10 ? "0" : "") + Math.abs(ore) +":"+ (minuti<10 ? "0" : "") + minuti;
	}
	
	public String getSaldoOreIniziale() {
		return formattaDuration(cedolino.getSaldoOrePrecedente());
	}
	
	public String getSaldoOreFinale() {
		return formattaDuration(banca.getSaldoOrePrecedente());
	}
	
	private void calcolaOreLavorate() {
		oreLavorate = Duration.ofHours(0);
		for (VoceBancaOre v : this.getVociBancaOre()) {
			oreLavorate = oreLavorate.plus(v.getOreEffettuate());
		}
	}
	
	public String getTotaleOreLavorate() {
		return formattaDuration(oreLavorate);
	}
	
	public String getTotaleOrePreviste() {
		Duration d = Duration.ofHours(0);
		for (VoceBancaOre v : this.getVociBancaOre()) {
			d = d.plus(v.getOrePreviste());
		}
		return formattaDuration(d);
	}
	
	public String getTotalePermessiAOre() {
		return formattaDuration(banca.getTotalePermessiAOre());
	}

	public String getTotalePermessiGiornalieri() {
		return formattaDuration(banca.getTotalePermessi());
	}

	public String getTotaleOreCaricate() {
		Duration tot = banca.getTotalePermessi().plus(banca.getTotalePermessiAOre()).plus(oreLavorate);
		return formattaDuration(tot);
	}

	public String getDettagli(LocalDate dataCorrente) {
		List<VoceCedolino> dettaglioVoci = banca.getDettagli(dataCorrente);
		if(dettaglioVoci.isEmpty()) return "Non ci sono voci nel cedolino per la data del " + dateFormatter.format(dataCorrente);
		else
		return dettaglioVoci.stream().map(VoceCedolino::toString).collect(Collectors.joining("\n"));
	}
	
}
