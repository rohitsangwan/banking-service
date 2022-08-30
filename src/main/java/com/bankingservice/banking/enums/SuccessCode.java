package com.bankingservice.banking.enums;

import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
public enum SuccessCode {
    /**
     * Success code
     */
    SUCCESS_CODE("BS_ACC_202002", "Success", HttpStatus.OK);

    private final String code;
    private final String message;
    private final HttpStatus httpStatusCode;

    private SuccessCode(String code, String message, HttpStatus httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
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

    /**
     * Gets httpStatusCode
     *
     * @return the httpStatusCode
     */
    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}
