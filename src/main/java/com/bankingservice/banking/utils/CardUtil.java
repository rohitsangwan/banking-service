package com.bankingservice.banking.utils;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class CardUtil {
    public static Long generateCardNumber() {
        Random random = new Random();
        String firstDigit = String.valueOf(random.nextInt(9) + 1);
        UUID uuid = UUID.randomUUID();
        Integer hashCode = ("CN" + uuid).hashCode();
        Long epochMilli = Instant.now().toEpochMilli();
        String uniqueString = String.valueOf(hashCode + epochMilli);
        String requiredString = uniqueString.substring(0, 11);
        Long cardNumber = Long.parseLong(firstDigit + requiredString);
        return cardNumber;
    }

    public static Integer generateCvv(){
        Random random = new Random();
        UUID uuid = UUID.randomUUID();
        Long epochMilli = Instant.now().toEpochMilli();
        String hashCode = String.valueOf(("CVV" + uuid + epochMilli).hashCode());
        String refactoredString = hashCode.replace("-", String.valueOf(random.nextInt(10)));
        Integer cvv = Integer.parseInt(refactoredString.substring(0,3));
        return cvv;
    }
}
