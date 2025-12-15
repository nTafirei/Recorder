package com.marotech.recording.model;

public enum ActiveStatus {

    ACTIVE("Active"), NOT_ACTIVE("Not Active");

    private String status;

    private ActiveStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
