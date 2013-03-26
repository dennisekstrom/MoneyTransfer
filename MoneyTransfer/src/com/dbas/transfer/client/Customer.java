package com.dbas.transfer.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Customer implements IsSerializable {
	
	private String firstName;
	private String lastName;
	private String passportNum;

	public Customer() {
		this(null, null, null);
	}
	
	public Customer(String firstName, String lastName, String passportNum) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.passportNum = passportNum;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassportNum() {
		return passportNum;
	}

	public void setPassportNum(String passportNum) {
		this.passportNum = passportNum;
	}

}