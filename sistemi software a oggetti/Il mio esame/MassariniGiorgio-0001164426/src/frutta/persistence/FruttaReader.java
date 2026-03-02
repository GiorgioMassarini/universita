package frutta.persistence;

import java.io.IOException;
import java.io.Reader;

import frutta.model.MappaFrutti;


public interface FruttaReader {
	
	public MappaFrutti leggiRilevazioniPrezzi(Reader reader) throws IOException, BadFileFormatException;
	
}