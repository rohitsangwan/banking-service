package com.bankingservice.banking.services;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.exception.ErrorCode;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
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
    public RegisterUserResponseDTO insertDetailsForRegistration(RegisterRequestDTO registerRequestDTO) throws InsertionFailedException {
        try {
            logger.info("[insertDetailsForRegistration] Registering a new user: {}", registerRequestDTO);
            RegisterUserModel entity = registerUserRepository.save(accountServiceHelper.makeRegisterEntity(registerRequestDTO));
            RegisterUserResponseDTO registerUserResponseDTO = accountServiceHelper.makeRegisterUserResponseDTO(entity);
            return registerUserResponseDTO;
        } catch (Exception e) {
            logger.error("[insertDetailsForRegistration] details insertion failed in mysql for request: {}", registerRequestDTO, e);
            throw new InsertionFailedException(ErrorCode.USER_REGISTRATION_FAILED, ErrorCode.USER_REGISTRATION_FAILED.getErrorMessage(), ErrorCode.USER_REGISTRATION_FAILED.getDisplayMessage());
        }
    }


    /**
     * insert details for user on boarding
     *
     * @param onBoardRequestDTO
     * @return onBoardResponseDTO
     */
    public OnBoardResponseDTO insertDetailsForOnBoarding(OnBoardRequestDTO onBoardRequestDTO) throws UserIdNotFoundException {
        try {
            logger.info("[insertDetailsForOnBoarding] On boarding a user: {}", onBoardRequestDTO);
            UserOnBoardModel user = userOnBoardRepository.save(accountServiceHelper.makeOnBoardingEntity(onBoardRequestDTO));
            OnBoardResponseDTO onBoardResponseDTO = accountServiceHelper.makeOnBoardingResponseDTO(user);
            return onBoardResponseDTO;
        } catch (UserIdNotFoundException e) {
            logger.error("[insertDetailsForOnBoarding] details insertion failed in mysql for request: {}", onBoardRequestDTO, e);
            throw new UserIdNotFoundException(ErrorCode.USER_ONBOARD_FAILED, ErrorCode.USER_ONBOARD_FAILED.getErrorMessage(), ErrorCode.USER_ONBOARD_FAILED.getDisplayMessage());
        }
    }
}
