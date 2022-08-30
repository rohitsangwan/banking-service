package com.bankingservice.banking.enums;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorCode {
    USER_ID_NOT_FOUND("BS_ACC_400001", "User details not found for given id: %s", "Id may be incorrect, please re-check id", HttpStatus.OK),
    USER_REGISTRATION_FAILED("BS_ACC_400002", "Failed to insert registration details into db", "Email/username already exists", HttpStatus.OK),
    USER_ONBOARD_FAILED("BS_ACC_400034", "Failed to insert on boarding details into db", "Account for this user Id already exists", HttpStatus.OK),
    CARD_GENERATION_FAILED("BS_ACC_400036", "Failed to generate card details", "Card already exists or user not found", HttpStatus.OK);

    private final String code;
    private final String errorMessage;
    private final String displayMessage;
    private final HttpStatus httpStatusCode;

    private ErrorCode(String code, String errorMessage, String displayMessage, HttpStatus httpStatusCode) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.displayMessage = displayMessage;
        this.httpStatusCode = httpStatusCode;
    }
}
