package com.bankingservice.banking.exception;

import com.bankingservice.banking.enums.ErrorCode;

public class UserIdNotFoundException extends Exception {
    private ErrorCode errorCode;
    private String errorMessage;
    private String displayMessage;

    public UserIdNotFoundException() {
    }

    public UserIdNotFoundException(ErrorCode errorCode, String errorMessage, String displayMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.displayMessage = displayMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
