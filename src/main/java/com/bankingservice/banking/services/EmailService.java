package com.bankingservice.banking.services;

import com.bankingservice.banking.constants.Constants;
import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.enums.OtpStatus;
import com.bankingservice.banking.exception.InvalidOtpException;
import com.bankingservice.banking.exception.OtpExpiredException;
import com.bankingservice.banking.models.mysql.OtpModel;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.repository.OtpRepository;
import com.bankingservice.banking.utils.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private OtpRepository otpRepository;

    public OtpModel sendEmail(String email) {

        Properties properties = System.getProperties();
        properties.put(Constants.SMTP_HOST, Constants.HOST);
        properties.put(Constants.SMTP_PORT, Constants.PORT);
        properties.put(Constants.SSL_ENABLE, Constants.TRUE);
        properties.put(Constants.SMTP_AUTH, Constants.TRUE);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constants.USERNAME, Constants.PASSWORD);
            }
        });
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        Long generatedOTP = OtpUtil.generateOTP();
        try {
            mimeMessage.setFrom(Constants.USERNAME);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(Constants.SUBJECT);
            mimeMessage.setText(Constants.BODY + generatedOTP);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
        OtpModel otpModel = new OtpModel();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        otpModel.setOtp(generatedOTP);
        otpModel.setTime(currentTime);
        return otpModel;
    }

    public Boolean validateOtp(RegisterUserModel registerUserModel, CardRequestDTO cardRequestDTO) throws InvalidOtpException, OtpExpiredException {
        try {
            Optional<OtpModel> otpModel = otpRepository.findByOtpId(registerUserModel.getId());
            if (otpModel.isPresent()) {
                Long myOtp = otpModel.get().getOtp();
                if (myOtp.equals(cardRequestDTO.getOtp())) {
                    if (System.currentTimeMillis() - otpModel.get().getTime().getTime() > 60000 || otpModel.get().getOtpStatus() == OtpStatus.EXPIRED) {
                        OtpModel myOtpModel = sendEmail(registerUserModel.getEmail());
                        otpModel.get().setOtp(myOtpModel.getOtp());
                        otpModel.get().setTime(myOtpModel.getTime());
                        otpModel.get().setOtpStatus(OtpStatus.ACTIVE);
                        otpRepository.save(otpModel.get());
                        throw new OtpExpiredException(ErrorCode.OTP_EXPIRED, ErrorCode.OTP_EXPIRED.getErrorMessage(),
                                ErrorCode.OTP_EXPIRED.getDisplayMessage());
                    } else {
                        otpModel.get().setOtpStatus(OtpStatus.EXPIRED);
                        otpRepository.save(otpModel.get());
                        return true;
                    }
                } else {
                    throw new InvalidOtpException(ErrorCode.OTP_VALIDATION_FAILED,
                            String.format(ErrorCode.OTP_VALIDATION_FAILED.getErrorMessage(), registerUserModel.getEmail()),
                            ErrorCode.OTP_VALIDATION_FAILED.getDisplayMessage());
                }
            } else
                throw new InvalidOtpException(ErrorCode.OTP_VALIDATION_FAILED,
                        String.format(ErrorCode.OTP_VALIDATION_FAILED.getErrorMessage(), registerUserModel.getEmail()),
                        ErrorCode.OTP_VALIDATION_FAILED.getDisplayMessage());

        } catch (InvalidOtpException | NullPointerException e) {
            throw new InvalidOtpException(ErrorCode.OTP_VALIDATION_FAILED,
                    String.format(ErrorCode.OTP_VALIDATION_FAILED.getErrorMessage(), registerUserModel.getEmail()),
                    ErrorCode.OTP_VALIDATION_FAILED.getDisplayMessage());
        } catch (OtpExpiredException e) {
            throw new OtpExpiredException(ErrorCode.OTP_EXPIRED, ErrorCode.OTP_EXPIRED.getErrorMessage(),
                    ErrorCode.OTP_EXPIRED.getDisplayMessage());
        }
    }
}

