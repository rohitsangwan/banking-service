package com.bankingservice.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetaDTO implements Serializable {
    private String code;
    private String message;
    private String responseId;
    private String requestId;
}
