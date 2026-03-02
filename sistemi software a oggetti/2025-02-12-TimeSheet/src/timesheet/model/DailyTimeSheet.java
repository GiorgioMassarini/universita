package timesheet.model;

import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


public class DailyTimeSheet {

	private SortedMap<String, Integer> workedMinutesPerProject;
	
	public DailyTimeSheet() {
		workedMinutesPerProject = new TreeMap<>();
	}
	
	public void setWorkedTime(String projectName, int minutes) {
		Objects.requireNonNull(projectName, "Il nome del progetto non può essere nullo");
		if (projectName.isBlank()) throw new IllegalArgumentException("Il nome del progetto non può essere vuoto");
		if (minutes<0) throw new IllegalArgumentException("I minuti lavorati non possono essere negativi: " + minutes);
		workedMinutesPerProject.put(projectName,minutes);
	}
	 

	public int getWorkedTime(String projectName) {
		Objects.requireNonNull(projectName, "Il nome del progetto non può essere nullo");
		if (projectName.isBlank()) throw new IllegalArgumentException("Il nome del progetto non può essere vuoto");
		return workedMinutesPerProject.getOrDefault(projectName, 0);
	}
	
	public String getWorkedTimeAsString(String projectName) {
		return Formatters.getTimeAsString(getWorkedTime(projectName));
	}
	
	public int getTotalWorkedTime() {
		return workedMinutesPerProject.values().stream().mapToInt(Integer::intValue).sum();
	}
	
	public Set<String> activeProjects() {
		return workedMinutesPerProject.keySet();
	}
	
	@Override
	public String toString() {
		return workedMinutesPerProject.toString();
	}
}

