package com.commercialista.backend.exception;

public class OrdinamentoNonValidoOriginalException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -788709569813057868L;
	private String field;

    public OrdinamentoNonValidoOriginalException(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
