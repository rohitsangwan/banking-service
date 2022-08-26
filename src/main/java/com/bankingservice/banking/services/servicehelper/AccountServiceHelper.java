package com.bankingservice.banking.services.servicehelper;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.enums.ErrorCode;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import com.bankingservice.banking.utils.UniqueValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

import static com.bankingservice.banking.constants.Constants.REG;

@Component
public class AccountServiceHelper {
    @Autowired
    private RegisterUserRepository registerUserRepository;
    @Autowired
    private UserOnBoardRepository userOnBoardRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceHelper.class);

    /**
     * save the register user model
     *
     * @param user
     * @return registerUserModel
     */
    public RegisterUserModel saveRegisterModel(RegisterUserModel user) throws InsertionFailedException {
        try {
            logger.info("[saveRegisterModel] saving the registration details for user: {}", user);
            RegisterUserModel registerUserModel = registerUserRepository.save(user);
            logger.info("[saveRegisterModel] registration details saved for user: {}", user);
            return registerUserModel;
        } catch (DataIntegrityViolationException e) {
            logger.info("[saveRegisterModel] username/email already exists for user: {}", user);
            throw new InsertionFailedException(ErrorCode.USER_REGISTRATION_FAILED,
                    ErrorCode.USER_REGISTRATION_FAILED.getErrorMessage(),
                    ErrorCode.USER_REGISTRATION_FAILED.getDisplayMessage());
        }
    }

    /**
     * save the user on board model
     *
     * @param user
     * @return userOnBoardModel
     */
    public UserOnBoardModel saveOnBoardModel(UserOnBoardModel user) throws InsertionFailedException {
        try {
            logger.info("[saveOnBoardModel] saving the on boarding info for user: {}", user);
            UserOnBoardModel userOnBoardModel = userOnBoardRepository.save(user);
            logger.info("[saveOnBoardModel] on boarding info saved for user: {}", user);
            return userOnBoardModel;
        } catch (DataIntegrityViolationException e){
            logger.info("[saveRegisterModel] account exists for user: {}", user);
            throw new InsertionFailedException(ErrorCode.USER_ONBOARD_FAILED,
                    ErrorCode.USER_ONBOARD_FAILED.getErrorMessage(),
                    ErrorCode.USER_ONBOARD_FAILED.getDisplayMessage());
        }
    }

    /**
     * convert registerRequestDTO to registerUserModel
     *
     * @param registerRequestDTO
     * @return registerUserModel
     */
    public RegisterUserModel convertDtoToRegisterUserModel(RegisterRequestDTO registerRequestDTO) {
        logger.info("[convertDtoToRegisterUserModel] converting registerRequestDTO {} to registerUserModel", registerRequestDTO);
        RegisterUserModel registerUserModel = new RegisterUserModel();
        BeanUtils.copyProperties(registerRequestDTO, registerUserModel);
        ArrayList<String> uniqueKeyList = UniqueValueUtil.generateUniqueId(REG, 1);
        registerUserModel.setUserId(uniqueKeyList.get(0));
        return registerUserModel;
    }

    /**
     * convert registerUserModel to registerUserResponseDTO
     *
     * @param registerUserModel
     * @return registerUserResponseDTO
     */
    public RegisterUserResponseDTO convertModelToRegisterResponseDto(RegisterUserModel registerUserModel) {
        logger.info("[convertModelToRegisterResponseDto] converting registerUserModel {} to registerUserResponseDTO", registerUserModel);
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
    public UserOnBoardModel convertDtoToUserOnBoardModel(OnBoardRequestDTO onBoardRequestDTO) throws UserIdNotFoundException {
        logger.info("[convertDtoToUserOnBoardModel] converting onBoardRequestDTO {} to userOnBoardModel", onBoardRequestDTO);
        UserOnBoardModel userOnBoardModel = new UserOnBoardModel();
        BeanUtils.copyProperties(onBoardRequestDTO, userOnBoardModel);
        Optional<RegisterUserModel> registerUserModel = registerUserRepository.findByUserId(onBoardRequestDTO.getUserId());
        if (registerUserModel.isPresent()) {
            userOnBoardModel.setRegisterUserId(registerUserModel.get().getId());
            return userOnBoardModel;
        } else {
            logger.error("[convertDtoToUserOnBoardModel] user does not exist for userId: {}", onBoardRequestDTO.getUserId());
            throw new UserIdNotFoundException(ErrorCode.USER_ID_NOT_FOUND,
                    String.format(ErrorCode.USER_ID_NOT_FOUND.getErrorMessage(), onBoardRequestDTO.getUserId()),
                    ErrorCode.USER_ID_NOT_FOUND.getDisplayMessage());
        }
    }

    /**
     * converting userOnBoardModel to onBoardResponseDTO
     *
     * @param userOnBoardModel
     * @return onBoardResponseDTO
     */
    public OnBoardResponseDTO convertModelToOnBoardResponseDto(UserOnBoardModel userOnBoardModel) throws UserIdNotFoundException {
        logger.info("[convertModelToOnBoardResponseDto] converting userOnBoardModel {} to onBoardResponseDTO", userOnBoardModel);
        OnBoardResponseDTO onBoardResponseDTO = new OnBoardResponseDTO();
        BeanUtils.copyProperties(userOnBoardModel, onBoardResponseDTO);
        Optional<RegisterUserModel> registerUserModelOptional = registerUserRepository.findById(userOnBoardModel.getRegisterUserId());
        if (registerUserModelOptional.isPresent()) {
            RegisterUserModel registerUserModel = registerUserModelOptional.get();
            BeanUtils.copyProperties(registerUserModel, onBoardResponseDTO);
            return onBoardResponseDTO;
        } else {
            logger.error("[convertModelToOnBoardResponseDto] user does not exist for userId: {}", userOnBoardModel.getRegisterUserId());
            throw new UserIdNotFoundException(ErrorCode.USER_ID_NOT_FOUND,
                    String.format(ErrorCode.USER_ID_NOT_FOUND.getErrorMessage(), userOnBoardModel.getRegisterUserId()),
                    ErrorCode.USER_ID_NOT_FOUND.getDisplayMessage());
        }
    }
}
