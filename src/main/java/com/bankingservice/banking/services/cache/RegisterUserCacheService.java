package com.bankingservice.banking.services.cache;

import com.bankingservice.banking.dao.RegisterUserCacheDao;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.repository.RegisterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterUserCacheService {

    @Autowired
    private RegisterUserCacheDao registerUserCacheDao;

    @Autowired
    private RegisterUserRepository registerUserRepository;

    public void saveUserRegistrationDetails(RegisterUserModel registerUserModel){
        registerUserCacheDao.saveUserRegistrationDetails(registerUserModel);
    }

    public RegisterUserModel getRegisterUserModelByUserId(String userId){
        RegisterUserModel registerUserModel = registerUserCacheDao.getRegisterUserModelByUserId(userId);
        return registerUserModel;
    }
}
