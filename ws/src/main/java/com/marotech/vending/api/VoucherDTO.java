package com.marotech.vending.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VoucherDTO {
    
    private String id;
    private String voucherNumber;
    private String date;
    private String meter;
    private BigDecimal amount;
    private String currency;
    private String buyerMobileNumber;
    private String beneficiaryMobileNumber;
    private String product;
    private String merchant;
}
