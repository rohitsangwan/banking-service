package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.utils.CreateMetaData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bankingservice.banking.constants.Constants.OK;

@RestController
@RequestMapping("/account/v1")
public class HealthCheckController {

    /**
     * entry point controller to health check
     *
     * @return baseResponseDTO
     */

    @GetMapping("/health")
    public ResponseEntity<BaseResponseDTO> healthCheck(){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData(OK);
        baseResponseDTO.setMetaDTO(CreateMetaData.createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }
}
