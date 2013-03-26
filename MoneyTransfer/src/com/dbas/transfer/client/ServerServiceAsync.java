package com.dbas.transfer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ServerServiceAsync {
	void registerCustomer(String firstName, String lastName, String ppn,
			AsyncCallback<Boolean> callback);

	void sendMoney(double amount, String senderPPN, String receiverPPN,
			String sendKioskAddress, String recKioskAddress,
			AsyncCallback<Boolean> callback);

	void receiveMoney(String receiverId, String recKioskAddress,
			AsyncCallback<String> callback);

	void getKioskId(String address, AsyncCallback<Integer> callback);

	void getKioskAddresses(AsyncCallback<String[]> callback);
}
