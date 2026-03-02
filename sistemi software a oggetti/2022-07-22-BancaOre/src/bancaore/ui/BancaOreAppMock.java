package bancaore.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import bancaore.controller.Controller;
import bancaore.model.BancaOre;
import bancaore.model.Cedolino;
import bancaore.model.SettimanaLavorativa;
import bancaore.model.VoceCedolino;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BancaOreAppMock extends Application {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Banca ore - MOCK");

		Controller controller = new Controller(fakeBank());
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 830, 500, Color.AQUAMARINE);
		stage.setScene(scene);
		stage.show();
	}

	private BancaOre fakeBank() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,40), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,11),LocalTime.of(6,42), LocalTime.of(7,30), Optional.empty());
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,14),LocalTime.of(13,40), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm2);
		m.aggiungi(vm3);
		m.aggiungi(vm1);
		return new BancaOre(m);
	}

	public static void main(String[] args) {
		launch(args);
	}
	

}
