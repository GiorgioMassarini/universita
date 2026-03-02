package tram.controller;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import tram.model.Linea;
import tram.model.Analizzatore;
import tram.model.Direzione;
import tram.model.Fermata;


public class Controller {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	private Set<Linea> linee;
	private Analizzatore analyzer;
		
	//--------------
	
	public Controller(Set<Linea> linee) {
		if(linee==null) throw new IllegalArgumentException("Lista linee nulla nel construttore del Controller!");
		this.linee = linee;
		try {
			this.analyzer = new Analizzatore(linee);
		}
		catch(IllegalArgumentException e) {
			Controller.alert("Errore nella creazione dell'Analyzer", "Insieme delle linee nullo o vuoto",
				"Dettagli:\n" + e.getMessage());
		}
	}

	public Set<Linea> getLinee() {
		return linee;
	}
	
	public List<Fermata> fermateLinea(Linea l){
		var optLinea = analyzer.getLinea(l.getNome());
		if (optLinea.isEmpty()) throw new IllegalArgumentException("Linea inesistente");
		else return optLinea.get().getFermate();
	}
	
	public LocalTime prossimaCorsa(String nomeLinea, String nomeFermata, Direzione dir, LocalTime orario) {
		return analyzer.prossimoPassaggio(nomeLinea, nomeFermata, dir, orario);
	} 
	
}
