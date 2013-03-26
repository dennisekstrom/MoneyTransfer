package com.dbas.transfer.server;
import java.sql.Date;

import com.dbas.transfer.client.Customer;

public class Transfer {

	

	private int id;
	private Date time;
	private double sendAmount;
	private TransferStatus status;
	private Customer sender;
	private Customer receiver;
	private Kiosk sendKiosk;
	private Kiosk recKiosk;
	private Currency sendCurrency;
	private Currency recCurrency;

	public Transfer(int id, Date time, double sendAmount,
			TransferStatus status, Customer sender, Customer receiver,
			Kiosk sendKiosk, Kiosk recKiosk, Currency sendCurrency,
			Currency recCurrency) {
		this.id = id;
		this.time = time;
		this.sendAmount = sendAmount;
		this.status = status;
		this.sender = sender;
		this.receiver = receiver;
		this.sendKiosk = sendKiosk;
		this.recKiosk = recKiosk;
		this.sendCurrency = sendCurrency;
		this.recCurrency = recCurrency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(double sendAmount) {
		if (sendAmount <= 0)
			throw new IllegalArgumentException("sendAmount <= 0");

		this.sendAmount = sendAmount;
	}

	public TransferStatus getStatus() {
		return status;
	}

	public void setStatus(TransferStatus status) {
		this.status = status;
	}

	public Customer getSender() {
		return sender;
	}

	public void setSender(Customer sender) {
		this.sender = sender;
	}

	public Customer getReceiver() {
		return receiver;
	}

	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}

	public Kiosk getSendKiosk() {
		return sendKiosk;
	}

	public void setSendKiosk(Kiosk sendKiosk) {
		this.sendKiosk = sendKiosk;
	}

	public Kiosk getRecKiosk() {
		return recKiosk;
	}

	public void setRecKiosk(Kiosk recKiosk) {
		this.recKiosk = recKiosk;
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

}
