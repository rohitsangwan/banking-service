package com.bankingservice.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MetaDTO implements Serializable {
    private String code;
    private String message;
    private String responseId;
    private String requestId;

    /**
     * Instantiates a new Meta dto
     *
     * @param code
     * @param message
     * @param responseId
     * @param requestId
     */
    public MetaDTO(String code, String message, String responseId, String requestId) {
        this.code = code;
        this.message = message;
        this.responseId = responseId;
        this.requestId = requestId;
    }
}
