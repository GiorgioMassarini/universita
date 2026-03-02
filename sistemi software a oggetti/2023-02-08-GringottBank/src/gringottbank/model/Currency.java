package gringottbank.model;

import java.util.Arrays;

public enum Currency  {
	
	GBP("sterline", 5), USD("dollari", 6), EUR("euro", 5.66); 

	private Currency(String name, double exchangeRate){
		this.exchangeRate=exchangeRate;
		this.name=name;
	}
	private double exchangeRate;
	private String name;
	
	public double getExchangeRate() { return exchangeRate; }
	public String getName() { return name; }
	
	/** Returns the string names of all constants, in natural order (e.g. "galeoni", "falci", "zellini")
	 */
	public static String[] getNames() {
		return Arrays.stream(Currency.values()).map(Currency::getName).toArray(String[]::new);
	}
}
