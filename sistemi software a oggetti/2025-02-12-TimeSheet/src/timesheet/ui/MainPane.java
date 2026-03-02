package timesheet.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import timesheet.controller.Controller;
import timesheet.model.Formatters;


public class MainPane extends BorderPane {
	
	private Controller controller;
	
	private TextArea outputArea;
	private TextField txtOreTotaliProgetto, txtOreLavorate;
	private DatePicker pickerData;
	private Button applica, dettagli, sintesi;
	private Spinner<String> spinnerProgetti;
	private Label sab, dom;

	@SuppressWarnings("unused")
	public MainPane(Controller controller) {
		this.controller=controller;
		//
		HBox topBox = new HBox();
			topBox.setPadding(new Insets(10, 10, 10, 10));			
			sab = new Label("Lavoro sabato: "   + controller.isSaturdayWorkingDay());
			dom = new Label("Lavoro domenica: " + controller.isSundayWorkingDay());
			topBox.getChildren().addAll(sab, new Label("     "), dom, new Label("     "), new Label("Max ore giornaliere: " + String.valueOf(controller.getMaxHoursPerDay())));			
		this.setTop(topBox);
		
		VBox centerBox = new VBox();
			centerBox.setPadding(new Insets(0, 10, 10, 10));
			HBox row1 = new HBox();
			row1.setPadding(new Insets(10, 10, 10, 0));

			spinnerProgetti = new Spinner<>(FXCollections.observableArrayList(controller.getProjectNames()));
			spinnerProgetti.setTooltip(new Tooltip("Scegliere il progetto"));
			spinnerProgetti.setPrefWidth(180);
			spinnerProgetti.valueProperty().addListener( (changed, oldval, newval) ->  aggiornaMaxOreProgetto(newval));
			
			txtOreTotaliProgetto = new TextField();
			txtOreTotaliProgetto.setEditable(false);
			txtOreTotaliProgetto.setAlignment(Pos.CENTER_RIGHT);
			txtOreTotaliProgetto.setPrefWidth(35);
			txtOreTotaliProgetto.setTooltip(new Tooltip("Ore totali previste nel progetto"));
			txtOreTotaliProgetto.setText(String.valueOf(controller.getMaxHoursPerProject(spinnerProgetti.getValue())));

			row1.getChildren().addAll(new Label("Progetto "), spinnerProgetti, new Label("   Ore previste "), txtOreTotaliProgetto);
			

			HBox row2 = new HBox();
			row2.setPadding(new Insets(0, 10, 10, 0));
			
			pickerData = new DatePicker(LocalDate.now());
			pickerData.setPrefWidth(100);
			pickerData.setTooltip(new Tooltip("Scegliere il giorno da modificare"));
			pickerData.setOnAction(ev -> giorniCorretti());
			
			txtOreLavorate = new TextField();
			txtOreLavorate.setPrefWidth(50);
			txtOreLavorate.setTooltip(new Tooltip("Inserire le ore lavorate nella forma HH:MM"));
			txtOreLavorate.setAlignment(Pos.CENTER);
			txtOreLavorate.setText("00:00");
			
			row2.getChildren().addAll(new Label("Data "), pickerData, new Label("   Ore lavorate "), txtOreLavorate);
			
			HBox row3 = new HBox();
			row3.setPadding(new Insets(0, 10, 10, 0));
			
			applica = new Button("Applica");
			applica.setFont(Font.font("Arial", FontWeight.BOLD, 11));
			applica.setOnAction(ev -> applica());
			
			dettagli = new Button("Dettaglio ore");
			dettagli.setFont(Font.font("Arial", FontWeight.BOLD, 11));
			dettagli.setOnAction(ev -> mostraDettaglioOre());
			
			sintesi = new Button("Sintesi ore");
			sintesi.setFont(Font.font("Arial", FontWeight.BOLD, 11));
			sintesi.setOnAction(ev -> mostraSintesiOre());
			
			row3.getChildren().addAll(new Label("    "), applica, new Label("    "), dettagli, new Label("    "), sintesi);
			
			centerBox.getChildren().addAll(row1, row2, row3);
		this.setCenter(centerBox);	
		
		HBox bottomBox = new HBox();
			bottomBox.setPadding(new Insets(0, 10, 10, 10));
			outputArea = new TextArea();
			outputArea.setPrefSize(700, 270);
			outputArea.setFont(Font.font("Courier New", FontWeight.NORMAL, 14));
			outputArea.setEditable(false);
			bottomBox.getChildren().addAll(outputArea);
		this.setBottom(bottomBox);
	}

	private void applica() {
		String OreLavorateAsSting = txtOreLavorate.getText();
		LocalTime OreLavorate =null;
		try {
			OreLavorate=LocalTime.parse(OreLavorateAsSting, Formatters.timeFormatter);
		}catch(DateTimeParseException e) {
			Controller.alert("ERRORE", "Formato ore lavorate errato", "Inserisci le ore lavorate nel formato hh:mm");
			return;
		}
		if(!giorniCorretti()) {
			Controller.alert("Giorno non lavorativo","Non è possibile inserire ore in questo giorno",
					 		 "Le impostazioni non ammettono il lavoro nel giorno selezionato");
			return;
		}
		int minutiLavoratiAsInt = OreLavorate.getMinute();
		if(minutiLavoratiAsInt>controller.getMaxHoursPerDay()*60) {
			Controller.alert("Violazione del limite orario giornaliero",
					 		 "Sono state superate le ore massime giornaliere ("+controller.getMaxHoursPerDay()+")",
					 		 "Totale giornaliero: "+OreLavorate);
			return;
		}
		LocalDate giornoSelezioanto = pickerData.getValue();
		String projectName = spinnerProgetti.getValue();
		controller.setWorkedTimePerProject(giornoSelezioanto, minutiLavoratiAsInt, projectName);
		outputArea.setText("Inserite "+OreLavorate+" per il "+ giornoSelezioanto.format(Formatters.dateFormatter));
		
		/* Il metodo deve:
		 * - recuperare le ore lavorate, controllarne il formato e, se tutto risulta corretto, verificare tramite giorniCorretti 
		 *   che la data selezionata sia un giorno lavorativo valido
		 * - solo in caso positivo della precedente verifica, verificare che, aggiungendo le ore attuali, non si superi il massimo 
		 *   numero di ore lavorative giornaliere
		 * - solo se anche questa verifica è positiva, recuperare dallo Spinner il nome del progetto e impostare le ore lavorate 
		 *   tramite il metodo setWorkedTimePerProject del Controller, emettendo nell’area di testo un apposito messaggio di conferma 
		 *   (vedere figura nel testo).
		 * In tutti i casi di errore sopra evidenziati, il metodo deve emettere tramite alert un apposito, dettagliato messaggio 
		 * d’errore all’utente.
		 * */		
		// *****************************
		// DA FARE
		// *****************************		
	}
	
	private void mostraSintesiOre() {
		outputArea.setText(controller.annualSynthesis());
	}

	private void mostraDettaglioOre() {
		outputArea.setText(controller.annualDetail());
	}

	private boolean giorniCorretti() {
		LocalDate giornoSelezioanto = pickerData.getValue();
		DayOfWeek giornoDellaSettimana = giornoSelezioanto.getDayOfWeek();
		if(giornoDellaSettimana.getValue()<6) {
			return true;
		}
		if(giornoDellaSettimana.getValue()==6 && controller.isSaturdayWorkingDay().getValue()) {
			return true;
		}
		if(giornoDellaSettimana.getValue()==7 && controller.isSundayWorkingDay().getValue()) {
			return true;
		}
		Controller.alert("Giorno non lavorativo","Non è possibile inserire ore in questo giorno",
						 "Le impostazioni non ammettono il lavoro nel giorno selezionato");
		return false;
		/* Deve verificare se il giorno selezionato sia lavorativo, in base alle impostazioni correnti dell’applicazione.
		 * Più precisamente:
			- i giorni da lunedì a venerdì sono sempre lavorativi
			- il sabato e la domenica lo sono solo se ciò è permesso dall’impostazione iniziale del costruttore
		 * Nel caso il giorno selezionato risulti non lavorativo, il metodo deve emettere un messaggio d’errore tramite il metodo 
		 * statico alert del Controller e restituire false; in caso invece di esito positivo, deve semplicemente restituire true.
		 * */
		// *****************************
		// DA FARE
		// *****************************		
	}

	private void aggiornaMaxOreProgetto(String projectName) {
		txtOreTotaliProgetto.setText(String.valueOf(controller.getMaxHoursPerProject(projectName)));
	}
	
}
