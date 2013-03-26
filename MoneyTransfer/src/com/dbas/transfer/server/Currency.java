package com.dbas.transfer.server;
public class Currency {
	private String code;
	
	public Currency(String code) {
		setCode(code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		for (Character ch : code.toCharArray())
			if (!Character.isLetter(ch))
				throw new IllegalArgumentException("Code must consist of exactly 3 letters.");
		
		this.code = code.toUpperCase();
	}
}