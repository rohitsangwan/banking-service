package com.bankingservice.banking.dto.response;

import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.Gender;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class OnBoardResponseDTO implements Serializable {
    private Integer age;
    private Long aadhaarNumber;
    private String address;
    private Gender gender;
    private AccountType accountType;
    private RegisterUserModel registerUserModel;
}
