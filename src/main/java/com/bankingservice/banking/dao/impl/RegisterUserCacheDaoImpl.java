package com.bankingservice.banking.dao.impl;

import com.bankingservice.banking.constants.CacheConstants;
import com.bankingservice.banking.dao.RegisterUserCacheDao;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterUserCacheDaoImpl implements RegisterUserCacheDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private RedisTemplate<String, RegisterUserModel> redisTemplate;

    @Override
    public void saveUserRegistrationDetails(RegisterUserModel registerUserModel) {
        try {
            redisTemplate.opsForHash().put(CacheConstants.USER_REGISTRATION, registerUserModel.getUserId(), registerUserModel);
        } catch (Exception exception) {
            logger.error(CacheConstants.USER_REGISTRATION_FAILED, exception);
        }
    }

    @Override
    public RegisterUserModel getRegisterUserModelByUserId(String userId) {
        RegisterUserModel registerUserModel = null;
        try {
            registerUserModel = (RegisterUserModel) redisTemplate.opsForHash().get(CacheConstants.USER_REGISTRATION + userId, userId);
        } catch (Exception exception) {
            logger.error(CacheConstants.USER_REGISTRATION_DETAILS_NOT_FOUND, exception);
        }
        return registerUserModel;
    }
}
