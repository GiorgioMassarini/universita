package timesheet.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class MonthlyTimeSheet {

	private Month month;
	private Year year;
	private SortedMap<LocalDate, DailyTimeSheet> mappaTimeSheetGiornalieri;
	private LocalDate startDate, endDate;
	
	public MonthlyTimeSheet(Month month, Year year) {
		Objects.requireNonNull(month, "Il mese non può essere nullo");
		Objects.requireNonNull(year, "L'anno non può essere nullo");
		this.month=month;
		this.year=year;
		//
		startDate = LocalDate.of(year.getValue(), month.getValue(), 1);
		endDate = startDate.plusMonths(1).minusDays(1);
		mappaTimeSheetGiornalieri = new TreeMap<>();
		for(LocalDate d=startDate; !endDate.isBefore(d); d = d.plusDays(1)) {
			mappaTimeSheetGiornalieri.put(d, new DailyTimeSheet());
		}
	}

	public Month getMonth() {
		return month;
	}

	public Year getYear() {
		return year;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public int getTotalWorkedTime() {
		return mappaTimeSheetGiornalieri.values().stream().mapToInt(DailyTimeSheet::getTotalWorkedTime).sum();
	}
	
	public String getTotalWorkedTimeAsString() {
		return Formatters.getTimeAsString(getTotalWorkedTime());
	}
	
	public int getTotalWorkedTimePerProject(String project) {
		return mappaTimeSheetGiornalieri.values().stream().mapToInt(workedTimePerProject -> workedTimePerProject.getWorkedTime(project)).sum();
	}
	
	public String getTotalWorkedTimePerProjectAsString(String project) {
		return Formatters.getTimeAsString(getTotalWorkedTimePerProject(project));
	}
	
	public Set<String> activeProjects() {
		return mappaTimeSheetGiornalieri.values().stream()
										.map(DailyTimeSheet::activeProjects)
										.flatMap(Set::stream)
										.collect(Collectors.toSet());
	}
	
	public void setWorkedTimePerProject(LocalDate date, int minutes, String projectName) {
		Objects.requireNonNull(date, "date è null");
		Objects.requireNonNull(projectName, "il nome del progetto è null");
		if(date.isAfter(endDate)||date.isBefore(startDate)) {
			throw new IllegalArgumentException("Data illegale, fuori dal mese di riferimento");
		}
		if(projectName.isBlank()) {
			throw new IllegalArgumentException("il nome del progetto è vuoto");
		}
		if(minutes<0) {
			throw new IllegalArgumentException("i minuti sono negativi");
		}
		DailyTimeSheet giornaliero = mappaTimeSheetGiornalieri.get(date);
		giornaliero.setWorkedTime(projectName, minutes);
		
		/*
		 * Riceve la data relativa al giorno richiesto, i minuti lavorati e il nome del progetto a cui si riferiscono: 
		 * verifica preventivamente che la data sia non nulla e compresa nel mese (*), che i minuti non siano negativi 
		 * e che il nome del progetto non sia nullo né blank, lanciando nel caso le opportune eccezioni con opportuni
		 * messaggi d'errore; 
		 * se tutto fila lascio, accede al DailyTimeSheet del giorno specificato e imposta in esso i minuti lavorati 
		 * per il progetto specificato.
		 * */
		// *****************************
		// DA FARE
		// *****************************		
	}
	
	public int getWorkedTimePerProject(LocalDate date, String projectName) {
		Objects.requireNonNull(date, "date è null");
		Objects.requireNonNull(projectName, "il nome del progetto è null");
		if(date.isAfter(endDate)||date.isBefore(startDate)) {
			throw new IllegalArgumentException("Data illegale, fuori dal mese di riferimento");
		}
		if(projectName.isBlank()) {
			throw new IllegalArgumentException("il nome del progetto è vuoto");
		}
		DailyTimeSheet giornaliero = mappaTimeSheetGiornalieri.get(date);
		if(giornaliero==null) {
			return 0;
		}else {
			return giornaliero.getWorkedTime(projectName);
		}
		/*
		 * Riceve la data richiesta e il nome del progetto desiderato: verifica preventivamente che la data sia non nulla e 
		 * sia compresa nel mese (*), che il nome del progetto non sia nullo né blank, lanciando nel caso le opportune eccezioni
		 * con opportuni messaggi d'errore: solo in caso positivo accede al corrispondente DailyTimeSheet e recupera i minuti 
		 * lavorati in quel giorno per lo specifico progetto
		 */
		// *****************************
		// DA FARE
		// *****************************
	}
	
	public int getWorkedTime(LocalDate date) {
		Objects.requireNonNull(date, "date è null");
		if(date.isAfter(endDate)||date.isBefore(startDate)) {
			throw new IllegalArgumentException("Data illegale, fuori dal mese di riferimento");
		}
		DailyTimeSheet giornaliero = mappaTimeSheetGiornalieri.get(date);
		if(giornaliero==null) {
			return 0;
		}else {
			return giornaliero.getTotalWorkedTime();
		}
		
		/*
		 * restituisce i minuti lavorati in un dato giorno, espresso sotto forma di LocalDate, per tutti i progetti. 
		 * Più precisamente, il metodo riceve la data richiesta, verifica preventivamente che essa sia non nulla e compresa 
		 * nel mese (*), lanciando nel caso le opportune eccezioni con opportuni messaggi d'errore: solo in caso positivo 
		 * accede al corrispondente DailyTimeSheet e recupera i minuti totali lavorati in quel giorno. Nel caso in cui non vi 
		 * sia ancora alcun DailyTimeSheet istanziato per quel giorno, deve restituire (ovviamente) 0 minuti lavorati
		 */
		// *****************************
		// DA FARE
		// *****************************
	}
	
	@Override
	public String toString() {
		return "Mese di " + month.getDisplayName(TextStyle.FULL, Locale.ITALY).toUpperCase() + " " + year + "\t" 
				+ "ore totali " + getTotalWorkedTimeAsString() 
				+ ", di cui: " + this.activeProjects().stream().map(p -> p + " = " + this.getTotalWorkedTimePerProjectAsString(p)).collect(Collectors.joining(", "));
	}
	
	public String toFullString() {
		return this.toString() + System.lineSeparator() + "Dettaglio:" + System.lineSeparator() 
				+ mappaTimeSheetGiornalieri.entrySet().stream().map(
						es -> Formatters.dateFormatter.format(es.getKey()) + "\t" 
						+ this.activeProjects().stream().map(p -> p + " = " + es.getValue().getWorkedTimeAsString(p)).collect(Collectors.joining(", "))
					).collect(Collectors.joining(System.lineSeparator()));
	}
}
