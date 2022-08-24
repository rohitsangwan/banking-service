package com.bankingservice.banking.dto.response;

import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.Gender;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class OnBoardResponseDTO implements Serializable {

    /**
     * age is user's age
     */
    private Integer age;
    /**
     * aadhaarNumber is user's aadhaar number
     */
    private Long aadhaarNumber;
    /**
     * address is user's address
     */
    private String address;
    /**
     * gender is user's gender - MALE/FEMALE
     */
    private Gender gender;
    /**
     * accountType is type of account - CURRENT/SAVINGS/NRI_ACCOUNT
     */
    private AccountType accountType;
    /**
     * registerUserModel contains id, name, email, mobileNumber, userName, password, userId
     */
    private RegisterUserModel registerUserModel;

}
