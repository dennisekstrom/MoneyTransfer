package com.dbas.transfer.server;
public class ExchangeRate {
	private Currency sendCurrency;
	private Currency recCurrency;
	private double rate;

	public ExchangeRate(Currency sendCurrency, Currency recCurrency, double rate) {
		this.sendCurrency = sendCurrency;
		this.recCurrency = recCurrency;
		this.rate = rate;
	}

	public Currency getSendCurrency() {
		return sendCurrency;
	}

	public void setSendCurrency(Currency sendCurrency) {
		this.sendCurrency = sendCurrency;
	}

	public Currency getRecCurrency() {
		return recCurrency;
	}

	public void setRecCurrency(Currency recCurrency) {
		this.recCurrency = recCurrency;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}
