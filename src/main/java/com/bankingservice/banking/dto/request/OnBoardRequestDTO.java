package com.bankingservice.banking.dto.request;

import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.Gender;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class OnBoardRequestDTO implements Serializable {
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
     * userId is the user id which gets auto generated
     */
    private String userId;
}