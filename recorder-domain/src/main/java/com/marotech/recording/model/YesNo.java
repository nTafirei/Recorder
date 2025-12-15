package com.marotech.recording.model;

public enum YesNo {

    YES("Yes"), NO("No");

    private String label;

    YesNo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
