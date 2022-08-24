package com.bankingservice.banking.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponseDTO<T extends Serializable> implements Serializable {
    /**
     * store data information for response
     */
    private T data;

    /**
     * store meta information for response
     */
    private MetaDTO metaDTO;
}
