package com.bankingservice.banking.services.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingService {
    /**
     * Log request.
     *
     * @param httpServletRequest the http servlet request
     * @param body               the body
     */
    void logRequest(HttpServletRequest httpServletRequest, Object body);

    /**
     * Log response.
     *
     * @param httpServletRequest  the http servlet request
     * @param httpServletResponse the http servlet response
     * @param body                the body
     */
    void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);
}
