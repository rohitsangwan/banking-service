package com.bankingservice.banking.services.servicehelper;

import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.request.SetPinRequestDTO;
import com.bankingservice.banking.dto.response.CardResponseDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.enums.CardState;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.exception.CardNotFoundException;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.models.mysql.CardModel;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.CardRepository;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import com.bankingservice.banking.utils.CardUtil;
import com.bankingservice.banking.utils.UniqueValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

import static com.bankingservice.banking.constants.Constants.REG;

@Component
public class AccountServiceHelper {
    @Autowired
    private RegisterUserRepository registerUserRepository;
    @Autowired
    private UserOnBoardRepository userOnBoardRepository;
    @Autowired
    private CardRepository cardRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceHelper.class);

    /**
     * save the register user model
     *
     * @param user
     * @return registerUserModel
     */
    public RegisterUserModel saveRegisterModel(RegisterUserModel user) throws InsertionFailedException {
        try {
            logger.info("[saveRegisterModel] saving the registration details for user: {}", user);
            RegisterUserModel registerUserModel = registerUserRepository.save(user);
            logger.info("[saveRegisterModel] registration details saved for user: {}", user);
            return registerUserModel;
        } catch (DataIntegrityViolationException e) {
            logger.error("[saveRegisterModel] username/email already exists for user: {}", user);
            throw new InsertionFailedException(ErrorCode.USER_REGISTRATION_FAILED,
                    ErrorCode.USER_REGISTRATION_FAILED.getErrorMessage(),
                    ErrorCode.USER_REGISTRATION_FAILED.getDisplayMessage());
        }
    }

    /**
     * save the user on board model
     *
     * @param user
     * @return userOnBoardModel
     */
    public UserOnBoardModel saveOnBoardModel(UserOnBoardModel user) throws InsertionFailedException {
        try {
            logger.info("[saveOnBoardModel] saving the on boarding info for user: {}", user);
            UserOnBoardModel userOnBoardModel = userOnBoardRepository.save(user);
            logger.info("[saveOnBoardModel] on boarding info saved for user: {}", user);
            return userOnBoardModel;
        } catch (DataIntegrityViolationException e) {
            logger.error("[saveRegisterModel] account exists for user: {}", user);
            throw new InsertionFailedException(ErrorCode.USER_ONBOARD_FAILED,
                    ErrorCode.USER_ONBOARD_FAILED.getErrorMessage(),
                    ErrorCode.USER_ONBOARD_FAILED.getDisplayMessage());
        }
    }

    /**
     * save the card model
     *
     * @param cardModel
     * @return
     * @throws InsertionFailedException
     */
    public CardModel saveCardModel(CardModel cardModel) throws InsertionFailedException {
        try {
            logger.info("[saveCardModel] saving the card details for : {}", cardModel);
            CardModel card = cardRepository.save(cardModel);
            logger.info("[saveCardModel] card details saved for : {}", cardModel);
            return card;
        } catch (DataIntegrityViolationException e) {
            logger.error("[saveCardModel] card already exists for user: {}", cardModel);
            throw new InsertionFailedException(ErrorCode.CARD_GENERATION_FAILED,
                    ErrorCode.CARD_GENERATION_FAILED.getErrorMessage(),
                    ErrorCode.CARD_GENERATION_FAILED.getDisplayMessage());
        }
    }

    /**
     * convert registerRequestDTO to registerUserModel
     *
     * @param registerRequestDTO
     * @return registerUserModel
     */
    public RegisterUserModel convertDtoToRegisterUserModel(RegisterRequestDTO registerRequestDTO) {
        logger.info("[convertDtoToRegisterUserModel] converting registerRequestDTO {} to registerUserModel", registerRequestDTO);
        RegisterUserModel registerUserModel = new RegisterUserModel();
        BeanUtils.copyProperties(registerRequestDTO, registerUserModel);
        ArrayList<String> uniqueKeyList = UniqueValueUtil.generateUniqueId(REG, 1);
        registerUserModel.setUserId(uniqueKeyList.get(0));
        return registerUserModel;
    }

    /**
     * convert registerUserModel to registerUserResponseDTO
     *
     * @param registerUserModel
     * @return registerUserResponseDTO
     */
    public RegisterUserResponseDTO convertModelToRegisterResponseDto(RegisterUserModel registerUserModel) {
        logger.info("[convertModelToRegisterResponseDto] converting registerUserModel {} to registerUserResponseDTO", registerUserModel);
        RegisterUserResponseDTO registerUserResponseDTO = new RegisterUserResponseDTO();
        BeanUtils.copyProperties(registerUserModel, registerUserResponseDTO);
        return registerUserResponseDTO;
    }

    /**
     * convert onBoardRequestDTO to userOnBoardModel
     *
     * @param onBoardRequestDTO
     * @return userOnBoardModel
     */
    public UserOnBoardModel convertDtoToUserOnBoardModel(OnBoardRequestDTO onBoardRequestDTO) throws UserIdNotFoundException {
        logger.info("[convertDtoToUserOnBoardModel] converting onBoardRequestDTO {} to userOnBoardModel", onBoardRequestDTO);
        UserOnBoardModel userOnBoardModel = new UserOnBoardModel();
        BeanUtils.copyProperties(onBoardRequestDTO, userOnBoardModel);
        Optional<RegisterUserModel> registerUserModel = registerUserRepository.findByUserId(onBoardRequestDTO.getUserId());
        if (registerUserModel.isPresent()) {
            userOnBoardModel.setRegisterUserId(registerUserModel.get().getId());
            return userOnBoardModel;
        } else {
            logger.error("[convertDtoToUserOnBoardModel] user does not exist for userId: {}", onBoardRequestDTO.getUserId());
            throw new UserIdNotFoundException(ErrorCode.USER_ID_NOT_FOUND,
                    String.format(ErrorCode.USER_ID_NOT_FOUND.getErrorMessage(), onBoardRequestDTO.getUserId()),
                    ErrorCode.USER_ID_NOT_FOUND.getDisplayMessage());
        }
    }

    /**
     * converting userOnBoardModel to onBoardResponseDTO
     *
     * @param userOnBoardModel
     * @return onBoardResponseDTO
     */
    public OnBoardResponseDTO convertModelToOnBoardResponseDto(UserOnBoardModel userOnBoardModel) throws UserIdNotFoundException {
        logger.info("[convertModelToOnBoardResponseDto] converting userOnBoardModel {} to onBoardResponseDTO", userOnBoardModel);
        OnBoardResponseDTO onBoardResponseDTO = new OnBoardResponseDTO();
        BeanUtils.copyProperties(userOnBoardModel, onBoardResponseDTO);
        Optional<RegisterUserModel> registerUserModelOptional = registerUserRepository.findById(userOnBoardModel.getRegisterUserId());
        if (registerUserModelOptional.isPresent()) {
            RegisterUserModel registerUserModel = registerUserModelOptional.get();
            BeanUtils.copyProperties(registerUserModel, onBoardResponseDTO);
            return onBoardResponseDTO;
        } else {
            logger.error("[convertModelToOnBoardResponseDto] user does not exist for userId: {}", userOnBoardModel.getRegisterUserId());
            throw new UserIdNotFoundException(ErrorCode.USER_ID_NOT_FOUND,
                    String.format(ErrorCode.USER_ID_NOT_FOUND.getErrorMessage(), userOnBoardModel.getRegisterUserId()),
                    ErrorCode.USER_ID_NOT_FOUND.getDisplayMessage());
        }
    }

    /**
     * converting cardRequestDTO to cardModel
     *
     * @param cardRequestDTO
     * @return
     * @throws UserIdNotFoundException
     */
    public CardModel convertDtoToCardModel(CardRequestDTO cardRequestDTO) throws UserIdNotFoundException {
        logger.info("[convertDtoToCardModel] converting cardRequestDTO {} to cardModel", cardRequestDTO);
        CardModel cardModel = new CardModel();
        Optional<RegisterUserModel> registerUserModel = registerUserRepository.findByUserId(cardRequestDTO.getUserId());
        if (registerUserModel.isPresent()) {
            cardModel.setCardId(registerUserModel.get().getId());
            cardModel.setName(registerUserModel.get().getName());
            cardModel.setCardNumber(CardUtil.generateCardNumber());
            cardModel.setCvv(CardUtil.generateCvv());
            cardModel.setCardState(CardState.DISABLED);
            return cardModel;
        } else {
            logger.error("[convertDtoToCardModel] user does not exist for userId: {}", cardRequestDTO.getUserId());
            throw new UserIdNotFoundException(ErrorCode.USER_ID_NOT_FOUND,
                    String.format(ErrorCode.USER_ID_NOT_FOUND.getErrorMessage(), cardRequestDTO.getUserId()),
                    ErrorCode.USER_ID_NOT_FOUND.getDisplayMessage());
        }
    }

    /**
     * converting cardModel to cardResponseDTO
     *
     * @param card
     * @return
     */
    public CardResponseDTO convertCardModelToDto(CardModel card) {
        logger.info("[convertCardModelToDto] converting cardModel {} to cardResponseDTO", card);
        CardResponseDTO cardResponseDTO = new CardResponseDTO();
        BeanUtils.copyProperties(card, cardResponseDTO);
        return cardResponseDTO;
    }

    /**
     * fetching the card and setting the pin
     *
     * @param setPinRequestDTO
     * @return cardModel
     * @throws CardNotFoundException
     */
    public CardModel setPin(SetPinRequestDTO setPinRequestDTO) throws CardNotFoundException {
        logger.info("[setPin] fetching card details for card number: {}", setPinRequestDTO.getCardNumber());
        Optional<CardModel> card = cardRepository.findByCardNumber(setPinRequestDTO.getCardNumber());
        if (card.isPresent()) {
            CardModel card1 = card.get();
            card1.setPin(setPinRequestDTO.getPin());
            card1.setCardState(CardState.ACTIVE);
            CardModel cardModel = cardRepository.save(card1);
            return cardModel;
        } else {
            logger.error("[setPin] card does not exist for card number: {}", setPinRequestDTO.getCardNumber());
            throw new CardNotFoundException(ErrorCode.CARD_NOT_FOUND,
                    String.format(ErrorCode.CARD_NOT_FOUND.getErrorMessage(), setPinRequestDTO.getCardNumber()),
                    ErrorCode.CARD_NOT_FOUND.getDisplayMessage());
        }
    }
}
