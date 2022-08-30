package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.response.BaseResponseDTO;
import mockit.Tested;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HealthCheckControllerTest {

    @Tested
    private HealthCheckController healthCheckController;

    @Test
    public void healthCheckTest() {
        BaseResponseDTO baseResponseDTO = healthCheckController.healthCheck().getBody();
        Assert.assertNotNull(baseResponseDTO);
        Assert.assertNotNull(baseResponseDTO.getData());
        Assert.assertNotNull(baseResponseDTO.getMetaDTO());
    }
}
