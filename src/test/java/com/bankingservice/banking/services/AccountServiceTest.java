package com.bankingservice.banking.services;

import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.request.SetPinRequestDTO;
import com.bankingservice.banking.dto.response.CardResponseDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.dto.response.SetPinResponseDTO;
import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.CardState;
import com.bankingservice.banking.enums.Gender;
import com.bankingservice.banking.exception.CardNotFoundException;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.exception.UserNotFoundException;
import com.bankingservice.banking.models.mysql.CardModel;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.CardRepository;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import com.bankingservice.banking.services.servicehelper.AccountServiceHelper;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountServiceTest {
    @Tested
    private AccountService accountService;

    @Injectable
    private RegisterUserRepository registerUserRepository;

    @Injectable
    private UserOnBoardRepository userOnBoardRepository;

    @Injectable
    private CardRepository cardRepository;

    @Injectable
    private AccountServiceHelper accountServiceHelper;

    private List<RegisterUserModel> registerEntities;
    private RegisterUserModel registerUserModel;
    private UserOnBoardModel userOnBoardModel;
    private CardModel cardModel;
    private static final int id = RandomUtils.nextInt();
    private static final int pin = RandomUtils.nextInt();
    private static final int cvv = RandomUtils.nextInt();
    private static final int id1 = RandomUtils.nextInt();
    private static final String name = RandomStringUtils.randomAlphabetic(10);
    private static final String email = RandomStringUtils.randomAlphanumeric(10);
    private static final long mobileNumber = RandomUtils.nextLong();
    private static final String userName = RandomStringUtils.randomAlphanumeric(10);
    private static final String password = RandomStringUtils.randomAlphanumeric(10);
    private static final int age = RandomUtils.nextInt();
    private static final int cardId = RandomUtils.nextInt();
    private static final int registerUserId = RandomUtils.nextInt();
    private static final long aadhaarNumber = RandomUtils.nextLong();
    private static final long cardNumber = RandomUtils.nextLong();
    private static final String address = RandomStringUtils.randomAlphanumeric(10);
    private static final String userId = RandomStringUtils.randomAlphanumeric(10);
    private static final String userId1 = RandomStringUtils.randomAlphanumeric(10);


    @BeforeMethod
    public void setUp() {
        registerUserModel = new RegisterUserModel();
        registerUserModel.setId(id);
        registerUserModel.setName(name);
        registerUserModel.setEmail(email);
        registerUserModel.setMobileNumber(mobileNumber);
        registerUserModel.setUserName(userName);
        registerUserModel.setPassword(password);
        registerUserModel.setUserId(userId);

        RegisterUserModel registerUserModel1 = new RegisterUserModel();
        registerUserModel1.setId(id1);
        registerUserModel1.setName(name);
        registerUserModel1.setEmail(email);
        registerUserModel1.setMobileNumber(mobileNumber);
        registerUserModel1.setUserName(userName);
        registerUserModel1.setPassword(password);
        registerUserModel1.setUserId(userId);

        registerEntities = new ArrayList<>();
        registerEntities.add(registerUserModel);
        registerEntities.add(registerUserModel1);

        userOnBoardModel = new UserOnBoardModel();
        userOnBoardModel.setId(id);
        userOnBoardModel.setAge(age);
        userOnBoardModel.setAadhaarNumber(aadhaarNumber);
        userOnBoardModel.setAddress(address);
        userOnBoardModel.setGender(Gender.MALE);
        userOnBoardModel.setAccountType(AccountType.CURRENT);
        userOnBoardModel.setRegisterUserId(registerUserId);

        cardModel = new CardModel();
        cardModel.setCardState(CardState.DISABLED);
        cardModel.setCvv(cvv);
        cardModel.setCardNumber(cardNumber);
        cardModel.setName(name);
        cardModel.setCardId(cardId);
    }

    @Test
    public void testInsertDetailsForRegistration() throws InsertionFailedException {
        new Expectations() {{
            accountServiceHelper.saveRegisterModel((RegisterUserModel) any);
            result = registerUserModel;
        }};
        RegisterUserResponseDTO registerUserResponseDTO = accountService.insertDetailsForRegistration(makeRegisterRequestDTO());
        Assert.assertNotNull(registerUserResponseDTO);
        new Verifications() {{
            accountServiceHelper.saveRegisterModel((RegisterUserModel) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = InsertionFailedException.class)
    public void testInsertDetailsForRegistrationException() throws InsertionFailedException {
        new Expectations() {{
            accountServiceHelper.saveRegisterModel((RegisterUserModel) any);
            result = new DataIntegrityViolationException("");
        }};
        RegisterUserResponseDTO registerUserResponseDTO = accountService.insertDetailsForRegistration(makeRegisterRequestDTO());
        Assert.assertNotNull(registerUserResponseDTO);
        new Verifications() {{
            accountServiceHelper.saveRegisterModel((RegisterUserModel) any);
            times = 1;
        }};
    }

    @Test
    void testInsertDetailsForOnBoarding() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            accountServiceHelper.saveOnBoardModel((UserOnBoardModel) any);
            result = userOnBoardModel;
        }};
        OnBoardResponseDTO onBoardResponseDTO = accountService.insertDetailsForOnBoarding(makeOnBoardRequestDTO());
        Assert.assertNotNull(onBoardResponseDTO);
        new Verifications() {{
            accountServiceHelper.saveOnBoardModel((UserOnBoardModel) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = InsertionFailedException.class)
    public void testInsertDetailsForOnBoardingInsertionException() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            accountServiceHelper.saveOnBoardModel((UserOnBoardModel) any);
            result = new DataIntegrityViolationException("");
        }};
        OnBoardResponseDTO onBoardResponseDTO = accountService.insertDetailsForOnBoarding(makeOnBoardRequestDTO());
        Assert.assertNotNull(onBoardResponseDTO);
        new Verifications() {{
            accountServiceHelper.saveOnBoardModel((UserOnBoardModel) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = UserIdNotFoundException.class)
    public void testInsertDetailsForOnBoardingIdNotFoundException() throws UserIdNotFoundException, InsertionFailedException {
        new Expectations() {{
            accountServiceHelper.saveOnBoardModel((UserOnBoardModel) any);
            result = new UserIdNotFoundException();
        }};
        OnBoardResponseDTO onBoardResponseDTO = accountService.insertDetailsForOnBoarding(makeOnBoardRequestDTO());
        Assert.assertNotNull(onBoardResponseDTO);
        new Verifications() {{
            accountServiceHelper.saveOnBoardModel((UserOnBoardModel) any);
            times = 1;
        }};
    }

    @Test
    public void testGenerateCardDetails() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            accountServiceHelper.saveCardModel((CardModel) any);
            result = cardModel;
        }};
        CardResponseDTO cardResponseDTO = accountService.generateCardDetails(makeCardRequestDTO());
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            accountServiceHelper.saveCardModel((CardModel) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = InsertionFailedException.class)
    public void testGenerateCardDetailsInsertionFailedException() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            accountServiceHelper.saveCardModel((CardModel) any);
            result = new InsertionFailedException();
        }};
        CardResponseDTO cardResponseDTO = accountService.generateCardDetails(makeCardRequestDTO());
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            accountServiceHelper.saveCardModel((CardModel) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = UserIdNotFoundException.class)
    public void testGenerateCardDetailsUserIdNotFoundException() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            accountServiceHelper.saveCardModel((CardModel) any);
            result = new UserIdNotFoundException();
        }};
        CardResponseDTO cardResponseDTO = accountService.generateCardDetails(makeCardRequestDTO());
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            accountServiceHelper.saveCardModel((CardModel) any);
            times = 1;
        }};
    }

    @Test
    public void setPin() throws CardNotFoundException {
        new Expectations() {{
            accountServiceHelper.setPin((SetPinRequestDTO) any);
            result = cardModel;
        }};
        SetPinResponseDTO setPinResponseDTO = accountService.setPin(makeSetPinRequestDTO());
        Assert.assertNotNull(setPinResponseDTO);
        new Verifications() {{
            accountServiceHelper.setPin((SetPinRequestDTO) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = CardNotFoundException.class)
    public void testSetPinCardNotFoundException() throws CardNotFoundException {
        new Expectations() {{
            accountServiceHelper.setPin((SetPinRequestDTO) any);
            result = new CardNotFoundException();
        }};
        SetPinResponseDTO setPinResponseDTO = accountService.setPin(makeSetPinRequestDTO());
        Assert.assertNotNull(setPinResponseDTO);
        new Verifications() {{
            accountServiceHelper.setPin((SetPinRequestDTO) any);
            times = 1;
        }};
    }

    @Test
    public void getCardDetails() throws UserNotFoundException, CardNotFoundException {
        new Expectations() {{
            accountServiceHelper.findCardDetails(userId);
            result = cardModel;
        }};
        CardResponseDTO cardResponseDTO = accountService.getCardDetails(userId);
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            accountServiceHelper.findCardDetails(userId);
            times = 1;
        }};
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void getCardDetailsUserNotFoundException() throws UserNotFoundException, CardNotFoundException {
        new Expectations() {{
            accountServiceHelper.findCardDetails(userId);
            result = new UserNotFoundException();
        }};
        CardResponseDTO cardResponseDTO = accountService.getCardDetails(userId);
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            accountServiceHelper.findCardDetails(userId);
            times = 1;
        }};
    }

    @Test(expectedExceptions = CardNotFoundException.class)
    public void getCardDetailsCardNotFoundException() throws UserNotFoundException, CardNotFoundException {
        new Expectations() {{
            accountServiceHelper.findCardDetails(userId);
            result = new CardNotFoundException();
        }};
        CardResponseDTO cardResponseDTO = accountService.getCardDetails(userId);
        Assert.assertNotNull(cardResponseDTO);
        new Verifications() {{
            accountServiceHelper.findCardDetails(userId);
            times = 1;
        }};
    }

    private SetPinRequestDTO makeSetPinRequestDTO() {
        SetPinRequestDTO setPinRequestDTO = new SetPinRequestDTO();
        setPinRequestDTO.setCardNumber(cardNumber);
        setPinRequestDTO.setPin(pin);
        return setPinRequestDTO;
    }

    private CardRequestDTO makeCardRequestDTO() {
        CardRequestDTO cardRequestDTO = new CardRequestDTO();
        cardRequestDTO.setUserId(userId);
        return cardRequestDTO;
    }

    private OnBoardRequestDTO makeOnBoardRequestDTO() {
        OnBoardRequestDTO onBoardRequestDTO = new OnBoardRequestDTO();
        onBoardRequestDTO.setAge(age);
        onBoardRequestDTO.setAadhaarNumber(aadhaarNumber);
        onBoardRequestDTO.setAddress(address);
        onBoardRequestDTO.setGender(Gender.MALE);
        onBoardRequestDTO.setAccountType(AccountType.CURRENT);
        onBoardRequestDTO.setUserId(userId);
        return onBoardRequestDTO;
    }

    private RegisterRequestDTO makeRegisterRequestDTO() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setEmail(email);
        registerRequestDTO.setMobileNumber(mobileNumber);
        registerRequestDTO.setName(name);
        registerRequestDTO.setPassword(password);
        registerRequestDTO.setUserName(userName);
        return registerRequestDTO;
    }

    private Optional getOptionalEntity() {
        Optional<UserOnBoardModel> opt = Optional.of(userOnBoardModel);
        return opt;
    }
}
