package com.bankingservice.banking.exception;

import com.bankingservice.banking.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpExpiredException extends Exception{
    private ErrorCode errorCode;
    private String errorMessage;
    private String displayMessage;
}
