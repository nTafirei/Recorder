package com.marotech.vending.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marotech.vending.gson.GsonExcludeField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletPurchaseRequest extends BaseRequest{
    private String productId;
    private String meterNumber;
    private BigDecimal amount;
    private String currency;
    private String beneficiaryMobileNumber;
    private String paymentMethod;
    private String accountName;
    private String accountNumber;
    private String accountPin;
    @GsonExcludeField
    @JsonIgnore
    public boolean isValid() {
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(password)){
        return  false;
        }

        return  StringUtils.isNotBlank(beneficiaryMobileNumber)
                && StringUtils.isNotBlank(productId) && amount != null;
    }

    @Override
    public String toString() {
        return "WalletPurchaseRequest{" +
                "productId='" + productId + '\'' +
                ", meterNumber='" + meterNumber + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", beneficiaryMobileNumber='" + beneficiaryMobileNumber + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountPin='" + accountPin + '\'' +
                ", token='" + token + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}