package com.marotech.vending.api;

import com.marotech.vending.model.RegType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private RegType regType;
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationalId;
    private String address;
    private String town;
    private String country;
    private String mobileNumber;
    private String vendorId;
    private LocalDate dateOfBirth;
    private String answer1;
    private String answer2;
    private String answer3;
}
