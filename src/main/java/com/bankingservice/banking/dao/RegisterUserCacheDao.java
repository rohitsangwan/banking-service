package com.bankingservice.banking.dao;

import com.bankingservice.banking.models.mysql.RegisterUserModel;

import java.util.Optional;


public interface RegisterUserCacheDao {
    void saveUserRegistrationDetails(RegisterUserModel registerUserModel);

    RegisterUserModel getRegisterUserModelByUserId(String userId);
}
