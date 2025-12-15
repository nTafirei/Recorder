package com.marotech.vending.util;

public class EmailValidator {

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return org.apache.commons.validator.EmailValidator.getInstance().isValid(email);
    }
}
