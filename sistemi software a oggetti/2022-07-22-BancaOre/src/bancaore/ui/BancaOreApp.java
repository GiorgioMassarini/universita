package bancaore.ui;

import java.io.FileReader;
import java.io.IOException;

import bancaore.controller.Controller;
import bancaore.model.BancaOre;
import bancaore.model.Cedolino;
import bancaore.persistence.MyCedolinoReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class BancaOreApp extends Application {
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Banca ore");
		try {
				Cedolino m = new MyCedolinoReader().leggiCedolino(new FileReader("Cedolino.txt"));
				BancaOre b = new BancaOre(m);
				//
				Controller controller = new Controller(b);
				MainPane mainPanel = new MainPane(controller);
				//
				Scene scene = new Scene(mainPanel, 830, 500, Color.AQUAMARINE);
				stage.setScene(scene);
				stage.show();
				
		} catch (IOException e) {
			Controller.alert(
					"ERRORE DI I/O",
					"Errore di lettura: impossibile leggere i dati",
					e.getMessage());
		} catch (IllegalArgumentException e) {
			Controller.alert(
					"ERRORE IAE",
					"Formato dei file errato: impossibile leggere i dati",
					e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
