package com.bankingservice.banking.services;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.Gender;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

public class AccountServiceTest {
    @Tested
    private AccountService accountService;

    @Injectable
    private RegisterUserRepository registerUserRepository;

    @Injectable
    private UserOnBoardRepository userOnBoardRepository;

    private List<RegisterUserModel> registerEntities;
    private RegisterUserModel registerUserModel;
    private UserOnBoardModel userOnBoardModel;
    private static final int id = RandomUtils.nextInt();
    private static final int id1 = RandomUtils.nextInt();
    private static final String name = RandomStringUtils.randomAlphabetic(10);
    private static final String email = RandomStringUtils.randomAlphanumeric(10);
    private static final long mobileNumber = RandomUtils.nextLong();
    private static final String userName = RandomStringUtils.randomAlphanumeric(10);
    private static final String password = RandomStringUtils.randomAlphanumeric(10);
    private static final int age = RandomUtils.nextInt();
    private static final int registerUserId = RandomUtils.nextInt();
    private static final long aadhaarNumber = RandomUtils.nextLong();
    private static final String address = RandomStringUtils.randomAlphanumeric(10);


    @BeforeMethod
    public void setUp() {
        registerUserModel = new RegisterUserModel();
        registerUserModel.setId(id);
        registerUserModel.setName(name);
        registerUserModel.setEmail(email);
        registerUserModel.setMobileNumber(mobileNumber);
        registerUserModel.setUserName(userName);
        registerUserModel.setPassword(password);

        RegisterUserModel registerUserModel1 = new RegisterUserModel();
        registerUserModel1.setId(id1);
        registerUserModel.setName(name);
        registerUserModel.setEmail(email);
        registerUserModel.setMobileNumber(mobileNumber);
        registerUserModel.setUserName(userName);
        registerUserModel.setPassword(password);

        registerEntities = new ArrayList<>();
        registerEntities.add(registerUserModel);
        registerEntities.add(registerUserModel1);

        userOnBoardModel = new UserOnBoardModel();
        userOnBoardModel.setAge(age);
        userOnBoardModel.setAadhaarNumber(aadhaarNumber);
        userOnBoardModel.setAddress(address);
        userOnBoardModel.setGender(Gender.MALE);
        userOnBoardModel.setAccountType(AccountType.CURRENT);
        userOnBoardModel.setRegisterUserModel(registerUserModel);
        userOnBoardModel.setRegisterUserId(registerUserId);
    }

    @Test
    public void testInsertDetailsForRegistration() {
        new Expectations() {{
            registerUserRepository.save((RegisterUserModel) any);
            result = registerUserModel;
        }};
        RegisterUserResponseDTO registerUserResponseDTO = accountService.insertDetailsForRegistration(makeRegisterRequestDTO());
        Assert.assertNotNull(registerUserResponseDTO);
        new Verifications() {{
            registerUserRepository.save((RegisterUserModel) any);
            times = 1;
        }};
    }

    @Test
    void testInsertDetailsForOnBoarding() {
        new Expectations() {{
            userOnBoardRepository.save((UserOnBoardModel) any);
            result = userOnBoardModel;
        }};
        OnBoardResponseDTO onBoardResponseDTO = accountService.insertDetailsForOnBoarding(makeOnBoardRequestDTO());
        Assert.assertNotNull(onBoardResponseDTO);
        new Verifications() {{
            userOnBoardRepository.save((UserOnBoardModel) any);
            times = 1;
        }};
    }

    private OnBoardRequestDTO makeOnBoardRequestDTO() {
        OnBoardRequestDTO onBoardRequestDTO = new OnBoardRequestDTO();
        onBoardRequestDTO.setAge(age);
        onBoardRequestDTO.setAadhaarNumber(aadhaarNumber);
        onBoardRequestDTO.setAddress(address);
        onBoardRequestDTO.setGender(Gender.MALE);
        onBoardRequestDTO.setAccountType(AccountType.CURRENT);
        onBoardRequestDTO.setRegisterUserId(registerUserId);
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
