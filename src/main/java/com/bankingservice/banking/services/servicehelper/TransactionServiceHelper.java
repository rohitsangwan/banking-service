package com.bankingservice.banking.services.servicehelper;

import com.bankingservice.banking.dto.response.transaction.TxnValidationDTO;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.exception.UserNotFoundException;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionServiceHelper {

    @Autowired
    private RegisterUserRepository registerUserRepository;

    @Autowired
    private UserOnBoardRepository userOnBoardRepository;


    public TxnValidationDTO validateAccount(String userId) throws UserNotFoundException {
        Optional<RegisterUserModel> registerUserModel = registerUserRepository.findByUserId(userId);
        if (registerUserModel.isPresent()) {
            Optional<UserOnBoardModel> userOnBoardModel = userOnBoardRepository.findByRegisterUserId(registerUserModel.get().getId());
            if (userOnBoardModel.isPresent()) {
                TxnValidationDTO txnValidationDTO = new TxnValidationDTO();
                txnValidationDTO.setAccountNumber(userOnBoardModel.get().getAccountNumber());
                txnValidationDTO.setAccountBalance(userOnBoardModel.get().getAccountBalance());
                return txnValidationDTO;
            } else {
                throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND,
                        String.format(ErrorCode.USER_NOT_FOUND.getErrorMessage(), userId),
                        ErrorCode.USER_NOT_FOUND.getDisplayMessage());
            }
        } else {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND,
                    String.format(ErrorCode.USER_NOT_FOUND.getErrorMessage(), userId),
                    ErrorCode.USER_NOT_FOUND.getDisplayMessage());
        }
    }
}
