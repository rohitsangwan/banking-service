package com.bankingservice.banking.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserResponseDTO implements Serializable {
    private String name;
    private String email;
    private Long mobileNumber;
    private String userName;
    private String password;
    private String userId;
    private Integer registerUserId;
}
