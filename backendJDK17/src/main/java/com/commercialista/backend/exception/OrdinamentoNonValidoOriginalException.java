package com.commercialista.backend.exception;

public class OrdinamentoNonValidoOriginalException extends RuntimeException {
    private String field;

    public OrdinamentoNonValidoOriginalException(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
