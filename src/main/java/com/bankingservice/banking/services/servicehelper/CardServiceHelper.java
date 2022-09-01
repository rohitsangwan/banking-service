package com.bankingservice.banking.services.servicehelper;

import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.dto.request.SetPinRequestDTO;
import com.bankingservice.banking.dto.response.CardResponseDTO;
import com.bankingservice.banking.enums.CardState;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.exception.CardNotFoundException;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.exception.UserNotFoundException;
import com.bankingservice.banking.models.mysql.CardModel;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.repository.CardRepository;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.utils.CardUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CardServiceHelper {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private RegisterUserRepository registerUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceHelper.class);

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

    /**
     * fetch the card details
     *
     * @param userId
     * @return cardModel
     * @throws CardNotFoundException
     * @throws UserNotFoundException
     */
    public CardModel findCardDetails(String userId) throws CardNotFoundException, UserNotFoundException {
        logger.info("[findCardDetails] fetching card details for user Id: {}", userId);
        Optional<RegisterUserModel> registerUserModel = registerUserRepository.findByUserId(userId);
        if (registerUserModel.isPresent()) {
            Optional<CardModel> cardModel = cardRepository.findByCardId(registerUserModel.get().getId());
            if (cardModel.isPresent()) {
                return cardModel.get();
            } else {
                logger.error("[findCardDetails] card does not exist for user ID : {}", userId);
                throw new CardNotFoundException(ErrorCode.CARD_NOT_FOUND,
                        String.format(ErrorCode.CARD_NOT_FOUND.getErrorMessage(), userId),
                        ErrorCode.CARD_NOT_FOUND.getDisplayMessage());
            }
        } else {
            logger.error("[findCardDetails] user does not exist for user ID : {}", userId);
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND,
                    String.format(ErrorCode.USER_NOT_FOUND.getErrorMessage(), userId),
                    ErrorCode.USER_NOT_FOUND.getDisplayMessage());
        }
    }
}
