package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.dto.request.SetPinRequestDTO;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.dto.response.CardResponseDTO;
import com.bankingservice.banking.dto.response.SetPinResponseDTO;
import com.bankingservice.banking.enums.CardState;
import com.bankingservice.banking.exception.*;
import com.bankingservice.banking.services.CardService;
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

public class CardControllerTest {
    @Tested
    private CardController cardController;
    @Injectable
    private CardService cardService;
    private static final int cvv = RandomUtils.nextInt();
    private static final int pin = RandomUtils.nextInt();
    private static final String name = RandomStringUtils.randomAlphabetic(10);
    private static final long cardNumber = RandomUtils.nextLong();
    private static final String userId = RandomStringUtils.randomAlphanumeric(10);
    private static final HttpSession session = null;

    CardRequestDTO cardRequestDTO;
    CardResponseDTO cardResponseDTO;
    SetPinRequestDTO setPinRequestDTO;
    SetPinResponseDTO setPinResponseDTO;

    @BeforeMethod
    public void setUp() {
        cardResponseDTO = new CardResponseDTO();
        cardResponseDTO.setCardNumber(cardNumber);
        cardResponseDTO.setCardState(CardState.DISABLED);
        cardResponseDTO.setCvv(cvv);
        cardResponseDTO.setName(name);

        cardRequestDTO = new CardRequestDTO();
        cardRequestDTO.setUserId(userId);

        setPinRequestDTO = new SetPinRequestDTO();
        setPinRequestDTO.setPin(pin);
        setPinRequestDTO.setCardNumber(cardNumber);

        setPinResponseDTO = new SetPinResponseDTO();
        setPinResponseDTO.setPin(pin);
        setPinResponseDTO.setName(name);
        setPinResponseDTO.setCvv(cvv);
        setPinResponseDTO.setCardNumber(cardNumber);
        setPinResponseDTO.setCardState(CardState.ACTIVE);
    }

    @Test
    public void generateCard() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            cardService.generateCardDetails((CardRequestDTO) any);
            result = cardResponseDTO;
        }};
        BaseResponseDTO baseResponseDTO = cardController.generateCard(cardRequestDTO).getBody();
        Assert.assertNotNull(baseResponseDTO);
        Assert.assertNotNull(baseResponseDTO.getData());
        Assert.assertNotNull(baseResponseDTO.getMetaDTO());
        new Verifications() {{
            cardService.generateCardDetails((CardRequestDTO) any);
            times = 1;
        }};
    }

    @Test
    public void setPin() throws CardNotFoundException {
        new Expectations() {{
            cardService.setPin((SetPinRequestDTO) any);
            result = setPinResponseDTO;
        }};
        BaseResponseDTO baseResponseDTO = cardController.setPin(setPinRequestDTO).getBody();
        Assert.assertNotNull(baseResponseDTO);
        Assert.assertNotNull(baseResponseDTO.getData());
        Assert.assertNotNull(baseResponseDTO.getMetaDTO());
        new Verifications() {{
            cardService.setPin((SetPinRequestDTO) any);
            times = 1;
        }};
    }

    @Test
    public void getCardInfo() throws UserNotFoundException, CardNotFoundException, InvalidOtpException {
        new Expectations() {{
            cardService.getCardDetails((CardRequestDTO) any, (HttpSession) any);
            result = cardResponseDTO;
        }};
        BaseResponseDTO baseResponseDTO = cardController.getCardInfo(cardRequestDTO, session).getBody();
        Assert.assertNotNull(baseResponseDTO);
        Assert.assertNotNull(baseResponseDTO.getData());
        Assert.assertNotNull(baseResponseDTO.getMetaDTO());
        new Verifications() {{
            cardService.getCardDetails((CardRequestDTO) any,  (HttpSession) any);
            times = 1;
        }};
    }

}
