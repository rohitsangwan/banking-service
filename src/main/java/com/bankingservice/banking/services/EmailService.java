package com.bankingservice.banking.services;

import com.bankingservice.banking.constants.Constants;
import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.exception.InvalidOtpException;
import com.bankingservice.banking.utils.OtpUtil;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Properties;

@Service
public class EmailService {

    public Boolean sendEmail(String email, CardRequestDTO cardRequestDTO, HttpSession httpSession) {

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
            return false;
        }
        httpSession.setAttribute(Constants.MYOTP, generatedOTP);
        httpSession.setAttribute(Constants.MYEMAIL, email);
        httpSession.setMaxInactiveInterval(60);
        return true;
    }

    public Boolean validateOtp(String email, CardRequestDTO cardRequestDTO, HttpSession httpSession) throws InvalidOtpException {
        try {
            Long myOtp = (Long) httpSession.getAttribute(Constants.MYOTP);
            String myEmail = (String) httpSession.getAttribute(Constants.MYEMAIL);
            httpSession.removeAttribute(Constants.MYOTP);
            httpSession.removeAttribute(Constants.MYEMAIL);
            if (myOtp.equals(cardRequestDTO.getOtp()) && myEmail.equals(email)) {
                return true;
            } else {

                throw new InvalidOtpException(ErrorCode.OTP_VALIDATION_FAILED,
                        String.format(ErrorCode.OTP_VALIDATION_FAILED.getErrorMessage(), email),
                        ErrorCode.OTP_VALIDATION_FAILED.getDisplayMessage());
            }
        } catch (InvalidOtpException | NullPointerException e){
            throw new InvalidOtpException(ErrorCode.OTP_VALIDATION_FAILED,
                    String.format(ErrorCode.OTP_VALIDATION_FAILED.getErrorMessage(), email),
                    ErrorCode.OTP_VALIDATION_FAILED.getDisplayMessage());
        }
    }
}

