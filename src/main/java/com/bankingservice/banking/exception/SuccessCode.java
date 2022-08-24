package com.bankingservice.banking.exception;

import lombok.ToString;

@ToString
public enum SuccessCode {
    /**
     * Success code
     */
    SUCCESS_CODE("BS_ACC_20202", "Success");

    private final String code;
    private final String message;

    private SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

}
