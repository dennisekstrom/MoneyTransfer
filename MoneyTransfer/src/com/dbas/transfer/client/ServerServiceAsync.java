package com.dbas.transfer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ServerServiceAsync {
	void sendMoney(double amount, Customer sender, Customer receiver,
			int sendKioskId, int recKioskId, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void receiveMoney(long receiverId, int recKioskId,
			AsyncCallback<Double> callback) throws IllegalArgumentException;

	void getKioskAdresses(AsyncCallback<String[]> callback)
			throws IllegalArgumentException;
}
