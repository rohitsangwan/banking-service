package com.bankingservice.banking.services;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.services.servicehelper.AccountServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    private AccountServiceHelper accountServiceHelper;

    /**
     * insert details for user registration
     *
     * @param registerRequestDTO
     * @return registerUserResponseDTO
     */
    public RegisterUserResponseDTO insertDetailsForRegistration(RegisterRequestDTO registerRequestDTO) throws InsertionFailedException, DataIntegrityViolationException {
        try {
            logger.info("[insertDetailsForRegistration] Registering a new user: {}", registerRequestDTO);
            RegisterUserModel entity = accountServiceHelper.makeRegisterEntity(registerRequestDTO);
            RegisterUserModel registerUserModel = accountServiceHelper.saveRegisterModel(entity);
            RegisterUserResponseDTO registerUserResponseDTO = accountServiceHelper.makeRegisterUserResponseDTO(registerUserModel);
            return registerUserResponseDTO;
        } catch (DataIntegrityViolationException e) {
            logger.error("[insertDetailsForRegistration] details insertion failed in db for request: {}", registerRequestDTO, e);
            throw new InsertionFailedException(ErrorCode.USER_REGISTRATION_FAILED,
                    ErrorCode.USER_REGISTRATION_FAILED.getErrorMessage(),
                    ErrorCode.USER_REGISTRATION_FAILED.getDisplayMessage());
        }
    }


        /**
         * insert details for user on boarding
         *
         * @param onBoardRequestDTO
         * @return onBoardResponseDTO
         */
        public OnBoardResponseDTO insertDetailsForOnBoarding (OnBoardRequestDTO onBoardRequestDTO) throws
                UserIdNotFoundException, InsertionFailedException {
            try {
                logger.info("[insertDetailsForOnBoarding] On boarding a user: {}", onBoardRequestDTO);
                UserOnBoardModel user = accountServiceHelper.makeOnBoardingEntity(onBoardRequestDTO);
                UserOnBoardModel userOnBoardModel = accountServiceHelper.saveOnBoardModel(user);
                OnBoardResponseDTO onBoardResponseDTO = accountServiceHelper.makeOnBoardingResponseDTO(userOnBoardModel);
                return onBoardResponseDTO;
            } catch (UserIdNotFoundException e) {
                logger.error("[insertDetailsForOnBoarding] details insertion failed in mysql for request: {}", onBoardRequestDTO, e);
                throw new UserIdNotFoundException(ErrorCode.USER_ONBOARD_FAILED,
                        ErrorCode.USER_ONBOARD_FAILED.getErrorMessage(),
                        ErrorCode.USER_ONBOARD_FAILED.getDisplayMessage());
            } catch (DataIntegrityViolationException | InsertionFailedException e) {
                logger.info("[saveRegisterModel] account already exists for userId: {}", onBoardRequestDTO.getUserId());
                throw new InsertionFailedException(ErrorCode.USER_ONBOARD_FAILED,
                        ErrorCode.USER_ONBOARD_FAILED.getErrorMessage(),
                        ErrorCode.USER_ONBOARD_FAILED.getDisplayMessage());
            }
        }
    }
