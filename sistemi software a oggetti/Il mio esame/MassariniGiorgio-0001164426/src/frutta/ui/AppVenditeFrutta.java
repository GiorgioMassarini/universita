package frutta.ui;

import java.io.FileReader;
import java.io.IOException;

import frutta.controller.Controller;
import frutta.model.MappaFrutti;
import frutta.persistence.BadFileFormatException;
import frutta.persistence.MyFruttaReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class AppVenditeFrutta extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Vendite di frutta");

		MappaFrutti mappaFrutta = null;
		
		try {
			mappaFrutta = new MyFruttaReader().leggiRilevazioniPrezzi(new FileReader("frutta.txt"));
		} catch (BadFileFormatException | IOException e) {
			Controller.alert("frutta.txt: errore di lettura o formato del file errato", "Impossibile leggere i dati",
					"Dettagli:\n" + e.getMessage());
		}
		
		var controller = new Controller(mappaFrutta);
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 600, 400, Color.AQUAMARINE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
