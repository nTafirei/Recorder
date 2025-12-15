package com.marotech.vending.model;

public enum Verified {

    YES("Yes"), NO("No");

    private String type;

    Verified(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
