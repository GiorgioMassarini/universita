package frutta.model;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.ParseException;


public class Formatters {
	public static DateTimeFormatter itDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);
	
	public static class MyDecimalFormat extends DecimalFormat {
		private static final long serialVersionUID = 1L;
		
		public MyDecimalFormat(String pattern) {super(pattern);}
		
		public Number parse(String source) throws NumberFormatException {
			if (source.trim().contains("NaN")) return Double.NaN;
			try {
				return super.parse(source);
			}
			catch(ParseException e) {
				throw new NumberFormatException(e.getMessage());
			}
		}
	}
	
	public static MyDecimalFormat itPriceFormatter = new MyDecimalFormat("¤ #,##0.00");
}
