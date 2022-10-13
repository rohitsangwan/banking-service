package com.bankingservice.banking.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateBalanceRequestDTO implements Serializable {
    private Long accountNumber;
    private Double accountBalance;
}
