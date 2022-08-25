package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.MetaDTO;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.SuccessCode;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<BaseResponseDTO> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) throws InsertionFailedException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        logger.info("[registerUser] user registration started");
        baseResponseDTO.setData(accountService.insertDetailsForRegistration(registerRequestDTO));
        baseResponseDTO.setMetaDTO(createSuccessMetaData());
        logger.info("[registerUser] user successfully registered");
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
        logger.info("[onBoardUser] user on boarding started");
        baseResponseDTO.setData(accountService.insertDetailsForOnBoarding(onBoardRequestDTO));
        baseResponseDTO.setMetaDTO(createSuccessMetaData());
        logger.info("[onBoardUser] On boarding completed!");
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }


    /**
     * entry point controller to health check
     *
     * @return baseResponseDTO
     */

    @GetMapping("/health")
    public ResponseEntity<BaseResponseDTO> healthCheck(){
        logger.info("[healthCheck] health check");
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData("ok");
        baseResponseDTO.setMetaDTO(createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    /**
     * common method to create meta data for response
     *
     * @return MetaDTO
     */

    private MetaDTO createSuccessMetaData(){
        return new MetaDTO(SuccessCode.SUCCESS_CODE.getCode(), SuccessCode.SUCCESS_CODE.getMessage(), "Res", "Req");
    }
}
