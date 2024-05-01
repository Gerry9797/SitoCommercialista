package com.commercialista.backend.exception;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ProblemaValidazioneException extends ProblemaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3913851529145225430L;

	public ProblemaValidazioneException(List<FieldError> fieldErrors) {
		super("0004", 400, "Errore di validazione", fieldErrors.stream()
				.map(fieldError -> String.format("%s.%s %s", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.joining(". ", "", "."))
		);
	}

	public ProblemaValidazioneException(String errorMessage) {
		super("0013", 400, "Errore di validazione", errorMessage);
	}

}
