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
	
	public boolean sendMoney(double amount, Customer sender, Customer receiver, int sendKioskId, int recKioskId) {
		return DBConnection.sendMoney(amount, sender, receiver, sendKioskId, recKioskId);
	}
	
	public Double receiveMoney(long receiverId, int recKioskId) {
		return DBConnection.receiveMoney(receiverId, recKioskId);
	}
	
	public String[] getKioskAdresses() {
		ArrayList<String> adresses = new ArrayList<String>();
		for (Kiosk kiosk : DBConnection.getKiosks())
			adresses.add(kiosk.getAddress());
		return adresses.toArray(new String[0]);
	}
}