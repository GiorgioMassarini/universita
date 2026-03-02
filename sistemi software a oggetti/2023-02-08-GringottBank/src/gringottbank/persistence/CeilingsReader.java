package gringottbank.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import gringottbank.model.Ceiling;

public interface CeilingsReader {
	public List<Ceiling> readCeilings(Reader rdr) throws IOException, BadFileFormatException;
}