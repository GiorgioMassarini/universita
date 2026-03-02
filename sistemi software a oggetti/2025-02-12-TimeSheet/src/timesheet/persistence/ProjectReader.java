package timesheet.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;


public interface ProjectReader {
	
	public Map<String,Integer> projectHours(Reader reader) throws IOException, BadFileFormatException;
	
}