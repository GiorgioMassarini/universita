package gringottbank.ui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import gringottbank.controller.Controller;
import gringottbank.model.Ceiling;
import gringottbank.model.GringottAtm;
import gringottbank.persistence.BadFileFormatException;
import gringottbank.persistence.MyCeilingsReader;
import gringottbank.persistence.CeilingsReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GringottBankApp extends Application {

	public static String[] listing(File dir, String extension) {
		if (dir.isDirectory()) { 
			return dir.list((f,name) -> name.endsWith(extension));
		}
		throw new IllegalArgumentException(dir + " is not a directory");
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Gringott ATM");
		
		CeilingsReader cReader = new MyCeilingsReader();		
		List<Ceiling> ceilingList = null;
		Controller controller = null;
		try {
			ceilingList= cReader.readCeilings(new FileReader("Ceilings.txt"));
			controller = new Controller(new GringottAtm(), ceilingList);
		} catch (IOException e) {
			Controller.alert(
					"Errore di I/O",
					"Impossibile leggere i dati",
					"Dettagli: " + e.getMessage());
		} catch (BadFileFormatException e) {
			Controller.alert(
					"Errore di lettura",
					"formato del file errato",
					"Dettagli: " + e.getMessage());
		}
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 500, 300, Color.AQUAMARINE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
