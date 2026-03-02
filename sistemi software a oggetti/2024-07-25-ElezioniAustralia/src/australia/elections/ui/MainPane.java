package australia.elections.ui;

import java.io.IOException;

import australia.elections.controller.Controller;
import australia.elections.model.Risultato;
import australia.elections.persistence.BadFileFormatException;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class MainPane extends BorderPane {

	private TextArea areaSchedeLette, log, areaWinner;
	private Controller controller;
	private Button calcola, carica, showLog;
	private Stage stage;
	
	public MainPane(Stage stage, Controller controller) {
		this.stage = stage;
		this.controller = controller;

		VBox leftPane = new VBox();
		leftPane.setAlignment(Pos.TOP_LEFT);
			carica = new Button("Carica file");
			carica.setOnAction(this::carica);
		leftPane.getChildren().addAll(carica);
			areaSchedeLette = new TextArea();
			areaSchedeLette.setEditable(false);
			areaSchedeLette.setPrefWidth(200);
			areaSchedeLette.setPrefHeight(225);
			areaSchedeLette.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
			areaSchedeLette.setText("");
		leftPane.getChildren().add(areaSchedeLette);
			calcola = new Button("Calcola");
			calcola.setOnAction(this::calcolaVincitore);
			calcola.setDisable(true);
		leftPane.getChildren().addAll(calcola);
			areaWinner = new TextArea();
			areaWinner.setEditable(false);
			areaWinner.setPrefWidth(200);
			areaWinner.setPrefHeight(150);
			areaWinner.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
			areaWinner.setText("");
		leftPane.getChildren().add(areaWinner);
		this.setLeft(leftPane);

		VBox rightPane = new VBox();
		rightPane.setAlignment(Pos.TOP_LEFT);
			showLog = new Button("Show log");
			showLog.setOnAction(this::showLog);
			showLog.setDisable(true);
		leftPane.getChildren().addAll(showLog);
			log = new TextArea();
			log.setEditable(false);
			log.setPrefWidth(450);
			log.setPrefHeight(400);
			log.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
			log.setText("");
			rightPane.getChildren().addAll(showLog,log);
		this.setRight(rightPane);
	}

	private void calcolaVincitore(ActionEvent event) {
		Risultato result = controller.scrutina();
		showLog.setDisable(false);
		areaWinner.setText(result.toString());
		
		// **** DA FARE ****
		// Deve chiedere al Controller di effettuare lo scrutinio, abilitare il pulsante “Show log” 
		// e mostrare il vincitore nell’apposita area di testo in basso a sinistra
	}

	private void carica(ActionEvent event) {
		var chooser = new FileChooser();
		chooser.setTitle("Aprire file con le schede depositate nell'urna");
		chooser.getExtensionFilters().addAll(new ExtensionFilter("File di testo", "*.txt"));
		var selectedFile = chooser.showOpenDialog(stage);
		if (selectedFile==null) {
			Controller.alert("Selezione interrotta", "Non è stato selezionato alcun file", "Impossibile proseguire");
			return;
		}
		try {
			String schedeAsString = controller.leggiSchede(selectedFile);
			calcola.setDisable(false);
			this.areaSchedeLette.setText(schedeAsString);
			this.log.clear();
			this.areaWinner.clear();
		} catch (IOException e1) {
			Controller.alert("Errore apertura file", "Impossibile aprire il file",
					"Impossibile aprire il file " + selectedFile + " o leggere da esso");
			System.err.println(e1);
			return;
		}
		catch (BadFileFormatException e2) {
			Controller.alert("Errore di formato", "Il file contiene errori", e2.getMessage());
			System.err.println(e2);
			return;
		}
	}
	
	private void showLog(ActionEvent event) {
		String logAsString = controller.getLog();
		log.setText(logAsString);
		
		// **** DA FARE ****
		// Deve chiedere al Controller i dettagli del log e mostrarli nell’area di testo a destra
	}

}
