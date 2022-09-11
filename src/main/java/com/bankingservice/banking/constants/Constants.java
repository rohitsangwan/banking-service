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
     * The constant SUCCESS for success
     */
    public static final String SUCCESS = "Success";
    /**
     * The constant FAILED for failed
     */
    public static final String FAILED = "Failed";
    /**
     * The constant MYOTP for otp
     */
    public static final String MYOTP = "myOtp";
    /**
     * The constant MYEMAIL for myEmail
     */
    public static final String MYEMAIL = "myEmail";
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
     * The constant true
     */
    public static final String TRUE = "true";
    /**
     * The constant HOST used as host
     */
    public static final String HOST = "smtp.gmail.com";
    /**
     * The constant PORT
     */
    public static final String PORT = "465";
    /**
     * The constant SMTP_HOST
     */
    public static final String SMTP_HOST = "mail.smtp.host";
    /**
     * The constant SMTP_PORT
     */
    public static final String SMTP_PORT = "mail.smtp.port";
    /**
     * The constant SSL_ENABLE to enable ssl
     */
    public static final String SSL_ENABLE = "mail.smtp.ssl.enable";
    /**
     * The constant SMTP_AUTH for authentication
     */
    public static final String SMTP_AUTH = "mail.smtp.auth";
    /**
     * The constant USERNAME which is the username of email
     */
    public static final String USERNAME = "ro.banking.service@gmail.com";
    /**
     * The constant PASSWORD which is the password of email
     */
    public static final String PASSWORD = "ldrzrhulejzacmdj";
    /**
     * The constant SUBJECT which is the subject of the email containing OTP
     */
    public static final String SUBJECT = "OTP from Ro Banking Service";
    /**
     * The contant OTP
     */
    public static final String OTP = "OTP";
    /**
     * The constant BODY which is the body of the email containing OTP
     */
    public static final String BODY = "Your OTP is ";
    /**
     * The constant URI_TO_IGNORE.
     */
    public static final Set<String> URI_TO_IGNORE = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList("/account/v1/health")));
}
