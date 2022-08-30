package com.bankingservice.banking.services.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoggingServiceImpl implements LoggingService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        log.info("Request: {}", toJson(new RequestWrapper(httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
                buildHeadersMap(httpServletRequest), buildParametersMap(httpServletRequest), body)));
    }

    @Override
    public void logResponse(HttpServletRequest request, HttpServletResponse httpServletResponse, Object body) {
        log.info("Response {}", toJson(new ResponseWrapper(httpServletResponse.getStatus(), request.getMethod(),
                request.getRequestURI(), buildHeadersMap(httpServletResponse), body)));
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>(response.getHeaderNames().size());

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }
        return map;
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return String.valueOf(obj);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class ResponseWrapper {
        private int responseCode;
        private String method;
        private String path;
        private Map<String, String> headers;
        private Object body;

        /**
         * Instantiates a new Response wrapper.
         *
         * @param responseCode the response code
         * @param method       the method
         * @param path         the path
         * @param headers      the headers
         * @param body         the body
         */
        public ResponseWrapper(int responseCode, String method, String path, Map<String, String> headers, Object body) {
            this.responseCode = responseCode;
            this.method = method;
            this.path = path;
            this.headers = headers;
            this.body = body;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class RequestWrapper {
        private String method;
        private String path;
        private Map<String, String> headers;
        private Map<String, String> parameters;
        private Object body;

        /**
         * Instantiates a new Request wrapper.
         *
         * @param method     the method
         * @param path       the path
         * @param headers    the headers
         * @param parameters the parameters
         * @param body       the body
         */
        public RequestWrapper(String method, String path, Map<String, String> headers,
                              Map<String, String> parameters, Object body) {
            this.method = method;
            this.path = path;
            this.headers = headers;
            this.parameters = parameters;
            this.body = body;
        }
    }
}
