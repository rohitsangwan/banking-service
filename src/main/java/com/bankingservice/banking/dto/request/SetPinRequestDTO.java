package com.bankingservice.banking.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SetPinRequestDTO implements Serializable {
    /**
     * pin is the card pin
     */
    private Integer pin;
    /**
     * cardNumber is the card number
     */
    private Long cardNumber;
}
