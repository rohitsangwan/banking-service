package com.bankingservice.banking.dto.response.transaction;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActivateAccountDTO implements Serializable {
    private Long accountNumber;
}
