package com.marotech.vending.api;

import com.marotech.vending.model.PaymentType;
import lombok.Data;

@Data
public class PaymentMethodDTO {

    private String name;
    private PaymentType paymentType;
}
