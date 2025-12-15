package com.marotech.vending.model;

public enum OrgType {

    AGGREGATOR("Aggregator"), VENDOR("Vendor"), MERCHANT("Merchant");
    private final String type;

    private OrgType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
