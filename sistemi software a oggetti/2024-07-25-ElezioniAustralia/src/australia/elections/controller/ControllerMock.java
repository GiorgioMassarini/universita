package australia.elections.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import australia.elections.model.Scheda;
import australia.elections.model.Scrutinio;
import australia.elections.persistence.BadFileFormatException;


public class ControllerMock extends Controller {
	
	@Override
	public String leggiSchede(File file) throws IOException, BadFileFormatException {
		this.schedeLette = List.of(
			new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 6))),
			new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 6))),
			new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 3, "Lauren", 4, "Juliet", 6, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 6, "Joe", 3, "Lauren", 4, "Juliet", 2, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 4, "Lauren", 6, "Juliet", 3, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 6, "Joe", 2, "Lauren", 3, "Juliet", 4, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 4, "Joe", 2, "Lauren", 5, "Juliet", 6, "Fred", 3))),
			new Scheda(new TreeMap<>(Map.of("Maria", 1, "Ari", 2, "Joe", 6, "Lauren", 4, "Juliet", 5, "Fred", 3))),

			new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 1, "Joe", 4, "Lauren", 2, "Juliet", 5, "Fred", 3))),
			new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 4, "Lauren", 2, "Juliet", 6, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 1, "Joe", 2, "Lauren", 4, "Juliet", 3, "Fred", 6))),
			new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 1, "Joe", 2, "Lauren", 6, "Juliet", 4, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 1, "Joe", 3, "Lauren", 4, "Juliet", 2, "Fred", 6))),
			new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 1, "Joe", 3, "Lauren", 4, "Juliet", 5, "Fred", 2))),
			
			new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 1, "Lauren", 3, "Juliet", 6, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 1, "Lauren", 5, "Juliet", 4, "Fred", 6))),
			new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 3, "Joe", 1, "Lauren", 2, "Juliet", 6, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 1, "Lauren", 3, "Juliet", 6, "Fred", 5))), 
			
			//new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 3, "Joe", 2, "Lauren", 1, "Juliet", 5, "Fred", 6))), 
			new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 3, "Joe", 5, "Lauren", 1, "Juliet", 2, "Fred", 6))), //modified
			new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 4, "Lauren", 1, "Juliet", 6, "Fred", 5))),

			new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 1, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 1, "Fred", 6))),
			new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 6, "Juliet", 1, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 6, "Lauren", 4, "Juliet", 1, "Fred", 5))),
			new Scheda(new TreeMap<>(Map.of("Maria", 2, "Ari", 6, "Joe", 4, "Lauren", 3, "Juliet", 1, "Fred", 5))),
			
			//new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 6, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 1))),
			new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 4, "Juliet", 5, "Fred", 1))), //modified
			new Scheda(new TreeMap<>(Map.of("Maria", 6, "Ari", 3, "Joe", 2, "Lauren", 5, "Juliet", 4, "Fred", 1))),
			new Scheda(new TreeMap<>(Map.of("Maria", 4, "Ari", 2, "Joe", 3, "Lauren", 6, "Juliet", 5, "Fred", 1))),
			new Scheda(new TreeMap<>(Map.of("Maria", 5, "Ari", 2, "Joe", 3, "Lauren", 4, "Juliet", 6, "Fred", 1))),
			new Scheda(new TreeMap<>(Map.of("Maria", 3, "Ari", 2, "Joe", 5, "Lauren", 4, "Juliet", 6, "Fred", 1))) 
			);
			scrutinio = new Scrutinio(schedeLette);
			return "****MOCK **** " + System.lineSeparator() + schedeToString(schedeLette);
	}
}
