package com.marotech.recording.model;

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
