package com.bankingservice.banking.services;

import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.CardResponseDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.models.mysql.CardModel;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.services.servicehelper.AccountServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

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
     * @throws InsertionFailedException
     * @throws DataIntegrityViolationException
     */
    public RegisterUserResponseDTO insertDetailsForRegistration(RegisterRequestDTO registerRequestDTO) throws InsertionFailedException, DataIntegrityViolationException {
        try {
            logger.info("[insertDetailsForRegistration] Registering a new user: {}", registerRequestDTO);
            RegisterUserModel entity = accountServiceHelper.convertDtoToRegisterUserModel(registerRequestDTO);
            RegisterUserModel registerUserModel = accountServiceHelper.saveRegisterModel(entity);
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
            UserIdNotFoundException, InsertionFailedException {
        try {
            logger.info("[insertDetailsForOnBoarding] On boarding a user: {}", onBoardRequestDTO);
            UserOnBoardModel user = accountServiceHelper.convertDtoToUserOnBoardModel(onBoardRequestDTO);
            UserOnBoardModel userOnBoardModel = accountServiceHelper.saveOnBoardModel(user);
            OnBoardResponseDTO onBoardResponseDTO = accountServiceHelper.convertModelToOnBoardResponseDto(userOnBoardModel);
            return onBoardResponseDTO;
        } catch (DataIntegrityViolationException | InsertionFailedException e) {
            logger.info("[saveRegisterModel] account already exists for userId: {}", onBoardRequestDTO.getUserId());
            throw new InsertionFailedException(ErrorCode.USER_ONBOARD_FAILED,
                    ErrorCode.USER_ONBOARD_FAILED.getErrorMessage(),
                    ErrorCode.USER_ONBOARD_FAILED.getDisplayMessage());
        }
    }

    /**
     * generate card details
     *
     * @param cardRequestDTO
     * @return cardResponseDTO
     * @throws InsertionFailedException
     * @throws UserIdNotFoundException
     */
    public CardResponseDTO generateCardDetails(CardRequestDTO cardRequestDTO) throws InsertionFailedException, UserIdNotFoundException {
        try {
            logger.info("[generateCardDetails] generating card for user with user ID: {}", cardRequestDTO);
            CardModel cardModel = accountServiceHelper.convertDtoToCardModel(cardRequestDTO);
            CardModel card = accountServiceHelper.saveCardModel(cardModel);
            CardResponseDTO cardResponseDTO = accountServiceHelper.convertCardModelToDto(card);
            return cardResponseDTO;
        } catch (DataIntegrityViolationException | InsertionFailedException e){
            logger.info("[generateCardDetails] card already exists for userId: {}", cardRequestDTO.getUserId());
            throw new InsertionFailedException(ErrorCode.CARD_GENERATION_FAILED,
                    ErrorCode.CARD_GENERATION_FAILED.getErrorMessage(),
                    ErrorCode.CARD_GENERATION_FAILED.getDisplayMessage());
        } catch (UserIdNotFoundException e) {
            logger.info("[generateCardDetails] user does not exist with user ID: {}", cardRequestDTO.getUserId());
            throw new UserIdNotFoundException(ErrorCode.USER_ID_NOT_FOUND,
                    String.format(ErrorCode.USER_ID_NOT_FOUND.getErrorMessage(), cardRequestDTO.getUserId()),
                    ErrorCode.USER_ID_NOT_FOUND.getDisplayMessage());
        }
    }
}
