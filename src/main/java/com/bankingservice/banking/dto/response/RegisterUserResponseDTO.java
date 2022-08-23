package com.bankingservice.banking.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserResponseDTO implements Serializable {
    private String name;
    private String email;
    private long mobileNumber;
    private String userName;
    private String password;
    private String userId;
    private int registerUserId;
}
