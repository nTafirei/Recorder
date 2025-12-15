package com.marotech.vending.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PointsDTO {
    private int points;
    private String currency;
    private BigDecimal redeemableAmount;
    private boolean canRedeem;
}
