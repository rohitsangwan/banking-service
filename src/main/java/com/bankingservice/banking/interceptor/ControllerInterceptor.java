package com.bankingservice.banking.interceptor;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.bankingservice.banking.constants.Constants.REQUEST_ID;
import static com.bankingservice.banking.constants.Constants.RESPONSE_ID;

@Component
public class ControllerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (MDC.get(REQUEST_ID) == null) {
            MDC.put(REQUEST_ID, String.valueOf(UUID.randomUUID()));
        }
        if (MDC.get(RESPONSE_ID) == null) {
            MDC.put(RESPONSE_ID, String.valueOf(UUID.randomUUID()));
        }
        return true;
    }

}
