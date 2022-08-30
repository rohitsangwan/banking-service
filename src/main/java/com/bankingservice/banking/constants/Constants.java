package com.bankingservice.banking.constants;

import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * constants for general purpose
 */

public final class Constants {

    private Constants() {
    }

    /**
     * The constant CVV for generating the cvv for card
     */
    public static final String CVV = "CVV";

    /**
     * The constant OK for health check
     */
    public static final String OK = "ok";

    /**
     * The constant REG used as a prefix
     */
    public static final String REG = "REG";

    /**
     * The constant REQUEST_ID
     */
    public static final String REQUEST_ID = "request-id";
    /**
     * The constant RESPONSE_ID
     */
    public static final String RESPONSE_ID = "response-id";
    /**
     * The constant URI_TO_IGNORE.
     */
    public static final Set<String> URI_TO_IGNORE = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList("/account/v1/health")));
}
