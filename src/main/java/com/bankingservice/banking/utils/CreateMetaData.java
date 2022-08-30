package com.bankingservice.banking.utils;

import com.bankingservice.banking.dto.response.MetaDTO;
import com.bankingservice.banking.enums.SuccessCode;
import org.slf4j.MDC;

import static com.bankingservice.banking.constants.Constants.REQUEST_ID;
import static com.bankingservice.banking.constants.Constants.RESPONSE_ID;

public class CreateMetaData {

    /**
     * common method to create meta data for response
     *
     * @return MetaDTO
     */

    public static MetaDTO createSuccessMetaData(){
        return new MetaDTO(SuccessCode.SUCCESS_CODE.getCode(), SuccessCode.SUCCESS_CODE.getMessage(), MDC.get(REQUEST_ID), MDC.get(RESPONSE_ID));
    }
}
