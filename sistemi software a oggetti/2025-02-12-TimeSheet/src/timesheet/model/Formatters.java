package timesheet.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Formatters {
	public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);

	public static String getTimeAsString(int totalTimeInMinutes) {
		if (totalTimeInMinutes<0) throw new IllegalArgumentException("Minuti negativi: " + totalTimeInMinutes);
		int hours = totalTimeInMinutes / 60, mins = totalTimeInMinutes % 60;
		return timeFormatter.format(LocalTime.of(hours, mins));
	}
	
	public static String getTimeAsString(int hours, int mins) {
		if (hours<0 || hours>23) throw new IllegalArgumentException("Ore fuori range: " + hours);
		if (mins<0 || mins>59) throw new IllegalArgumentException("Minuti fuori range: " + mins);
		return timeFormatter.format(LocalTime.of(hours, mins));
	}
}
