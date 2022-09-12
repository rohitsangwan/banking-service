package com.bankingservice.banking.enums;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorCode {
    USER_ID_NOT_FOUND("BS_ACC_404001", "User details not found for given id: %s", "Id may be incorrect, please re-check id", HttpStatus.NOT_FOUND),
    CARD_NOT_FOUND("BS_ACC_404006", "Card not found", "Card details not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("BS_ACC_404009", "User not found", "User does not exist", HttpStatus.NOT_FOUND),
    USER_REGISTRATION_FAILED("BS_ACC_400002", "Failed to insert registration details into db", "Email/username already exists", HttpStatus.OK),
    USER_ONBOARD_FAILED("BS_ACC_400034", "Failed to insert on boarding details into db", "Account for this user Id already exists", HttpStatus.OK),
    CARD_GENERATION_FAILED("BS_ACC_400036", "Failed to generate card details", "Card already exists or user not found", HttpStatus.OK),
    OTP_VALIDATION_FAILED("BS_ACC_400039", "Incorrect OTP", "Please enter a valid OTP", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED("BS_ACC_400051", "You have entered an expired OTP. Another OTP has been sent to your mail", "Expired OTP", HttpStatus.BAD_REQUEST);


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
