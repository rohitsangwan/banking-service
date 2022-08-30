package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.services.AccountService;
import com.bankingservice.banking.utils.CreateMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for account api
 *
 * @author Rohit Sangwan
 */
@RestController
@RequestMapping("/account/v1")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    /**
     * entry point controller to register a user
     *
     * @param registerRequestDTO
     * @return baseResponseDTO
     */
    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) throws InsertionFailedException, DataIntegrityViolationException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData(accountService.insertDetailsForRegistration(registerRequestDTO));
        baseResponseDTO.setMetaDTO(CreateMetaData.createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    /**
     * entry point controller to onboard a user
     *
     * @param onBoardRequestDTO
     * @return baseResponseDTO
     */

    @PostMapping("/onboard")
    public ResponseEntity<BaseResponseDTO> onBoardUser(@RequestBody OnBoardRequestDTO onBoardRequestDTO) throws InsertionFailedException, UserIdNotFoundException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData(accountService.insertDetailsForOnBoarding(onBoardRequestDTO));
        baseResponseDTO.setMetaDTO(CreateMetaData.createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

}
