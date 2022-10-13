package com.bankingservice.banking.dto.response.transaction;

import lombok.Data;

import java.io.Serializable;

@Data
public class TxnValidationDTO implements Serializable {
    private Long accountNumber;
    private Double accountBalance;
}
