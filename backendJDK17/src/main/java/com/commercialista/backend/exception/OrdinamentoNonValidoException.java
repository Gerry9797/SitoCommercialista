package com.commercialista.backend.exception;

public class OrdinamentoNonValidoException extends RuntimeException {
    private String fields;

    public OrdinamentoNonValidoException(String fields) {
        this.fields = fields;
    }

    public String getFields() {
        return fields;
    }
}
