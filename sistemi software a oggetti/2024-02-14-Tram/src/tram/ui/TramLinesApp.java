package tram.ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tram.controller.Controller;
import tram.model.Linea;
import tram.persistence.BadFileFormatException;
import tram.persistence.MyLinesReader;


public class TramLinesApp extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Dentinia Tram Lines");

		MyLinesReader linesReader = new MyLinesReader();	
		
		Set<Linea> linee = null;
		try {
			linee = linesReader.leggiLinee(new FileReader("Linee.txt"));
		} catch (BadFileFormatException | IOException e) {
			Controller.alert("Errore di lettura o formato del file errato", "Impossibile leggere i dati",
					"Dettagli:\n" + e.getMessage());
		}

		var controller = new Controller(linee);
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 550, 200, Color.AQUAMARINE);
		stage.setScene(scene);stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
