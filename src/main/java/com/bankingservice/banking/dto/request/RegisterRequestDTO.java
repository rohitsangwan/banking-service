package com.bankingservice.banking.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDTO implements Serializable {
    private String name;
    private String email;
    private Long mobileNumber;
    private String userName;
    private String password;
}
