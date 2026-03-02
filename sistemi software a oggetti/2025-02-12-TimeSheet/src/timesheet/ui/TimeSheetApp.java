package timesheet.ui;

import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import timesheet.controller.Controller;
import timesheet.persistence.BadFileFormatException;
import timesheet.persistence.MyProjectReader;


public class TimeSheetApp extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Compilazione TimeSheet");

		var projectReader = new MyProjectReader();
		
		Map<String,Integer> projects = null;
		try {
			projects =  projectReader.projectHours(new FileReader("Projects.txt"));
		} catch (BadFileFormatException | IOException e) {
			Controller.alert("Projects.txt: errore di lettura o formato del file errato", "Impossibile leggere i dati",
					"Dettagli:\n" + e.getMessage());
		}

		var controller = new Controller(projects, Year.now());
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 700, 434, Color.AQUAMARINE);
		stage.setScene(scene);stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
