package com.marotech.recording.model;

public enum AgentStatus {

    ACTIVE("Active"), NOT_ACTIVE("Not Active"), NOT_AN_AGENT("Not An Agent");

    private String status;

    private AgentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
