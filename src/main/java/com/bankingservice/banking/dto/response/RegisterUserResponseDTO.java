package com.bankingservice.banking.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserResponseDTO implements Serializable {
    /**
     * name is user's name
     */
    private String name;
    /**
     * email is user's email ID which should be unique
     */
    private String email;
    /**
     * mobileNumber is user's mobile number
     */
    private Long mobileNumber;
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
}
