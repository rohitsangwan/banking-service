package com.bankingservice.banking.services;

import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.dto.request.SetPinRequestDTO;
import com.bankingservice.banking.dto.response.CardResponseDTO;
import com.bankingservice.banking.dto.response.SetPinResponseDTO;
import com.bankingservice.banking.enums.CardState;
import com.bankingservice.banking.exception.*;
import com.bankingservice.banking.models.mysql.CardModel;
import com.bankingservice.banking.services.servicehelper.CardServiceHelper;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpSession;

public class CardServiceTest {
    @Tested
    private CardService cardService;
    @Injectable
    private CardServiceHelper cardServiceHelper;

    private CardModel cardModel;

    private static final int pin = RandomUtils.nextInt();
    private static final int cvv = RandomUtils.nextInt();
    private static final String name = RandomStringUtils.randomAlphabetic(10);
    private static final int cardId = RandomUtils.nextInt();
    private static final long cardNumber = RandomUtils.nextLong();
    private static final String userId = RandomStringUtils.randomAlphanumeric(10);
    private static final SetPinRequestDTO setPinRequestDTO = new SetPinRequestDTO();
    private static final CardRequestDTO cardRequestDTO = new CardRequestDTO();
    private static final HttpSession session = null;

    @BeforeMethod
    public void setUp() {
        cardModel = new CardModel();
        cardModel.setCardState(CardState.DISABLED);
        cardModel.setCvv(cvv);
        cardModel.setCardNumber(cardNumber);
        cardModel.setName(name);
        cardModel.setCardId(cardId);
        setPinRequestDTO.setCardNumber(cardNumber);
        setPinRequestDTO.setPin(pin);
        cardRequestDTO.setUserId(userId);
    }

    @Test
    public void testGenerateCardDetails() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            cardServiceHelper.saveCardModel((CardModel) any);
            result = cardModel;
        }};
        CardResponseDTO cardResponseDTO = cardService.generateCardDetails(cardRequestDTO);
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            cardServiceHelper.saveCardModel((CardModel) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = InsertionFailedException.class)
    public void testGenerateCardDetailsInsertionFailedException() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            cardServiceHelper.saveCardModel((CardModel) any);
            result = new InsertionFailedException();
        }};
        CardResponseDTO cardResponseDTO = cardService.generateCardDetails(cardRequestDTO);
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            cardServiceHelper.saveCardModel((CardModel) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = UserIdNotFoundException.class)
    public void testGenerateCardDetailsUserIdNotFoundException() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            cardServiceHelper.saveCardModel((CardModel) any);
            result = new UserIdNotFoundException();
        }};
        CardResponseDTO cardResponseDTO = cardService.generateCardDetails(cardRequestDTO);
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            cardServiceHelper.saveCardModel((CardModel) any);
            times = 1;
        }};
    }

    @Test
    public void testSetPin() throws CardNotFoundException {
        new Expectations() {{
            cardServiceHelper.setPin((SetPinRequestDTO) any);
            result = cardModel;
        }};
        SetPinResponseDTO setPinResponseDTO = cardService.setPin(setPinRequestDTO);
        Assert.assertNotNull(setPinResponseDTO);
        new Verifications() {{
            cardServiceHelper.setPin((SetPinRequestDTO) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = CardNotFoundException.class)
    public void testSetPinCardNotFoundException() throws CardNotFoundException {
        new Expectations() {{
            cardServiceHelper.setPin((SetPinRequestDTO) any);
            result = new CardNotFoundException();
        }};
        SetPinResponseDTO setPinResponseDTO = cardService.setPin(setPinRequestDTO);
        Assert.assertNotNull(setPinResponseDTO);
        new Verifications() {{
            cardServiceHelper.setPin((SetPinRequestDTO) any);
            times = 1;
        }};
    }

    @Test
    public void testGetCardDetails() throws UserNotFoundException, CardNotFoundException, InvalidOtpException, OtpExpiredException {
        new Expectations() {{
            cardServiceHelper.findCardDetails(userId);
            result = cardModel;
        }};
        CardResponseDTO cardResponseDTO = cardService.getCardDetails(cardRequestDTO);
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            cardServiceHelper.findCardDetails(userId);
            times = 1;
        }};
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void testGetCardDetailsUserNotFoundException() throws UserNotFoundException, CardNotFoundException, InvalidOtpException, OtpExpiredException {
        new Expectations() {{
            cardServiceHelper.findCardDetails(userId);
            result = new UserNotFoundException();
        }};
        CardResponseDTO cardResponseDTO = cardService.getCardDetails(cardRequestDTO);
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            cardServiceHelper.findCardDetails(userId);
            times = 1;
        }};
    }

    @Test(expectedExceptions = CardNotFoundException.class)
    public void testGetCardDetailsCardNotFoundException() throws UserNotFoundException, CardNotFoundException, InvalidOtpException, OtpExpiredException {
        new Expectations() {{
            cardServiceHelper.findCardDetails(userId);
            result = new CardNotFoundException();
        }};
        CardResponseDTO cardResponseDTO = cardService.getCardDetails(cardRequestDTO);
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            cardServiceHelper.findCardDetails(userId);
            times = 1;
        }};
    }

}
