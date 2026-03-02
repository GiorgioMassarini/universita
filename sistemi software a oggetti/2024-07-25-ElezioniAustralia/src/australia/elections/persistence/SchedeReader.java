package australia.elections.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import australia.elections.model.Scheda;

public interface SchedeReader {
	public List<Scheda> leggiSchede(Reader reader) throws IOException, BadFileFormatException;
}
