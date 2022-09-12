package com.bankingservice.banking.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailRequestDTO implements Serializable {
    private String to;
    private Long otp;
}
