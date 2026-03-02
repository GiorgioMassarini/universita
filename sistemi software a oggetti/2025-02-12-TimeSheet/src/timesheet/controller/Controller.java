package timesheet.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import timesheet.model.AnnualTimeSheet;
import timesheet.model.Formatters;


public class Controller {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	private Map<String,Integer> projects;
	private boolean okSab, okDom;
	private int maxHoursPerDay;
	private AnnualTimeSheet timesheet;
	private Year year;
		
	//--------------
	
	public Controller(Map<String,Integer> projects, Year year) {
		Objects.requireNonNull(projects,"mappa progetti è null");
		this.projects=projects;
		this.okSab=SiNo.NO.getValue();
		this.okDom=SiNo.NO.getValue();
		this.maxHoursPerDay=8;
		this.year=year;
		this.timesheet = new AnnualTimeSheet(year);
		
		/* Il costruttore ausiliario a due soli argomenti configura il controller nel caso più standard 
		 * (lavoro permesso solo da lunedì a venerdì, max 8 ore lavorative al giorno) per l’anno specificato. 
		*/
	}
	
	public Controller(Map<String,Integer> projects, boolean okSab, boolean okDom, int maxHoursPerDay, Year year) {
		Objects.requireNonNull(projects,"mappa progetti è null");
		if(maxHoursPerDay<=0) {
			throw new IllegalArgumentException("ore massime di lavoro consentite al giorno illegale (<=0)!");
		}
		this.projects=projects;
		this.okSab=okSab;
		this.okDom=okDom;
		this.maxHoursPerDay=maxHoursPerDay;
		this.year=year;
		this.timesheet = new AnnualTimeSheet(year);
		/*
		 * Il costruttore primario riceve la Map<String,Integer> restituita dalla persistenza, due booleani che 
		 * esprimono l’eventuale possibilità di lavorare anche il sabato o la domenica, il numero massimo di ore
		 * lavorabili al giorno e l’anno di lavoro: dopo aver validato gli argomenti, memorizza il riferimento alla
		 * struttura dati ricevuta e crea l’AnnualTimeSheet per l’anno specificato, su cui opererà da ora in poi.
		*/
		// *****************************
		// DA FARE
		// *****************************
	}

	public Map<String,Integer> getProjects() {
		return projects;
	}

	public List<String> getProjectNames() {
		return projects.keySet().stream().sorted().toList();
	}

	public SiNo isSaturdayWorkingDay() {
		return SiNo.of(okSab);
	}
	
	public SiNo isSundayWorkingDay() {
		return SiNo.of(okDom);
	}

	public int getMaxHoursPerDay() {
		return maxHoursPerDay;
	}
	
	public int getMaxHoursPerProject(String project) {
		Objects.requireNonNull(project, "getMaxHoursPerProject: il nome del progetto non può essere nullo");
		if(project.isBlank()) throw new IllegalArgumentException("getMaxHoursPerProject: il nome del progetto non può essere vuoto");
		return projects.getOrDefault(project, 0);
	}
	
	public int getTotalWorkedTimePerProject(String project) {
		Objects.requireNonNull(project, "getTotalWorkedTimePerProject: il nome del progetto non può essere nullo");
		if(project.isBlank()) throw new IllegalArgumentException("getTotalWorkedTimePerProject: il nome del progetto non può essere vuoto");
		return timesheet.getTotalWorkedTimePerProject(project);
	}
	
	public int getWorkedTime(Month month) {
		Objects.requireNonNull(month, "getWorkedTime: il mese non può essere nullo");
		return timesheet.getWorkedTime(month);
	}
	
	public int getWorkedTime(LocalDate date) {
		Objects.requireNonNull(date, "getWorkedTime: la data non può essere nulla");
		return timesheet.getWorkedTime(date);
	}
	
	public int getWorkedTimePerProject(LocalDate date, String project) {
		Objects.requireNonNull(date, "getWorkedTimePerProject: la data non può essere nulla");
		Objects.requireNonNull(project, "getWorkedTimePerProject: il nome del progetto non può essere nullo");
		if(project.isBlank()) throw new IllegalArgumentException("getWorkedTimePerProject: il nome del progetto non può essere vuoto");
		return timesheet.getWorkedTimePerProject(date, project);		
	}
	
	public int getWorkedTimePerProject(Month month, String project) {
		Objects.requireNonNull(month, "getWorkedTimePerProject: il mese non può essere nullo");
		Objects.requireNonNull(project, "getWorkedTimePerProject: il nome del progetto non può essere nullo");
		if(project.isBlank()) throw new IllegalArgumentException("getWorkedTimePerProject: il nome del progetto non può essere vuoto");
		return timesheet.getWorkedTimePerProject(month, project);
	}
	
	public void setWorkedTimePerProject(LocalDate date, int minutes, String projectName) {
		Objects.requireNonNull(date, "setWorkedTimePerProject: la data non può essere nulla");
		if(minutes<0) throw new IllegalArgumentException("Minuti lavorati non possono essere negativi: " + minutes);
		Objects.requireNonNull(projectName, "setWorkedTimePerProject: il nome del progetto non può essere nullo");
		if(projectName.isBlank()) throw new IllegalArgumentException("setWorkedTimePerProject: il nome del progetto non può essere vuoto");
		timesheet.getMonthlyTimeSheet(date.getMonth()).setWorkedTimePerProject(date, minutes, projectName); 
	}
	
	public String annualDetail() {
		return timesheet.toString();
	}
	
	public String annualSynthesis() {
		return "SINTESI ORE LAVORATE SUI SINGOLI PROGETTI" + System.lineSeparator()
				+ timesheet.activeProjects().stream()
				 	.map(s -> String.format("%-30s", s) + "\t"+ Formatters.getTimeAsString(this.getTotalWorkedTimePerProject(s)) +
				 			" ( totali previste: " + this.getMaxHoursPerProject(s) + ")"
				 			)
				 	.collect(Collectors.joining(System.lineSeparator()));
	}

	public Year getYear() {
		return year;
	}
	
}
