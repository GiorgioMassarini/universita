package tram.ui;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tram.controller.Controller;
import tram.model.Fermata;
import tram.model.Frequenza;
import tram.model.Linea;


public class TramLinesAppMock extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Dentinia Tram Lines - MOCK");
		//
		var fermateLinea1 = List.of(new Fermata("Parco Nord", 0), new Fermata("Parco Centro", 6), new Fermata("Giardino", 10), new Fermata("Parco Sud", 14));
		var fermateLinea2 = List.of(new Fermata("Terminal", 0), new Fermata("Via Bologna", 5), new Fermata("corso Cairoli", 9), new Fermata("Piazza Bra", 16));
		var fermateLinea3 = List.of(new Fermata("Aeroporto", 0), new Fermata("Parcheggio interscambio", 5), new Fermata("Porta Nuova", 12), new Fermata("Stazione FS", 20));
		var linea1 = new Linea("Linea 1", new Frequenza(7), LocalTime.of(6,30), LocalTime.of(21,26), fermateLinea1);	
		var	linea2 = new Linea("Linea 2", new Frequenza(8), LocalTime.of(6,00), LocalTime.of(22,00), fermateLinea2);	
		var	linea3 = new Linea("Linea 3", new Frequenza(10), LocalTime.of(6,00), LocalTime.of(23,20), fermateLinea3);	
		//
		var controller = new Controller(Set.of(linea3, linea1, linea2));
		//
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 550, 200, Color.AQUAMARINE);
		stage.setScene(scene);stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
