package com.bankingservice.banking.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CardRequestDTO implements Serializable {
    /**
     * userId is the user id which is unique
     */
    private String userId;
    private Long otp;
}
