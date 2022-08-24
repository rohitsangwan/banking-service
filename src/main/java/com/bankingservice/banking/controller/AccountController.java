package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.MetaDTO;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BaseResponseDTO registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) throws InsertionFailedException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        logger.info("[registerUser] user registration started");
        baseResponseDTO.setData(accountService.insertDetailsForRegistration(registerRequestDTO));
        baseResponseDTO.setMetaDTO(createMetaData());
        logger.info("[registerUser] user successfully registered");
        return baseResponseDTO;
    }

    /**
     * entry point controller to onboard a user
     *
     * @param onBoardRequestDTO
     * @return baseResponseDTO
     */

    @PostMapping("/onboard")
    public BaseResponseDTO onBoardUser(@RequestBody OnBoardRequestDTO onBoardRequestDTO) throws InsertionFailedException, UserIdNotFoundException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        logger.info("[onBoardUser] user on boarding started");
        baseResponseDTO.setData(accountService.insertDetailsForOnBoarding(onBoardRequestDTO));
        baseResponseDTO.setMetaDTO(createMetaData());
        logger.info("[onBoardUser] On boarding completed!");
        return baseResponseDTO;
    }


    /**
     * entry point controller to health check
     *
     * @return baseResponseDTO
     */

    @GetMapping("/health")
    public BaseResponseDTO healthCheck(){
        logger.info("[healthCheck] health check");
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData("ok");
        baseResponseDTO.setMetaDTO(createMetaData());
        return baseResponseDTO;
    }

    /**
     * common method to create meta data for response
     *
     * @return MetaDTO
     */

    private MetaDTO createMetaData(){
        return new MetaDTO("123", "Mes", "Res", "Req","Dis");
    }
}
