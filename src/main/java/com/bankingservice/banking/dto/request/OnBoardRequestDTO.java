package com.bankingservice.banking.dto.request;

import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.Gender;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class OnBoardRequestDTO implements Serializable {
    private Integer age;
    private Long aadhaarNumber;
    private String address;
    private Gender gender;
    private AccountType accountType;
    private Integer registerUserId;
}
