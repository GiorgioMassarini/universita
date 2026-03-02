package frutta.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import frutta.controller.Controller;
import frutta.model.Formatters;
import frutta.model.Rilevazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class MainPane extends BorderPane {
	
	private Controller controller;
	
	private TextArea outputArea;
	private ComboBox<String> comboSottotipi;
	private ComboBox<String> comboTipiFrutta;
	private BarChart<String,Number> chart;
	private Button reset;

	@SuppressWarnings("unused")
	public MainPane(Controller controller) {
		this.controller=controller;
		//
		HBox topBox = new HBox();
			topBox.setPadding(new Insets(10, 10, 10, 10));
			//
			comboTipiFrutta = new ComboBox<>(FXCollections.observableArrayList(controller.tipiFrutta()));
			comboTipiFrutta.setPrefWidth(150);
			comboTipiFrutta.setTooltip(new Tooltip("Scegliere il tipo di frutta da visualizzare"));
			comboTipiFrutta.setOnAction(ev -> popolaComboSottotipi());
			//
			comboSottotipi = new ComboBox<>();
			comboSottotipi.setPrefWidth(150);
			comboSottotipi.setTooltip(new Tooltip("Scegliere il sottotipo"));
			comboSottotipi.setOnAction(ev -> mostraDati());
			//
			topBox.getChildren().addAll(new Label("Tipi di frutta  "), comboTipiFrutta, new Label("    Sottotipo   "), comboSottotipi);			
		this.setTop(topBox);
		
		HBox centerBox = new HBox();
			centerBox.setPadding(new Insets(0, 10, 10, 10));
			//
			outputArea = new TextArea();
			outputArea.setPrefSize(600,250);
			outputArea.setWrapText(true);
			outputArea.setFont(Font.font("Courier New", FontWeight.NORMAL, 14));
			outputArea.setEditable(false);
			//
			centerBox.getChildren().addAll(outputArea);
		this.setCenter(centerBox);
	
		HBox bottomBox = new HBox();
			bottomBox.setPadding(new Insets(10, 10, 10, 10));
			//		
			CategoryAxis asseOrizz = new CategoryAxis(); asseOrizz.setLabel("Date");
			NumberAxis   asseVert  = new NumberAxis();   asseVert.setLabel("Prezzi");
			chart = new BarChart<String,Number>(asseOrizz,asseVert);
			chart.setTitle("Andamento prezzi frutta");
			chart.setPrefHeight(200);
			chart.setLegendSide(Side.RIGHT);
			//
			reset = new Button("Reset");
			reset.setOnAction(ev -> chart.getData().clear());
			bottomBox.getChildren().addAll(chart, reset);			
		this.setBottom(bottomBox);	
		
	}
	
	private void popolaComboSottotipi() {
		var tipoFrutta = comboTipiFrutta.getValue();
		if (tipoFrutta==null) { 
			Controller.alert("Tipo di frutta non selezionato", "Occorre prima selezionare il tipo di frutta", "Selezionare la frutta desiderata dall'elenco\n");
			return;
		}
		outputArea.setText("Tipo di frutta scelto: " + tipoFrutta);
		comboSottotipi.setItems(FXCollections.observableArrayList(controller.sottotipi(tipoFrutta)));
	}

	private void mostraDati() {
		String tipo = comboTipiFrutta.getValue();
		String sottotipo = comboSottotipi.getValue();
		if(tipo==null || sottotipo==null) {
			return;
		}
		//
		OptionalDouble prezzoMinimo = controller.prezzoMinimo(tipo, sottotipo);
		OptionalDouble prezzoMassimo = controller.prezzoMassimo(tipo, sottotipo);
		OptionalDouble prezzoMedio = controller.prezzoMedio(tipo, sottotipo);
		//
		List<Rilevazione> listaRilevazioni = controller.rilevazioni(tipo, sottotipo);
		int numeroRilevazioni = listaRilevazioni.size();
		LocalDate inizioRilevazioni = listaRilevazioni.getFirst().data();
		LocalDate fineRilevazioni = listaRilevazioni.getLast().data();
		String testo ="Tipo di frutta scelto: "+tipo+" "+sottotipo+"\n\n"+
					  "Prezzo campionato su "+numeroRilevazioni+" settimane fra il "+
					  inizioRilevazioni.format(Formatters.itDateFormatter)+" e il "+
					  fineRilevazioni.format(Formatters.itDateFormatter)+"\n - min: "+
					  Formatters.itPriceFormatter.format(prezzoMinimo.getAsDouble())+
					  "\n - max: "+ Formatters.itPriceFormatter.format(prezzoMassimo.getAsDouble())+
					  "\n - med: "+Formatters.itPriceFormatter.format(prezzoMedio.getAsDouble());
		outputArea.setText(testo);
		aggiornaGrafico(tipo, sottotipo);
		// ***** DA REALIZZARE *****
		// Questo metodo deve scatenare il vero calcolo:  
		// - se i valori di tipo/sottotipo sono nulli, il metodo deve ritornare senza fare nulla; 
		// - altrimenti, deve recuperare tipo e sottotipo di frutta, e da quelli, interrogando il Controller, 
		//	 recuperare i dati sui prezzi e sintetizzare il messaggio da emettere a video; 
		//   infine, tramite il metodo ausiliario aggiornaGrafico, provvedere all’aggiornamento del grafico 
	}
	
	private boolean serieGiaEsistente(String nomeSerie) {
		return chart.getData().stream().map(XYChart.Series::getName).filter(n -> n.equals(nomeSerie)).count() > 0;
	}

	private void aggiornaGrafico(String tipoFrutta, String sottotipo) {
		String nomeSerie = tipoFrutta+sottotipo;
		if(serieGiaEsistente(nomeSerie)) {
			return;
		}
		List<Rilevazione> listaRilevazioni = controller.rilevazioni(tipoFrutta, sottotipo);
		Series<String,Number> serie = new Series<>();
		serie.setName(nomeSerie);
		List<Data<String,Number>> listaDati = new ArrayList<>();
		ObservableList<Series<String,Number>> listaSerie = chart.getData();
		for(Rilevazione e : listaRilevazioni) {
			LocalDate data =e.data();
			String dataAsString = Formatters.itDateFormatter.format(data);
			double prezzo = e.prezzo();
			Data<String,Number> dato = new Data<>();
			dato.setXValue(dataAsString);
			dato.setYValue(prezzo);
			listaDati.add(dato);
		}
		serie.setData(FXCollections.observableArrayList(listaDati));
		listaSerie.add(serie);
		chart.setData(listaSerie);
		
		// ***** DA REALIZZARE *****
		// Questo metodo deve verificare preliminarmente se lo specifico tipo e sottotipo di frutta 
		// siano già presenti nel grafico (SUGGERIMENTO: usare l’apposito metodo serieGiàPresente): 
		// in tal caso NON devono essere ri-aggiunti, quindi l’aggiornamento termina qui.
		// Altrimenti, il metodo deve: 
		// - recuperare la lista delle rilevazioni per lo specifico tipo e sottotipo di frutta 
		// - creare la nuova serie dati, *darle il giusto nome* e popolarla, aggiungendo, per ogni
		//   rilevazione, la coppia (data formattata, prezzo) 
		// - aggiungere al grafico la nuova serie così creata e rendere visibile la legenda 
		//   (se già non lo	era) 
	}

}
