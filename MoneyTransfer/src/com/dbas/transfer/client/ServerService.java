package com.dbas.transfer.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ServerService extends RemoteService {
	boolean sendMoney(double amount, Customer sender, Customer receiver,
			int sendKioskId, int recKioskId) throws IllegalArgumentException;

	Double receiveMoney(long receiverId, int recKioskId);
	
	String[] getKioskAdresses() throws IllegalArgumentException;
}
