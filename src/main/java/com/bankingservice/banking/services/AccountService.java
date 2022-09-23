package com.bankingservice.banking.services;

import com.bankingservice.banking.client.TransactionClient;
import com.bankingservice.banking.dao.RegisterUserCacheDao;
import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.request.SetPinRequestDTO;
import com.bankingservice.banking.dto.response.*;
import com.bankingservice.banking.dto.response.transaction.ActivateAccountDTO;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.exception.*;
import com.bankingservice.banking.models.mysql.CardModel;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.services.cache.RegisterUserCacheService;
import com.bankingservice.banking.services.servicehelper.AccountServiceHelper;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private RegisterUserCacheDao registerUserCacheDao;

    @Autowired
    private TransactionClient transactionClient;

    /**
     * insert details for user registration
     *
     * @param registerRequestDTO
     * @return registerUserResponseDTO
     * @throws InsertionFailedException
     * @throws DataIntegrityViolationException
     */
    public RegisterUserResponseDTO insertDetailsForRegistration(RegisterRequestDTO registerRequestDTO) throws InsertionFailedException, DataIntegrityViolationException {
        try {
            logger.info("[insertDetailsForRegistration] Registering a new user: {}", registerRequestDTO);
            RegisterUserModel entity = accountServiceHelper.convertDtoToRegisterUserModel(registerRequestDTO);
            RegisterUserModel registerUserModel = accountServiceHelper.saveRegisterModel(entity);
            registerUserCacheDao.saveUserRegistrationDetails(entity);
            logger.info("[insertDetailsForRegistration] details added to cache : {}", registerRequestDTO);
            RegisterUserResponseDTO registerUserResponseDTO = accountServiceHelper.convertModelToRegisterResponseDto(registerUserModel);
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
     * @throws InsertionFailedException
     * @throws UserIdNotFoundException
     */
    public OnBoardResponseDTO insertDetailsForOnBoarding(OnBoardRequestDTO onBoardRequestDTO) throws
            UserIdNotFoundException, InsertionFailedException, ServiceCallException {
        try {
            logger.info("[insertDetailsForOnBoarding] On boarding a user: {}", onBoardRequestDTO);
            UserOnBoardModel user = accountServiceHelper.convertDtoToUserOnBoardModel(onBoardRequestDTO);
            UserOnBoardModel userOnBoardModel = accountServiceHelper.saveOnBoardModel(user);
            OnBoardResponseDTO onBoardResponseDTO = accountServiceHelper.convertModelToOnBoardResponseDto(userOnBoardModel);
            ActivateAccountDTO activateAccountDTO = new ActivateAccountDTO();
            activateAccountDTO.setAccountNumber(onBoardResponseDTO.getAccountNumber());
            transactionClient.activateAccount(activateAccountDTO, onBoardResponseDTO.getUserId());
            return onBoardResponseDTO;
        } catch (DataIntegrityViolationException | InsertionFailedException e) {
            logger.error("[saveRegisterModel] account already exists for userId: {}", onBoardRequestDTO.getUserId());
            throw new InsertionFailedException(ErrorCode.USER_ONBOARD_FAILED,
                    ErrorCode.USER_ONBOARD_FAILED.getErrorMessage(),
                    ErrorCode.USER_ONBOARD_FAILED.getDisplayMessage());
        } catch (ServiceCallException e) {
            throw new ServiceCallException(ErrorCode.BANK_ACCOUNT_ACTIVATION_FAILED,
                    String.format(ErrorCode.BANK_ACCOUNT_ACTIVATION_FAILED.getErrorMessage(), onBoardRequestDTO.getUserId()),
                    ErrorCode.BANK_ACCOUNT_ACTIVATION_FAILED.getDisplayMessage());
        }
    }

    /**
     * fetch the user's details
     *
     * @param userId
     * @return userDetailsResponseDTO
     * @throws UserNotFoundException
     */
    public UserDetailsResponseDTO getUserDetails(String userId) throws UserNotFoundException {
        try {
            logger.info("[getUserDetails] fetching the user details with user Id : {}", userId);
            UserDetailsResponseDTO userDetailsResponseDTO = accountServiceHelper.getDetails(userId);
            return userDetailsResponseDTO;
        } catch (UserNotFoundException e) {
            logger.error("[getUserDetails] user does not exist for user ID : {}", userId);
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND,
                    String.format(ErrorCode.USER_NOT_FOUND.getErrorMessage(), userId),
                    ErrorCode.USER_NOT_FOUND.getDisplayMessage());
        }
    }
}
