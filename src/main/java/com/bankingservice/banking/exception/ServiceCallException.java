package com.bankingservice.banking.exception;

import com.bankingservice.banking.enums.ErrorCode;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCallException extends Exception {
    private ErrorCode errorCode;
    private String errorMessage;
    private String displayMessage;
}
