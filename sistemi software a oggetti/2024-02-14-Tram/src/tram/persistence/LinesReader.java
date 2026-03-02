package tram.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import tram.model.Linea;


public interface LinesReader {
	Set<Linea> leggiLinee(Reader reader) throws IOException, BadFileFormatException;
}
