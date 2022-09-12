package com.bankingservice.banking.services.servicehelper;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.CardRepository;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.springframework.dao.DataIntegrityViolationException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.sql.Ref;

public class AccountServiceHelperTest {
    private static final RegisterUserModel registerUserModel = new RegisterUserModel();
    private static final RegisterUserModel registerUserModel1 = new RegisterUserModel();
    private static final UserOnBoardModel userOnBoardModel = new UserOnBoardModel();
    private static final String exception = "exception";
    private static final String name = "tom";
    private static final RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
    private static final OnBoardRequestDTO onBoardRequestDTO = new OnBoardRequestDTO();

    @Injectable
    private RegisterUserRepository registerUserRepository;

    @Injectable
    private UserOnBoardRepository userOnBoardRepository;

    @Injectable
    private CardRepository cardRepository;

    @Tested
    private AccountServiceHelper accountServiceHelper;

    @BeforeTest
    public void setUp() {
        registerRequestDTO.setName(name);
        registerUserModel.setUserName(name);
    }

    @Test
    public void testSaveRegisterModel() throws InsertionFailedException {
        new Expectations() {{
            registerUserRepository.save((RegisterUserModel) any);
        }};
        accountServiceHelper.saveRegisterModel(registerUserModel);
        new Verifications() {{
            registerUserRepository.save((RegisterUserModel) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = InsertionFailedException.class)
    public void testSaveRegisterModelForException() throws InsertionFailedException {
        new Expectations() {{
            registerUserRepository.save((RegisterUserModel) any);
            result = new DataIntegrityViolationException(exception);
        }};
        accountServiceHelper.saveRegisterModel(registerUserModel);
        new Verifications() {{
            registerUserRepository.save((RegisterUserModel) any);
            times = 1;
        }};
    }

    @Test
    public void testSaveOnBoardModel() throws InsertionFailedException {
        new Expectations() {{
            userOnBoardRepository.save((UserOnBoardModel) any);
        }};
        accountServiceHelper.saveOnBoardModel(userOnBoardModel);
        new Verifications() {{
            userOnBoardRepository.save((UserOnBoardModel) any);
            times = 1;
        }};
    }

    @Test(expectedExceptions = InsertionFailedException.class)
    public void testSaveOnBoardModelForException() throws InsertionFailedException {
        new Expectations() {{
            userOnBoardRepository.save((UserOnBoardModel) any);
            result = new DataIntegrityViolationException(exception);
        }};
        accountServiceHelper.saveOnBoardModel(userOnBoardModel);
        new Verifications() {{
            userOnBoardRepository.save((UserOnBoardModel) any);
            times = 1;
        }};
    }

    @Test
    public void testConvertDtoToRegisterUserModel() {
        RegisterUserModel registerUser = accountServiceHelper.convertDtoToRegisterUserModel(registerRequestDTO);
        Assert.assertEquals(registerUser.getName(), name);
    }

    @Test
    public void testConvertModelToRegisterResponseDto() {
        RegisterUserResponseDTO registerUserResponseDTO = accountServiceHelper.convertModelToRegisterResponseDto(registerUserModel);
        Assert.assertEquals(registerUserResponseDTO.getUserName(), name);
    }

    @Test(enabled = false)
    public void testConvertDtoToUserOnBoardModel() throws UserIdNotFoundException {
        new Expectations() {{
            registerUserRepository.findByUserId(anyString);
            result = registerUserModel;
        }};
        accountServiceHelper.convertDtoToUserOnBoardModel(onBoardRequestDTO);
        new Verifications() {{
            registerUserRepository.findByUserId(anyString);
            times = 1;
        }};
    }


}
