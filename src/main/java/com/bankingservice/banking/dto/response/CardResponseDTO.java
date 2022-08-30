package com.bankingservice.banking.dto.response;

import com.bankingservice.banking.enums.CardState;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
public class CardResponseDTO implements Serializable {
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
}
