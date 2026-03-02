package gringottbank.model;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class CurrencyAmount extends Amount {

	private double quantity;
	private Currency currency;
	private NumberFormat formatter;
	
	public CurrencyAmount(double quantity, Currency currency) throws IllegalArgumentException {
		if (quantity<0) throw new IllegalArgumentException("negative amount");
		this.quantity=quantity;
		this.currency=currency;
		this.formatter = NumberFormat.getInstance(Locale.ITALY);
		this.formatter.setMinimumFractionDigits(2);
		this.formatter.setMaximumFractionDigits(2);
	}
	
	public double getQuantity(){
		return quantity;
	}
				
	public Currency getCurrency() {
		return currency;
	}

	public String toString() {
		return formatter.format(this.getQuantity()) + " " + getCurrency().getName();
	}

	@Override
	public boolean isNegative() {
		return quantity<0;
	}
	
	public CurrencyAmount plus(Currency currency, double howMuch) {
		if (howMuch<0) throw new IllegalArgumentException("quantity of currency should be non-negative");
		adder(currency,howMuch);
		return this;
	}
	
	public CurrencyAmount minus(Currency currency, double howMuch) {
		if (howMuch<0) throw new IllegalArgumentException("quantity of currency  should be non-negative");	
		adder(currency,-howMuch);
		return this;
	}
	
	protected void adder(Currency currency, double howMuch) {
		if (currency == null) throw new IllegalArgumentException("null currency is not allowed");
		double value = (this.currency==currency) ? howMuch : howMuch / currency.getExchangeRate() * this.currency.getExchangeRate();
		this.quantity += value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currency, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CurrencyAmount other = (CurrencyAmount) obj;
		return currency == other.currency && Double.doubleToLongBits(quantity) == Double.doubleToLongBits(other.quantity);
	}

}
