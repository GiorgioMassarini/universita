package gringottbank.model;

import java.util.Objects;

public class Ceiling {
	private String name;
	private Amount amount;

	public Ceiling(String name, Amount amount) {
		this.name = name;
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Ceiling other = (Ceiling) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(name, other.name);
	}

	public String getName() {
		return name;
	}

	public Amount getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Tetto max per " + name + ": " + amount.toString();
	}

}
