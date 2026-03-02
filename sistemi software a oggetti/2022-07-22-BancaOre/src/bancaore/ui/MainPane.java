package bancaore.ui;

import bancaore.controller.Controller;
import bancaore.model.VoceBancaOre;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane {
	
	private Controller controller;
	private ListView<VoceBancaOre> listView;
	private TextField txtOreLavorate, txtSaldoOreIniziale, txtSaldoOreFinale, 
						txtOrePreviste, txtTotRiposiAOre, txtTotRiposi, txtOreCaricate;
	private TextArea output;
	
	public MainPane(Controller controller) {
		this.controller=controller;
		// ----- top box -----
		HBox topBox = new HBox(10);
		topBox.setPrefWidth(400);
		topBox.getChildren().addAll(new Label("Dipendente: " + this.controller.getNomeDipendente()) );
		topBox.getChildren().addAll(new Label("Mese di: " + this.controller.getMeseAnno()) );
		this.setTop(topBox);		
		// ----- left box -----
		listView = new ListView<>();
		
		// *** DA FARE: popolamento list view ***
		
		listView.setPrefWidth(400);
		listView.setPrefHeight(listView.getItems().size()*24+ 2);
		this.setLeft(listView);	
		
		// *** DA FARE: aggancio listener per gestione eventi (metodo myHandler) ***
				
		// ----- right box -----
		VBox rightBox = new VBox(10);
		rightBox.setPrefWidth(400);
			//---
			txtSaldoOreIniziale = new TextField();
			txtSaldoOreIniziale.setEditable(false);
			txtSaldoOreIniziale.setMaxWidth(70);
			//---
			txtSaldoOreFinale = new TextField();
			txtSaldoOreFinale.setEditable(false);
			txtSaldoOreFinale.setMaxWidth(70);
			//---
			txtOreCaricate = new TextField();
			txtOreCaricate.setEditable(false);
			txtOreCaricate.setMaxWidth(70);
			txtOreCaricate.setAlignment(Pos.CENTER_RIGHT);
			//---
			txtOreLavorate = new TextField();
			txtOreLavorate.setEditable(false);
			txtOreLavorate.setMaxWidth(70);
			txtOreLavorate.setAlignment(Pos.CENTER_RIGHT);
			//---
			txtOrePreviste = new TextField();
			txtOrePreviste.setEditable(false);
			txtOrePreviste.setMaxWidth(70);
			txtOrePreviste.setAlignment(Pos.CENTER_RIGHT);
			//---
			txtTotRiposiAOre = new TextField();
			txtTotRiposiAOre.setEditable(false);
			txtTotRiposiAOre.setMaxWidth(70);
			txtTotRiposiAOre.setAlignment(Pos.CENTER_RIGHT);
			//---
			txtTotRiposi = new TextField();
			txtTotRiposi.setEditable(false);
			txtTotRiposi.setMaxWidth(70);
			txtTotRiposi.setAlignment(Pos.CENTER_RIGHT);
			//---
			output = new TextArea();
			output.setEditable(false);
			output.setMaxWidth(350);
			//---
			HBox miniBoxOrePreviste= new HBox(10);
			txtOrePreviste.setText(controller.getTotaleOrePreviste());
			miniBoxOrePreviste.getChildren().addAll(new Label("Ore previste"), txtOrePreviste);
			rightBox.getChildren().addAll(miniBoxOrePreviste);
			//---
			HBox miniBoxOreCaricate = new HBox(10);
			txtOreCaricate.setText(controller.getTotaleOreCaricate());
			miniBoxOreCaricate.getChildren().addAll(new Label("Ore caricate"), txtOreCaricate);
			rightBox.getChildren().addAll(miniBoxOreCaricate);
			//---
			rightBox.getChildren().addAll(new Label("di cui:"));
			//---
			HBox miniBoxOreLavorate = new HBox(10);
			txtOreLavorate.setText(controller.getTotaleOreLavorate());
			miniBoxOreLavorate.getChildren().addAll(new Label("Ore lavorate"), txtOreLavorate);
			rightBox.getChildren().addAll(miniBoxOreLavorate);
			//---		
			HBox miniBoxRiposiAOre = new HBox(10);
			txtTotRiposiAOre.setText(controller.getTotalePermessiAOre());
			miniBoxRiposiAOre.getChildren().addAll(new Label("Riposi a ore" ), txtTotRiposiAOre);
			rightBox.getChildren().addAll(miniBoxRiposiAOre);
			//---		
			HBox miniBoxRiposi = new HBox(10);
			txtTotRiposi.setText(controller.getTotalePermessiGiornalieri());
			miniBoxRiposi.getChildren().addAll(new Label("Riposi         "), txtTotRiposi);
			rightBox.getChildren().addAll(miniBoxRiposi);
			//---
			rightBox.getChildren().addAll(new Label("Banca ore:"));
			//---
			HBox miniBoxSaldoOreIniziale = new HBox(10);
			txtSaldoOreIniziale.setText(controller.getSaldoOreIniziale());
			miniBoxSaldoOreIniziale.getChildren().addAll(new Label("Saldo iniziale"), txtSaldoOreIniziale);
			rightBox.getChildren().addAll(miniBoxSaldoOreIniziale);
			//---		
			HBox miniBoxSaldoOreFinale = new HBox(10);
			txtSaldoOreFinale.setText(controller.getSaldoOreFinale());
			miniBoxSaldoOreFinale.getChildren().addAll(new Label("Saldo finale  "), txtSaldoOreFinale);
			rightBox.getChildren().addAll(miniBoxSaldoOreFinale);
			//---		
			rightBox.getChildren().addAll(new Label("Dettagli  "), output);			
		this.setRight(rightBox);
	}
		
	private void myHandler(VoceBancaOre voce) {		
		// *** DA FARE: gestione evento ***
	}
	
}
