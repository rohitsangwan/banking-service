package com.bankingservice.banking.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCode {
    /**
     * The user id not found
     */
    USER_ID_NOT_FOUND("BS_ACC_400001", "User details not found for given id", "Id may be incorrect, please re-check id"),
    /**
     * User registration failed
     */
    USER_REGISTRATION_FAILED("BS_ACC_400002", "Failed to insert registration details into sql", "Email/username already exists"),
    /**
     * User onboarding failed
     */
    USER_ONBOARD_FAILED("BS_ACC_400034", "Failed to insert on boarding details into sql", "Account for this user Id already exists");

    private final String code;
    private final String errorMessage;
    private final String displayMessage;

    private ErrorCode(String code, String errorMessage, String displayMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.displayMessage = displayMessage;
    }
}
