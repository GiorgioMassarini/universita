package gringottbank.model;

import java.util.Arrays;

public enum Coin  {
	// un Galeone è suddiviso in 17 Falci, ognuno dei quali è a sua volta suddiviso in 29 Zellini
	GALLEON("galeoni", 17), SICKLE("falci", 29), KNUT("zellini", 1); 

	private Coin(String name, int divider){
		this.divider=divider;
		this.name=name;
	}
	private int divider;
	private String name;
	
	public int getDivider() { return divider; }
	public String getName() { return name; }
	
	/** Returns the string names of all constants, in natural order (e.g. "galeoni", "falci", "zellini")
	 */
	public static String[] getNames() {
		return Arrays.stream(Coin.values()).map(Coin::getName).toArray(String[]::new);
	}
	
	/** Returns the proper Coin constant, given its string name
	 *  EXAMPLE: Coin.of("galeoni") returns Coin.GALLEON; etc.
	 */
	public static Coin of(String name) {
		for(Coin c: Coin.values()) if(c.getName().equalsIgnoreCase(name)) return c;
		throw new IllegalArgumentException("coin inesistente");
	}
}
