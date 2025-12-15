package com.marotech.recording.model;

public enum DeviceStatus {

    ACTIVE("Active"), INACTIVE("InActive"), SUSPENDED("Suspended"), CANCELLED(
            "Cancelled");

    private String status;

    private DeviceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
