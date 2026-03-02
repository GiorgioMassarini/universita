package timesheet.ui;

import java.time.Year;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import timesheet.controller.Controller;


public class TimeSheetAppMock extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Compilazione TimeSheet- MOCK");

		var projects = Map.of(
				"Lezioni di Analisi 2",		60,
				"Seminari di Analisi",		30,
				"Progetto Curve ellittiche", 900,
				"Progetto Solidi",			200
				);

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
