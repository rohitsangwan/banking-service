package com.bankingservice.banking.client;

import com.bankingservice.banking.constants.ApiEndpoints;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.dto.response.transaction.ActivateAccountDTO;
import com.bankingservice.banking.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
@Slf4j
public class TransactionClient {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Value("${transac.api.base.url}")
    private String txnBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public void activateAccount(ActivateAccountDTO activateAccountDTO, String userId) {
        StringBuilder urlBuilder = new StringBuilder(txnBaseUrl);
        urlBuilder.append(ApiEndpoints.TRANSACTION_ACTIVATE_ACCOUNT_ENDPOINT);
        urlBuilder.append(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<ActivateAccountDTO> requestEntity = new HttpEntity(activateAccountDTO, headers);
        try {
            ResponseEntity<BaseResponseDTO> response = restTemplate.exchange(urlBuilder.toString(), HttpMethod.POST, requestEntity, BaseResponseDTO.class);
            logger.info("[activateAccount] response : {}", response);
            if (!response.getStatusCode().is2xxSuccessful()) {
                logger.error("[activateAccount] Exception occurred while activating the account: {}", userId);
            }
        } catch (Exception e) {
            logger.error("[activateAccount] API failed for userId: {}", userId);
        }
    }

}
