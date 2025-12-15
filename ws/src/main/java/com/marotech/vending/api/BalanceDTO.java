package com.marotech.vending.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {
    private String currency;
    private BigDecimal amount;
}
