package tram.ui;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tram.controller.Controller;
import tram.model.Fermata;
import tram.model.Formatters;
import tram.model.Linea;
import tram.model.Direzione;



public class MainPane extends BorderPane {
	
	private Controller controller;
	
	private TextArea outputArea;
	private TextField txtOrarioDesiderato;
	private RadioButton avanti, indietro;
	private Button cerca;
	private ComboBox<Linea> comboLinee;
	private ComboBox<Fermata> comboFermate;
	private ToggleGroup tg;
	private Direzione direzione;

	public MainPane(Controller controller) {
		this.controller=controller;
		//
		HBox topBox = new HBox();
		topBox.setPrefWidth(500);
			//
			topBox.setPadding(new Insets(10, 10, 10, 10));
			comboLinee = new ComboBox<>();
			comboLinee.setItems(FXCollections.observableArrayList(controller.getLinee()));
			//
			// ************ DA FARE ************ 
			// creazione e popolamento comboLinee
			// ********************************* 		
			//
			comboLinee.setTooltip(new Tooltip("Scegliere la linea"));
			comboLinee.setPrefWidth(130);
			comboFermate = new ComboBox<>();
			comboFermate.setPrefWidth(150);
			comboFermate.setTooltip(new Tooltip("Scegliere la fermata desiderata"));
			comboLinee.setOnAction(ev -> popolaFermate(comboLinee.getValue()));
			topBox.getChildren().addAll(new Label("Linee  "), comboLinee, new Label("  Fermate  "), comboFermate);
			//
			avanti = new RadioButton("Andata");
			indietro = new RadioButton("Ritorno");
			topBox.getChildren().addAll(new Label("   "), avanti, new Label("  "), indietro);
			tg = new ToggleGroup();
			avanti.setToggleGroup(tg);
			indietro.setToggleGroup(tg);
			avanti.setOnAction(ev -> setDirection(Direzione.ANDATA));
			indietro.setOnAction(ev -> setDirection(Direzione.RITORNO));
			avanti.setSelected(true); 
			direzione = Direzione.ANDATA;
		this.setTop(topBox);
		HBox centerBox = new HBox();
			centerBox.setPrefWidth(550);
			centerBox.setPadding(new Insets(10, 10, 10, 10));
			txtOrarioDesiderato = new TextField();
			txtOrarioDesiderato.setPrefWidth(50);
			txtOrarioDesiderato.setTooltip(new Tooltip("Inserire l'orario nella forma italiana HH:MM"));
			txtOrarioDesiderato.setText("10:30");
			cerca = new Button("Prossima corsa");
			cerca.setOnAction(ev -> cerca());
			centerBox.getChildren().addAll(new Label("Orario desiderato  "), txtOrarioDesiderato, new Label("      "), cerca);
		this.setCenter(centerBox);	
		HBox bottomBox = new HBox();	
			outputArea = new TextArea();
			outputArea.setPrefSize(550,150);
			outputArea.setFont(Font.font("Courier New", FontWeight.NORMAL, 14));
			outputArea.setEditable(false);
			bottomBox.getChildren().addAll(outputArea);
		this.setBottom(bottomBox);
	}

	private void popolaFermate(Linea value) {
		Linea selezionata=comboLinee.getValue();
		if(selezionata==null) {
			Controller.alert("Linea non selezionata", "Occorre prima selezionare la linea", 
							 "Selezionale la linea desiderata dalla combo in alto a sinistra");
			return;
		}
		comboFermate.setItems(FXCollections.observableArrayList(selezionata.getFermate()));
		//
		// ************ DA FARE ************ 
		// recupera la linea selezionata e, se non è nulla, popola la combo fermate con le fermate di tale linea
		// altrimenti emette un messaggio d'errore "linea inesistente" tramite il metodo alert del Controller
		// ********************************* 
		//
	}

	private void cerca() {
		Linea lineaSelezionata=comboLinee.getValue();
		if(lineaSelezionata==null) {
			Controller.alert("Linea non selezionata", "Occorre prima selezionare la linea", 
							 "Selezionare la linea desiderata dalla combo in alto a sinistra");
			return;
		}
		Fermata fermataSelezionata = comboFermate.getValue();
		if(fermataSelezionata==null) {
			Controller.alert("Fermata non selezionata", "Occorre prima selezionare la fermata", 
					 "Selezionare la fermata desiderata dalla combo in alto");
			return;
		}
		String orarioAsString = txtOrarioDesiderato.getText();
		LocalTime orario=null;
		try {
			orario = LocalTime.parse(orarioAsString, Formatters.timeFormatter);
		}catch(DateTimeParseException e) {
			Controller.alert("Errore nella lettura dell'orario", "Orario non segue il formato HH:MM", 
					 "Dettagli: \n"+e.getMessage());
			return;
		}
		String txtResult="";
		LocalTime result=controller.prossimaCorsa(lineaSelezionata.getNome(), 
												  fermataSelezionata.toString(),
												  direzione, orario);
		txtResult=txtResult+"orario richiesto "+orario.toString()+"\n"+
				 "prossima corsa prevista alle "+result.toString();
		outputArea.setText(txtResult);
		
		//
		// ************ DA FARE ************
		// 1) preliminarmente, recupera la linea selezionata dalla combo linee: se questa è nulla, si emette un messaggio d’errore 
		//    tramite il metodo alert del Controller e si ritorna senza fare nulla
		// 2) poi, recupera la fermata selezionata dalla combo fermate: anche in questo caso, se essa è nulla si emette un messaggio 
		//    d’errore tramite il metodo alert del Controller e si ritorna senza fare nulla
		// 3) indi, recupera l’orario dal campo di testo apposito e ne fa il parsing: in caso di errore occorre emettere un apposito 
		//    messaggio tramite il metodo alert del Controller e ritornare senza fare nulla
		// 4) infine, tramite il controller, provvede al calcolo dell’orario della prossima corsa e sintetizza gli appositi messaggi 
		//    da emettere sull’area di testo.
		// ********************************* 
		//
	}

	private void setDirection(Direzione dir) {
		this.direzione = dir;
	}
	
}
