package com.dbas.transfer.server;
public class Kiosk {
	private int id;
	private String address;
	private Currency currency;
	private double operatingBalance;
	private double revenue;

	public Kiosk(int id, String address, Currency currency,
			double operatingBalance, double revenue) {
		this.id = id;
		this.address = address;
		this.currency = currency;
		this.operatingBalance = operatingBalance;
		this.revenue = revenue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getOperatingBalance() {
		return operatingBalance;
	}

	public void setOperatingBalance(double operatingBalance) {
		this.operatingBalance = operatingBalance;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

}
