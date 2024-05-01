package com.commercialista.backend.enums;

public enum StatiAccountEnum {

	ATTIVO("ATTIVO"),
	
	NON_ATTIVO("NON_ATTIVO"),
	
	SOSPESO("SOSPESO");
	
	private final String id;
	
	StatiAccountEnum(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	
}
