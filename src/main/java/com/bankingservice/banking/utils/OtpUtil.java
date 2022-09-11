package com.bankingservice.banking.utils;

import com.bankingservice.banking.constants.Constants;

import java.time.Instant;
import java.util.UUID;

public class OtpUtil {
    public static Long generateOTP() {
        UUID uuid = UUID.randomUUID();
        Long epochMilli = Instant.now().toEpochMilli();
        Integer hashCode = Math.abs((Constants.OTP + uuid + epochMilli).hashCode());
        String otp = String.valueOf(hashCode).substring(0,6);
        return Long.parseLong(otp);
    }
}
