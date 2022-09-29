package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.request.UpdateBalanceRequestDTO;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.exception.UserNotFoundException;
import com.bankingservice.banking.services.TransactionService;
import com.bankingservice.banking.utils.CreateMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private TransactionService transactionService;

    @PutMapping("/update/account-balance")
    public ResponseEntity<BaseResponseDTO> updateAccountBalance(@RequestBody UpdateBalanceRequestDTO updateBalanceRequestDTO) throws UserNotFoundException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        transactionService.updateBalance(updateBalanceRequestDTO);
        baseResponseDTO.setMetaDTO(CreateMetaData.createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

}
