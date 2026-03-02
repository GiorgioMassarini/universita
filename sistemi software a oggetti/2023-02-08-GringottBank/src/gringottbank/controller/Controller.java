package gringottbank.controller;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import gringottbank.model.Amount;
import gringottbank.model.Atm;
import gringottbank.model.Ceiling;
import gringottbank.model.Coin;
import gringottbank.model.CoinAmount;
import gringottbank.model.Currency;
import gringottbank.model.CurrencyAmount;
import gringottbank.model.ImpossibleWithdrawException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {
	

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	//--------------
	
	private Atm atm;
	private List<Ceiling> ceilingList;
	private List<String> clientList;
	private NumberFormat formatter;
	
	public Controller(Atm atm, List<Ceiling> ceilingList) {
		this.atm = atm;
		this.ceilingList = ceilingList;
		this.clientList = ceilingList.stream().map(Ceiling::getName).collect(Collectors.toList());
		this.formatter = NumberFormat.getInstance(Locale.ITALY);
		this.formatter.setMinimumFractionDigits(2);
		this.formatter.setMaximumFractionDigits(2);
	}

	public Atm getAtm() {
		return atm;
	}

	public List<Ceiling> getCeilings() {
		return ceilingList;
	}

	public List<String> getClients() {
		return clientList;
	}

	public Amount withdraw(String idCliente, int money, Currency currency) throws ImpossibleWithdrawException {
		CurrencyAmount requestedAmount = new CurrencyAmount(money, currency);
		Optional<Ceiling> match = ceilingList.stream().filter(item -> item.getName().equals(idCliente)).findFirst();
		if (match.isEmpty()) {
			throw new ImpossibleWithdrawException("Prelievo non autorizzato - Cliente sconosciuto (" + idCliente + ")");
		}
		else {
			CoinAmount plafond = (CoinAmount) match.get().getAmount(); // it's a CoinAmount in fact
			int plafondInKnuts = plafond.getValueInKnuts();
			double plafondInGalleons = plafondInKnuts /  Coin.GALLEON.getDivider() / Coin.SICKLE.getDivider();
			double plafondInCurrency = plafondInGalleons * currency.getExchangeRate();
			if (plafondInCurrency<requestedAmount.getQuantity()) {
				throw new ImpossibleWithdrawException("Prelievo non autorizzato - L'ammontare richiesto supera il plafond disponibile (" + plafond 
						+ "), pari a " + formatter.format(plafondInCurrency) + " " + currency.name(),
						atm.withdraw(requestedAmount));
			}
			else
			if (requestedAmount.getQuantity()==0) {
				throw new ImpossibleWithdrawException("Prelievo non autorizzato - Impossibile prelevare zero");
			}
			else {
				return atm.withdraw(requestedAmount);
			}
		}
	}

	public Amount withdraw(String idCliente, int galleons, int sickles, int knuts) throws ImpossibleWithdrawException {
		CoinAmount requestedAmount = new CoinAmount().plus(Coin.GALLEON, galleons).plus(Coin.SICKLE, sickles).plus(Coin.KNUT, knuts);
		Optional<Ceiling> match = ceilingList.stream().filter(item -> item.getName().equals(idCliente)).findFirst();
		if (match.isEmpty()) {
			throw new ImpossibleWithdrawException("Prelievo non autorizzato - Cliente sconosciuto (" + idCliente + ")");
		}
		else {
			CoinAmount plafond = (CoinAmount) match.get().getAmount(); // it's a CoinAmount in fact
			if (plafond.getValueInKnuts()<requestedAmount.getValueInKnuts()) {
				throw new ImpossibleWithdrawException("Prelievo non autorizzato - L'ammontare richiesto supera il plafond disponibile (" + plafond + ")",
						atm.withdraw(requestedAmount));
			}
			else
			if (requestedAmount.getValueInKnuts()==0) {
				throw new ImpossibleWithdrawException("Prelievo non autorizzato - Impossibile prelevare zero");
			}
			else {
				return atm.withdraw(requestedAmount);
			}
		}
		
	}

}
