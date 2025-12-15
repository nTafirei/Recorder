package com.marotech.vending.api;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private String id;
    private String name;
    private String merchant;
    private String productType;
    private BigDecimal minPurchase;
    private BigDecimal maxPurchase;
    private boolean requiresMetre;
}