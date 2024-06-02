package com.commercialista.backend.enums;

public enum SortDirection {
    ASC("asc"),
    DESC("desc");

    private String direction;

    SortDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
