package australia.elections.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import australia.elections.model.Risultato;
import australia.elections.model.Scheda;
import australia.elections.model.Scrutinio;
import australia.elections.persistence.BadFileFormatException;
import australia.elections.persistence.MySchedeReader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller {
	
	protected List<Scheda> schedeLette;
	protected Scrutinio scrutinio;
	
	protected String schedeToString(List<Scheda> schede) {
		return schedeLette.stream()
				.map(Scheda::toString)
				.collect(Collectors.joining("------------"+System.lineSeparator(), 
											"ELENCO SCHEDE"+System.lineSeparator(), System.lineSeparator()));
	}
	
	public String leggiSchede(File file) throws IOException, BadFileFormatException {
		schedeLette = new MySchedeReader().leggiSchede(new FileReader(file));
		scrutinio = new Scrutinio(schedeLette);
		return schedeToString(schedeLette);
	}
	
	public Risultato scrutina() {
		return scrutinio.scrutina();
	}
	
	public long getTotaleVoti() {
		return scrutinio.getTotaleVoti();
	}
	
	public String getLog() {
		return scrutinio.getLog();
	}
	
	//--------------------------
	
	public static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
}
