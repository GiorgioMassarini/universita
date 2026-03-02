package gringottbank.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import gringottbank.model.Ceiling;
import gringottbank.model.Coin;
import gringottbank.model.CoinAmount;

	 /*
		Harry		20 galeoni
		Hermione	19 galeoni, 20 zellini
		Enrico		200 galeoni
		Ambra		100 galeoni, 20 zellini
		Roberta		100 galeoni, 28 zellini
		Ron			12 galeoni, 10 falci, 6 zellini
	 */

public class MyCeilingsReader implements CeilingsReader {

	@Override
	public List<Ceiling> readCeilings(Reader rdr) throws IOException, BadFileFormatException {
		List<Ceiling> result = new ArrayList<>();
		BufferedReader br = new BufferedReader(rdr);
		String line;
		while((line=br.readLine())!=null) {
			if(line.isBlank()) {
				continue;
			}
			String[] tokens = line.split("\\t+");
			if(tokens.length!=2) {
				throw new BadFileFormatException("Errore di formato a riga: "+line);
			}
			String name = tokens[0].trim();
			if(name.isBlank()) {
				throw new BadFileFormatException("nome mancante a riga: "+line);
			}
			CoinAmount amount = extractAmount(tokens[1]);
			Ceiling current = new Ceiling(name, amount);
			result.add(current);
		}
		return result;
	}
	
	private CoinAmount extractAmount(String importoAsString) throws BadFileFormatException {
		String[] coins = importoAsString.split(",");
		CoinAmount result = new CoinAmount();
		if(coins.length<1 || coins.length>3) {
			throw new BadFileFormatException("Formato delle monete errato nella parte: "+importoAsString);
		}
		for(int i=0; i<coins.length;i++) {
			String[] items = coins[i].trim().split("\\s+");
			if(items.length!=2) {
				throw new BadFileFormatException("Spazio mancante o più di uno nella parte: "+importoAsString);
			}
			String currentCoin = items[1].trim();
			int quantitaDelCoin= parsePositive(items[0].trim());
			try {
				result.plus(Coin.of(currentCoin), quantitaDelCoin);
			}catch(IllegalArgumentException e) {
				throw new BadFileFormatException("coin inesistente nella parte: "+importoAsString);
			}
		}
		return result;
		// METODO DI UTILITA' PER CONVERTIRE UN IMPORTO GRINGOTT (es. "12 galeoni, 20 zellini") IN UN COIN AMOUNT
		// DA IMPLEMENTARE
	}

	private int parsePositive(String s) throws BadFileFormatException {
		// METODO DI UTILITA' IN REGALO :)
		int importo;
		try {
			importo = Integer.parseInt(s);
		}
		catch(NumberFormatException e) {
			throw new BadFileFormatException("Formato sotto-importo illegale: numero errato\t" + s);
		}
		if (importo<0) throw new BadFileFormatException("Formato sotto-importo illegale: numero negativo\t" + s);
		return importo;
	}
	
}