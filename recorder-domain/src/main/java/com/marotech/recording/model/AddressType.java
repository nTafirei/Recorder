package com.marotech.recording.model;

public enum AddressType {

    HOME("Home"), BUSINESS("Business");

    private String type;

    private AddressType(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }
}
