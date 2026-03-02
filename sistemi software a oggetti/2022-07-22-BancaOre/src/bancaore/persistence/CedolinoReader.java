package bancaore.persistence;

import java.io.IOException;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

import bancaore.model.Cedolino;

public interface CedolinoReader {
	public Cedolino leggiCedolino(Reader rdr) throws IOException;
	
	public static String getDayOfWeekName(DayOfWeek day, Locale locale) {
		return day.getDisplayName(TextStyle.FULL_STANDALONE, locale).toLowerCase();
	}
}