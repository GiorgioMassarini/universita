package gringottbank.model;

import java.util.Objects;

public class Withdrawal {
	private String name;
	private Amount amount;

	public Withdrawal(String name, Amount amount) {
		if (amount.isNegative()) throw new IllegalArgumentException("Amount to be withdrawn cannot be negative " + amount);
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
		Withdrawal other = (Withdrawal) obj;
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
		return "Prelievo di " + name + " per " + amount.toString();
	}

}
