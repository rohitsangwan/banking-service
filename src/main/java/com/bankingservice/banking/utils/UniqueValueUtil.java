package com.bankingservice.banking.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Unique value util
 */
public class UniqueValueUtil {
    /**
     * Create a list of unique keys
     * It's a static method
     *
     * @param prefix
     * @param numberOfValues
     * @return uniqueKeyList
     */
    public static ArrayList<String> generateUniqueId(String prefix, Integer numberOfValues) {
        ArrayList<String> uniqueKeyList = new ArrayList<>();
        for (Integer uniqueKeyListIterator = 0; uniqueKeyListIterator < numberOfValues; uniqueKeyListIterator++) {
            UUID uuid = UUID.randomUUID();
            Long epochMilli = Instant.now().toEpochMilli();
            Integer hashCode = ("BS" + uuid + epochMilli).hashCode();
            uniqueKeyList.add(prefix + hashCode);
        }
        return uniqueKeyList;
    }
}
