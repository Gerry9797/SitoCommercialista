package com.commercialista.backend.exception;

public class OrdinamentoNonValidoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1961157373706401562L;
	private String fields;

    public OrdinamentoNonValidoException(String fields) {
        this.fields = fields;
    }

    public String getFields() {
        return fields;
    }
}
