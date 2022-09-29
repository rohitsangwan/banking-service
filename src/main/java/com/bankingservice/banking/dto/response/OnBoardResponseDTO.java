package com.bankingservice.banking.dto.response;

import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.Gender;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class OnBoardResponseDTO implements Serializable {

    /**
     * name is user's name
     */
    private String name;
    /**
     * email is user's email ID which should be unique
     */
    private String email;
    /**
     * userName is user's userName which should be unique
     */
    private String userName;
    /**
     * password is password which will be set by the user
     */
    private String password;
    /**
     * userId is the userId which will be auto generated and will be provided to the user
     */
    private String userId;
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
     * accountNumber is the account number
     */
    private Long accountNumber;
    /**
     * accountBalance is the bank account balance
     */
    private Double accountBalance;
}
