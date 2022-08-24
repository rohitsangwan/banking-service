package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.MetaDTO;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import com.bankingservice.banking.services.AccountService;
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
    @Autowired
    private AccountService accountService;

    /**
     * entry point controller to register a user
     *
     * @param registerRequestDTO
     * @return baseResponseDTO
     */
    @PostMapping("/register")
    public BaseResponseDTO registerUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData(accountService.insertDetailsForRegistration(registerRequestDTO));
        baseResponseDTO.setMetaDTO(createMetaData());
        return baseResponseDTO;
    }

    /**
     * entry point controller to onboard a user
     *
     * @param onBoardRequestDTO
     * @return baseResponseDTO
     */

    @PostMapping("/onboard")
    public BaseResponseDTO onBoardUser(@RequestBody OnBoardRequestDTO onBoardRequestDTO){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData(accountService.insertDetailsForOnBoarding(onBoardRequestDTO));
        baseResponseDTO.setMetaDTO(createMetaData());
        return baseResponseDTO;
    }
    

    /**
     * entry point controller to health check
     *
     * @return baseResponseDTO
     */

    @GetMapping("/health")
    public BaseResponseDTO healthCheck(){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData("ok");
        baseResponseDTO.setMetaDTO(createMetaData());
        return baseResponseDTO;
    }

    /**
     * common method to create meta data for response
     *
     * @return
     */

    private MetaDTO createMetaData(){
        return new MetaDTO("123", "Mes", "Res", "Req");
    }
}
