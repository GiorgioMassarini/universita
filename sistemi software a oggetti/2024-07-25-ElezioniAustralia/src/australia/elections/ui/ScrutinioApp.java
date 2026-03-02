package australia.elections.ui;

import australia.elections.controller.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ScrutinioApp extends Application {

	public void start(Stage stage) {
		stage.setTitle("ScrutinioApp Dentinia");
		
		MainPane root = new MainPane(stage, new Controller());

		Scene scene = new Scene(root, 700, 450, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
