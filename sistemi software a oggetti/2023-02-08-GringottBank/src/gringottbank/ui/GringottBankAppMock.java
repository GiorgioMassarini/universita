package gringottbank.ui;

import java.util.List;

import gringottbank.controller.Controller;
import gringottbank.model.Ceiling;
import gringottbank.model.Coin;
import gringottbank.model.CoinAmount;
import gringottbank.model.GringottAtm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GringottBankAppMock extends Application {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Gringott ATM - MOCK");

		Controller controller = new Controller(new GringottAtm(), creaListaFake());
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 720, 500, Color.AQUAMARINE);
		stage.setScene(scene);
		stage.show();
	}
	
	private List<Ceiling> creaListaFake() {
		return List.of(
				new Ceiling("Harry",	new CoinAmount().plus(Coin.GALLEON, 20)),
				new Ceiling("Hermione",	new CoinAmount().plus(Coin.GALLEON, 19).plus(Coin.KNUT, 20)),
				new Ceiling("Enrico",	new CoinAmount().plus(Coin.GALLEON, 200)),
				new Ceiling("Ambra",	new CoinAmount().plus(Coin.GALLEON, 100).plus(Coin.KNUT, 20)),
				new Ceiling("Roberta",	new CoinAmount().plus(Coin.GALLEON, 100).plus(Coin.KNUT, 28)),
				new Ceiling("Ron",		new CoinAmount().plus(Coin.GALLEON, 12).plus(Coin.SICKLE, 10).plus(Coin.KNUT, 6))
				);
	}

	public static void main(String[] args) {
		launch(args);
	}
	

}
