package frutta.ui;

import java.time.LocalDate;

import frutta.controller.Controller;
import frutta.model.MappaFrutti;
import frutta.model.Rilevazione;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class AppVenditeFruttaMock extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Vendite di frutta - MOCK");
		
		MappaFrutti mappaFrutti = new MappaFrutti();
		// NB: le righe con NaN non vanno inserite come Rilevazioni
		//
		mappaFrutti.aggiungi("Albicocche", "nostrane", new Rilevazione(LocalDate.of(2025,3,24), 2.65));
		mappaFrutti.aggiungi("Albicocche", "nostrane", new Rilevazione(LocalDate.of(2025,3,31), 2.45));
		mappaFrutti.aggiungi("Albicocche", "nostrane", new Rilevazione(LocalDate.of(2025,4, 7), 2.45));
		mappaFrutti.aggiungi("Albicocche", "nostrane", new Rilevazione(LocalDate.of(2025,4,14), 2.05));
		//
		mappaFrutti.aggiungi("Ciliegie", "Bigarreau", new Rilevazione(LocalDate.of(2025,3,24), 8.00));
		mappaFrutti.aggiungi("Ciliegie", "Bigarreau", new Rilevazione(LocalDate.of(2025,3,31), 5.50));
		mappaFrutti.aggiungi("Ciliegie", "Bigarreau", new Rilevazione(LocalDate.of(2025,4, 7), 5.50));
		mappaFrutti.aggiungi("Ciliegie", "Bigarreau", new Rilevazione(LocalDate.of(2025,4,14), 4.63));
		//
		mappaFrutti.aggiungi("Ciliegie", "Celeste", new Rilevazione(LocalDate.of(2025,4,14), 7.50));
		//
		mappaFrutti.aggiungi("Ciliegie", "Dure", new Rilevazione(LocalDate.of(2025,3,31), 4.88));
		mappaFrutti.aggiungi("Ciliegie", "Dure", new Rilevazione(LocalDate.of(2025,4, 7), 4.63));
		mappaFrutti.aggiungi("Ciliegie", "Dure", new Rilevazione(LocalDate.of(2025,4,14), 4.25));
		//
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,2,10), 3.82));		
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,2,17), 3.15));
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,2,24), 2.85));
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3, 3), 2.75));
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3,10), 2.65));
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3,17), 2.54));
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3,24), 2.29));
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,3,31), 2.22));
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,4, 7), 2.22));
		mappaFrutti.aggiungi("Fragoline", "polpose", new Rilevazione(LocalDate.of(2025,4,14), 2.14));
		
		var controller = new Controller(mappaFrutti);
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 600, 400, Color.AQUAMARINE);
		stage.setScene(scene);stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}