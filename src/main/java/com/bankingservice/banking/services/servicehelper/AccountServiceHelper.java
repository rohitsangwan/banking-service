package com.bankingservice.banking.services.servicehelper;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.exception.ErrorCode;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.utils.UniqueValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class AccountServiceHelper {
    @Autowired
    private RegisterUserRepository registerUserRepository;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceHelper.class);

    /**
     * convert registerRequestDTO to registerUserModel
     *
     * @param registerRequestDTO
     * @return registerUserModel
     */
    public RegisterUserModel makeRegisterEntity(RegisterRequestDTO registerRequestDTO) {
        logger.info("[makeRegisterEntity] converting registerRequestDTO {} to registerUserModel", registerRequestDTO);
        RegisterUserModel registerUserModel = new RegisterUserModel();
        BeanUtils.copyProperties(registerRequestDTO, registerUserModel);
        ArrayList<String> uniqueKeyList = UniqueValueUtil.generateUniqueId("REG", 1);
        registerUserModel.setUserId(uniqueKeyList.get(0));
        return registerUserModel;
    }

    /**
     * convert registerUserModel to registerUserResponseDTO
     *
     * @param registerUserModel
     * @return registerUserResponseDTO
     */
    public RegisterUserResponseDTO makeRegisterUserResponseDTO(RegisterUserModel registerUserModel) {
        logger.info("[makeRegisterUserResponseDTO] converting registerUserModel {} to registerUserResponseDTO", registerUserModel);
        RegisterUserResponseDTO registerUserResponseDTO = new RegisterUserResponseDTO();
        BeanUtils.copyProperties(registerUserModel, registerUserResponseDTO);
        return registerUserResponseDTO;
    }

    /**
     * convert onBoardRequestDTO to userOnBoardModel
     *
     * @param onBoardRequestDTO
     * @return userOnBoardModel
     */
    public UserOnBoardModel makeOnBoardingEntity(OnBoardRequestDTO onBoardRequestDTO) throws UserIdNotFoundException {
        logger.info("[makeOnBoardingEntity] converting onBoardRequestDTO {} to userOnBoardModel", onBoardRequestDTO);
        UserOnBoardModel userOnBoardModel = new UserOnBoardModel();
        BeanUtils.copyProperties(onBoardRequestDTO, userOnBoardModel);
        Optional<RegisterUserModel> registerUserModel = registerUserRepository.findByUserId(onBoardRequestDTO.getUserId());
        if (registerUserModel.isPresent()) {
            userOnBoardModel.setRegisterUserId(registerUserModel.get().getId());
            return userOnBoardModel;
        } else {
            logger.debug("[makeOnBoardingEntity] user does not exist for userId: {}", onBoardRequestDTO.getUserId());
            throw new UserIdNotFoundException(ErrorCode.USER_ID_NOT_FOUND, ErrorCode.USER_ID_NOT_FOUND.getErrorMessage(), ErrorCode.USER_ID_NOT_FOUND.getDisplayMessage());
        }
    }

    /**
     * converting userOnBoardModel to onBoardResponseDTO
     *
     * @param userOnBoardModel
     * @return onBoardResponseDTO
     */
    public OnBoardResponseDTO makeOnBoardingResponseDTO(UserOnBoardModel userOnBoardModel) throws UserIdNotFoundException {
        logger.info("[makeOnBoardingResponseDTO] converting userOnBoardModel {} to onBoardResponseDTO", userOnBoardModel);
        OnBoardResponseDTO onBoardResponseDTO = new OnBoardResponseDTO();
        BeanUtils.copyProperties(userOnBoardModel, onBoardResponseDTO);
        Optional<RegisterUserModel> registerUserModelOptional = registerUserRepository.findById(userOnBoardModel.getRegisterUserId());
        if (registerUserModelOptional.isPresent()) {
            RegisterUserModel registerUserModel = registerUserModelOptional.get();
            BeanUtils.copyProperties(registerUserModel, onBoardResponseDTO);
            return onBoardResponseDTO;
        } else {
            logger.debug("[makeOnBoardingEntity] user does not exist for userId: {}", userOnBoardModel.getRegisterUserId());
            throw new UserIdNotFoundException(ErrorCode.USER_ID_NOT_FOUND, ErrorCode.USER_ID_NOT_FOUND.getErrorMessage(), ErrorCode.USER_ID_NOT_FOUND.getDisplayMessage());
        }
    }
}
