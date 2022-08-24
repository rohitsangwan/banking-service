package com.bankingservice.banking.services;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import com.bankingservice.banking.services.servicehelper.AccountServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Account Service
 */
@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private RegisterUserRepository registerUserRepository;

    @Autowired
    private UserOnBoardRepository userOnBoardRepository;

    @Autowired
    private AccountServiceHelper accountServiceHelper;

    /**
     * insert details for user registration
     *
     * @param registerRequestDTO
     * @return registerUserResponseDTO
     */
    public RegisterUserResponseDTO insertDetailsForRegistration(RegisterRequestDTO registerRequestDTO) {
        logger.info("[insertDetailsForRegistration] Registering a new user: {}", registerRequestDTO);
        RegisterUserModel entity = registerUserRepository.save(accountServiceHelper.makeRegisterEntity(registerRequestDTO));
        RegisterUserResponseDTO registerUserResponseDTO = accountServiceHelper.makeRegisterUserResponseDTO(entity);
        return registerUserResponseDTO;
    }

    /**
     * insert details for user on boarding
     *
     * @param onBoardRequestDTO
     * @return onBoardResponseDTO
     */
    public OnBoardResponseDTO insertDetailsForOnBoarding(OnBoardRequestDTO onBoardRequestDTO) {
        logger.info("[insertDetailsForOnBoarding] On boarding a user: {}", onBoardRequestDTO);
        UserOnBoardModel user = userOnBoardRepository.save(accountServiceHelper.makeOnBoardingEntity(onBoardRequestDTO));
        OnBoardResponseDTO onBoardResponseDTO = accountServiceHelper.makeOnBoardingResponseDTO(user);
        return onBoardResponseDTO;
    }
}
