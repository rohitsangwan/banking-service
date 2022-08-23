package com.bankingservice.banking.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponseDTO<T extends Serializable> implements Serializable {
    private T data;
    private MetaDTO metaDTO;
}
