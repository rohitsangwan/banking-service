package com.bankingservice.banking.config.cron;

import com.bankingservice.banking.enums.OtpStatus;
import com.bankingservice.banking.models.mysql.OtpModel;
import com.bankingservice.banking.repository.OtpRepository;
import com.bankingservice.banking.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class OtpStatusCron {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private OtpRepository otpRepository;

    @Scheduled(cron = "*/10 * * * * ?")
    public void updateStatusOfExpiredOtps() {
        List<OtpModel> otpModelList = otpRepository.findAll();
        List<OtpModel> newOtpModelList = otpModelList.stream().map(otpModel -> {
            if (otpModel.getOtpStatus() == OtpStatus.ACTIVE && System.currentTimeMillis() - otpModel.getTime().getTime() > 60000) {
                otpModel.setOtpStatus(OtpStatus.EXPIRED);
                logger.info("Updating the OTP status of {} to Expired", otpModel);
            }
            return otpModel;
        }).collect(Collectors.toList());
        otpModelList = newOtpModelList;
        otpRepository.saveAll(otpModelList);
    }
}
