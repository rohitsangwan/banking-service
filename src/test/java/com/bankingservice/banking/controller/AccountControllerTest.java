package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.Gender;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.services.AccountService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccountControllerTest {

    @Tested
    private AccountController accountController;

    @Injectable
    private AccountService accountService;

    private static final int id = RandomUtils.nextInt();
    private static final String name = RandomStringUtils.randomAlphabetic(10);
    private static final String email = RandomStringUtils.randomAlphanumeric(10);
    private static final long mobileNumber = RandomUtils.nextLong();
    private static final String userName = RandomStringUtils.randomAlphanumeric(10);
    private static final String password = RandomStringUtils.randomAlphanumeric(10);
    private static final int age = RandomUtils.nextInt();
    private static final long aadhaarNumber = RandomUtils.nextLong();
    private static final String address = RandomStringUtils.randomAlphanumeric(10);
    private static final String userId = RandomStringUtils.randomAlphanumeric(10);

    RegisterUserResponseDTO registerUserResponseDTO;
    RegisterRequestDTO registerRequestDTO;
    OnBoardRequestDTO onBoardRequestDTO;
    OnBoardResponseDTO onBoardResponseDTO;

    @BeforeMethod
    public void setUp() {
        registerUserResponseDTO = new RegisterUserResponseDTO();
        registerUserResponseDTO.setName(name);
        registerUserResponseDTO.setEmail(email);
        registerUserResponseDTO.setMobileNumber(mobileNumber);
        registerUserResponseDTO.setUserName(userName);
        registerUserResponseDTO.setPassword(password);
        registerUserResponseDTO.setUserId(userId);

        registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setName(name);
        registerRequestDTO.setEmail(email);
        registerRequestDTO.setMobileNumber(mobileNumber);
        registerRequestDTO.setUserName(userName);
        registerRequestDTO.setPassword(password);

        RegisterUserModel registerUserModel = new RegisterUserModel();
        registerUserModel.setId(id);
        registerUserModel.setName(name);
        registerUserModel.setEmail(email);
        registerUserModel.setMobileNumber(mobileNumber);
        registerUserModel.setUserName(userName);
        registerUserModel.setPassword(password);

        onBoardRequestDTO = new OnBoardRequestDTO();
        onBoardRequestDTO.setAge(age);
        onBoardRequestDTO.setGender(Gender.MALE);
        onBoardRequestDTO.setAccountType(AccountType.CURRENT);
        onBoardRequestDTO.setAadhaarNumber(aadhaarNumber);
        onBoardRequestDTO.setAddress(address);
        onBoardRequestDTO.setUserId(userId);

        onBoardResponseDTO = new OnBoardResponseDTO();
        onBoardResponseDTO.setAge(age);
        onBoardResponseDTO.setGender(Gender.MALE);
        onBoardResponseDTO.setAccountType(AccountType.CURRENT);
        onBoardResponseDTO.setAadhaarNumber(aadhaarNumber);
        onBoardResponseDTO.setAddress(address);
        onBoardResponseDTO.setName(name);
        onBoardResponseDTO.setEmail(email);
        onBoardResponseDTO.setUserName(userName);
        onBoardResponseDTO.setPassword(password);
        onBoardResponseDTO.setUserId(userId);
    }

    @Test
    public void testRegisterUser() throws InsertionFailedException {
        new Expectations() {{
            accountService.insertDetailsForRegistration((RegisterRequestDTO) any);
            result = registerUserResponseDTO;
        }};
        BaseResponseDTO baseResponseDTO = accountController.registerUser(registerRequestDTO).getBody();
        Assert.assertNotNull(baseResponseDTO);
        Assert.assertNotNull(baseResponseDTO.getData());
        Assert.assertNotNull(baseResponseDTO.getMetaDTO());
        new Verifications() {{
            accountService.insertDetailsForRegistration((RegisterRequestDTO) any);
            times = 1;
        }};
    }

    @Test
    public void testOnBoardUser() throws InsertionFailedException, UserIdNotFoundException {
        new Expectations() {{
            accountService.insertDetailsForOnBoarding((OnBoardRequestDTO) any);
            result = onBoardResponseDTO;
        }};
        BaseResponseDTO baseResponseDTO = accountController.onBoardUser(onBoardRequestDTO).getBody();
        Assert.assertNotNull(baseResponseDTO);
        Assert.assertNotNull(baseResponseDTO.getData());
        Assert.assertNotNull(baseResponseDTO.getMetaDTO());
        new Verifications() {{
            accountService.insertDetailsForOnBoarding((OnBoardRequestDTO) any);
            times = 1;
        }};
    }

    @Test
    public void healthCheckTest() {
        BaseResponseDTO baseResponseDTO = accountController.healthCheck().getBody();
        Assert.assertNotNull(baseResponseDTO);
        Assert.assertNotNull(baseResponseDTO.getData());
        Assert.assertNotNull(baseResponseDTO.getMetaDTO());
    }
}
