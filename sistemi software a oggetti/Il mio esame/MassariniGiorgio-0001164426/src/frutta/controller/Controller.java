package frutta.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.SortedSet;

import frutta.model.MappaFrutti;
import frutta.model.Rilevazione;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class Controller {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	//--------------

	private MappaFrutti mappaFrutti;
		
	public Controller(MappaFrutti mappaFrutti) {
		Objects.requireNonNull(mappaFrutti, "Mappa frutti nulla nel construttore del Controller");
		if (mappaFrutti.isEmpty()) throw new IllegalArgumentException("Mappa frutti vuota nel construttore del Controller");
		this.mappaFrutti = mappaFrutti;
	}

	public MappaFrutti mappaFrutti() {
		return mappaFrutti;
	}

	public OptionalDouble prezzoMedio(String frutta) {
		return mappaFrutti.prezzoMedio(frutta);
	}
	
	public OptionalDouble prezzoMinimo(String frutta) {
		return mappaFrutti.prezzoMinimo(frutta);
	}
	
	public OptionalDouble prezzoMassimo(String frutta) {
		return mappaFrutti.prezzoMassimo(frutta);
	}
	
	public OptionalDouble prezzoMedio(String frutta, String sottotipo) {
		return mappaFrutti.prezzoMedio(frutta, sottotipo);
	}
	
	public OptionalDouble prezzoMinimo(String frutta, String sottotipo) {
		return mappaFrutti.prezzoMinimo(frutta, sottotipo);
	}
	
	public OptionalDouble prezzoMassimo(String frutta, String sottotipo) {
		return mappaFrutti.prezzoMassimo(frutta, sottotipo);
	}
	
	public SortedSet<String> tipiFrutta(){
		return mappaFrutti.tipiFrutta();
	}
	
	public SortedSet<String> sottotipi(String frutta) {
		return mappaFrutti.sottotipi(frutta);
	}
	
	public SortedSet<LocalDate> date(){
		return mappaFrutti.date();
	}
	
	public List<Rilevazione> rilevazioni(String tipoFrutta, String sottotipo){
		return mappaFrutti.rilevazioniPerSottotipo(tipoFrutta, sottotipo);
	}
}
