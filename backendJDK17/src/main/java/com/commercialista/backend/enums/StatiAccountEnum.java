package com.commercialista.backend.enums;

public enum StatiAccountEnum {

	ATTIVO("ATTIVO"),
	
	NON_ATTIVO("NON_ATTIVO"),
	
	SOSPESO("SOSPESO");
	
	private final String codice;
	
	StatiAccountEnum(String codice) {
		this.codice = codice;
	}

	public String getCodice() {
		return codice;
	}
	
	
}
