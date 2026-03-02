package gringottbank.model;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

public class CoinAmount extends Amount {

	private Map<Coin,Integer> quantity;
	
	/** 
	 * The constructor creates a zero-amount. To create other amounts, just use plus/minus methods in cascade.
	 * EXAMPLE: new CoinAmount().plus(Coin.GALLEON,10).plus(Coin.SICKLE,15)
	 * */
	public CoinAmount() {
		this.quantity = new TreeMap<>();
		Stream.of(Coin.values()).forEach(k -> this.quantity.put(k,0));
	}
	
	public CoinAmount(Coin coin, int howMany) {
		this();
		if (howMany<0) throw new IllegalArgumentException("quantity of coins should be non-negative");
		adder(coin,howMany);
	}
	
	public CoinAmount plus(Coin coin, int howMany) {
		if (howMany<0) throw new IllegalArgumentException("quantity of coins should be non-negative");
		adder(coin,howMany);
		return this;
	}
	
	public CoinAmount minus(Coin coin, int howMany) {
		if (howMany<0) throw new IllegalArgumentException("quantity of coins should be non-negative");	
		adder(coin,-howMany);
		return this;
	}
	
	protected void adder(Coin coin, int howMany) {
		if (coin == null) throw new IllegalArgumentException("null coin is not allowed");	
		Integer currentQuantity = this.getQuantity(coin);
		this.quantity.put(coin, (currentQuantity==null ? 0 : currentQuantity)+howMany);
	}
	
	public Integer getQuantity(Coin t){
		return quantity.getOrDefault(t,0);
	}
		
	public String toString() {
		var res = this.quantity.keySet().stream().map(this::entryString).reduce("", (s1,s2)->s1+s2);
		return res.equals("") ? "0" : res.substring(0, res.length()-2);
	}
	
	private String entryString(Coin key) {
		return quantity.getOrDefault(key,0) + " " + key.getName() + ", "; 
	}

	@Override
	public boolean isNegative() {
		return getValueInKnuts()<0;
	}

	public int getValueInKnuts() {
		return (this.getQuantity(Coin.GALLEON)*Coin.GALLEON.getDivider() + this.getQuantity(Coin.SICKLE))
				*Coin.SICKLE.getDivider() + this.getQuantity(Coin.KNUT);
	}

	@Override
	public int hashCode() {
		return Objects.hash(quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CoinAmount other = (CoinAmount) obj;
		return Objects.equals(quantity, other.quantity);
	}	

}
