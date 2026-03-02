package australia.elections.ui;

import australia.elections.controller.ControllerMock;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ScrutinioAppMock extends ScrutinioApp {

	@Override
	public void start(Stage stage) {
		stage.setTitle("ScrutinioApp Dentinia - MOCK");
		MainPane root = new MainPane(stage, new ControllerMock());

		Scene scene = new Scene(root, 700, 450, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
