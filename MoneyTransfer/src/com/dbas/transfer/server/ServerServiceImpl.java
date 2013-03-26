package com.dbas.transfer.server;

import java.util.ArrayList;

import com.dbas.transfer.client.Customer;
import com.dbas.transfer.client.ServerService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ServerServiceImpl extends RemoteServiceServlet implements
		ServerService {

	@Override
	public boolean registerCustomer(String firstName, String lastName,
			String ppn) {
		return DBConnection.addCustomer(new Customer(firstName, lastName, ppn));
	}

	@Override
	public boolean sendMoney(double amount, String senderPPN,
			String receiverPPN, String sendKioskAddress, String recKioskAddress) {
		return DBConnection.sendMoney(amount, senderPPN, receiverPPN,
				sendKioskAddress, recKioskAddress);
	}

	@Override
	public String receiveMoney(String receiverId, String recKioskAddress) {
		return DBConnection.receiveMoney(receiverId, recKioskAddress);
	}

	@Override
	public Integer getKioskId(String address) {
		return DBConnection.getKioskId(address);
	}

	@Override
	public String[] getKioskAddresses() {
		ArrayList<String> adresses = new ArrayList<String>();
		for (Kiosk kiosk : DBConnection.getKiosks())
			adresses.add(kiosk.getAddress() + " (" + kiosk.getCurrency().getCode() + ")");
		return adresses.toArray(new String[0]);
	}
}