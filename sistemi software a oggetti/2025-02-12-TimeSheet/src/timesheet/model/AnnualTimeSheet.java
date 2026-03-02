package timesheet.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AnnualTimeSheet {

	private MonthlyTimeSheet[] monthlyTimesheets;
	private Year year;
	
	public AnnualTimeSheet(Year year) {
		Objects.requireNonNull(year, "L'anno non può essere nullo");
		var today = LocalDate.now();
		if (today.getYear()<year.getValue()) throw new IllegalArgumentException("Non si possono compilare timesheet per anni futuri");
		this.year=year;
		this.monthlyTimesheets = new MonthlyTimeSheet[Month.values().length];
		for(Month month: Month.values()) this.monthlyTimesheets[month.ordinal()] = new MonthlyTimeSheet(month, year);
	}
	
	public AnnualTimeSheet() {
		this(Year.of(LocalDate.now().getYear()));
	}

	public Year getYear() {
		return year;
	}
	
	public int getTotalWorkedTime() {
		return Stream.of(monthlyTimesheets).filter(m -> m!=null).mapToInt(MonthlyTimeSheet::getTotalWorkedTime).sum();
	}
	
	public int getTotalWorkedTimePerProject(String project) {
		Objects.requireNonNull(project, "Il nome del progetto non può essere nullo");
		if (project.isBlank()) throw new IllegalArgumentException("Il nome del progetto non può essere blank");
		return Stream.of(monthlyTimesheets).filter(m -> m!=null).mapToInt(ts -> ts.getTotalWorkedTimePerProject(project)).sum();
	}
		
	public MonthlyTimeSheet getMonthlyTimeSheet(Month month) {
		Objects.requireNonNull(month, "Il mese da impostare non può essere nullo");
		return monthlyTimesheets[month.ordinal()];
	}
	
	public int getWorkedTime(Month month) {
		Objects.requireNonNull(month, "Il mese non può essere nullo");
		return monthlyTimesheets[month.ordinal()] == null ? 0 : monthlyTimesheets[month.ordinal()].getTotalWorkedTime();
	}
	
	public int getWorkedTime(LocalDate date) {
		Objects.requireNonNull(date, "La data non può essere nulla");
		var month = date.getMonth();
		var monthlyTimesheet = monthlyTimesheets[month.ordinal()];
		Objects.requireNonNull(monthlyTimesheet, "Il mese corrispondente alla data è nullo");
		return monthlyTimesheet.getWorkedTime(date);
	}
	
	public int getWorkedTimePerProject(Month month, String project) {
		Objects.requireNonNull(month, "Il mese non può essere nullo");
		Objects.requireNonNull(project, "Il nome del progetto non può essere nullo");
		if (project.isBlank()) throw new IllegalArgumentException("Il nome del progetto non può essere blank");
		return monthlyTimesheets[month.ordinal()].getTotalWorkedTimePerProject(project);
	}
	
	public int getWorkedTimePerProject(LocalDate giorno, String project) {
		Objects.requireNonNull(giorno, "Il giorno non può essere nullo");
		Objects.requireNonNull(project, "Il nome del progetto non può essere nullo");
		if (project.isBlank()) throw new IllegalArgumentException("Il nome del progetto non può essere blank");
		var monthlyTimesheet = monthlyTimesheets[Month.from(giorno).ordinal()];
		return monthlyTimesheet.getWorkedTimePerProject(giorno, project);
	}
	
	@Override
	public String toString() {
		return "Timesheet Anno " + year + System.lineSeparator() + System.lineSeparator()
				+ Stream.of(monthlyTimesheets).filter(m -> m!=null).map(MonthlyTimeSheet::toString).collect(Collectors.joining(System.lineSeparator()));
	}
	
	public Set<String> activeProjects() {
		return Stream.of(monthlyTimesheets).filter(m -> m!=null).map(MonthlyTimeSheet::activeProjects).flatMap(Set::stream).collect(Collectors.toSet());
	}
}
