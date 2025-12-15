package com.marotech.vending.model;

public enum DocType {

    PASSPORT("Passport"), NATIONAL_ID("National ID"),
    SALES_REPORT("Sales Report");

    private String type;

    DocType(String name) {
        this.type = name;
    }

    public String getType() {
        return type;
    }
}
