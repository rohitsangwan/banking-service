package com.bankingservice.banking.utils;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class AccountNumberUtil {
    public static Long generateAccountNumber() {
        Random random = new Random();
        String firstDigit = String.valueOf(random.nextInt(9) + 1);
        UUID uuid = UUID.randomUUID();
        Integer hashCode = ("AN" + uuid).hashCode();
        Long epochMilli = Instant.now().toEpochMilli();
        String uniqueString = String.valueOf(hashCode + epochMilli);
        String requiredString = uniqueString.substring(0, 11);
        Long accountNumber = Long.parseLong(firstDigit + requiredString);
        return accountNumber;
    }
}
