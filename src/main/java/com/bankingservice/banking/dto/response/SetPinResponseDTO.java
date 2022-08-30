package com.bankingservice.banking.dto.response;

import com.bankingservice.banking.enums.CardState;
import lombok.Data;

import java.io.Serializable;

@Data
public class SetPinResponseDTO implements Serializable {
    /**
     * cardNumber is the card number
     */
    private Long cardNumber;
    /**
     * name is the cardholder's name
     */
    private String name;
    /**
     * cvv is the card's cvv
     */
    private Integer cvv;
    /**
     * cardState is the state of the card which can be ACTIVE/BLOCKED/DISABLED
     */
    private CardState cardState;
    /**
     * pin is the card pin
     */
    private Integer pin;
}
