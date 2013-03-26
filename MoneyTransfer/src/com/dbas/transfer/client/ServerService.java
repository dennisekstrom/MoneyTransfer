package com.dbas.transfer.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ServerService extends RemoteService {
	boolean registerCustomer(String firstName, String lastName, String ppn);
	
	boolean sendMoney(double amount, String senderPPN, String receiverPPN,
			String sendKioskAddress, String recKioskAddress);

	String receiveMoney(String receiverId, String recKioskAddress);
	
	Integer getKioskId(String address);
	
	String[] getKioskAddresses();
}
