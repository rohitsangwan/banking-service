package com.bankingservice.banking.services;

import com.bankingservice.banking.dto.request.UpdateBalanceRequestDTO;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.exception.UserNotFoundException;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private UserOnBoardRepository userOnBoardRepository;

    public void updateBalance(UpdateBalanceRequestDTO updateBalanceRequestDTO) throws UserNotFoundException {
        try {
            UserOnBoardModel userOnBoardModel = userOnBoardRepository.findByAccountNumber(updateBalanceRequestDTO.getAccountNumber());
            if (userOnBoardModel != null) {
                userOnBoardModel.setAccountBalance(updateBalanceRequestDTO.getAccountBalance());
                userOnBoardRepository.save(userOnBoardModel);
            } else {
                logger.error("[updateBalance] user does not exist for account number : {}", updateBalanceRequestDTO.getAccountBalance());
                throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getErrorMessage(),
                        ErrorCode.USER_NOT_FOUND.getDisplayMessage());
            }

        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getErrorMessage(),
                    ErrorCode.USER_NOT_FOUND.getDisplayMessage());
        }
    }
}
