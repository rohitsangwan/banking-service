package com.bankingservice.banking.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class UniqueValueUtil {
    public static ArrayList<String> generateUniqueId(String prefix, int numberOfValues) {
        ArrayList<String> uniqueKeyList = new ArrayList<>();
        for (int uniqueKeyListIterator = 0; uniqueKeyListIterator < numberOfValues; uniqueKeyListIterator++) {
            UUID uuid = UUID.randomUUID();
            long epochMilli = Instant.now().toEpochMilli();
            int hashCode = ("BS" + uuid + epochMilli).hashCode();
            uniqueKeyList.add(prefix + hashCode);
        }
        return uniqueKeyList;
    }
}
