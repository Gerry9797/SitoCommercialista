package com.commercialista.backend.payload.request.test;

import java.util.List;

public class RicercaElementTestCsvRequest extends RicercaElementTestRequest {
    private List<String> visibleFields;

    public List<String> getVisibleFields() {
        return visibleFields;
    }

    public void setVisibleFields(List<String> visibleFields) {
        this.visibleFields = visibleFields;
    }
}
