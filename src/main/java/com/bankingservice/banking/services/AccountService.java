package com.bankingservice.banking.services;

import com.bankingservice.banking.dto.request.OnBoardRequestDTO;
import com.bankingservice.banking.dto.request.RegisterRequestDTO;
import com.bankingservice.banking.dto.response.OnBoardResponseDTO;
import com.bankingservice.banking.dto.response.RegisterUserResponseDTO;
import com.bankingservice.banking.models.mysql.RegisterUserModel;
import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import com.bankingservice.banking.repository.RegisterUserRepository;
import com.bankingservice.banking.repository.UserOnBoardRepository;
import com.bankingservice.banking.utils.UniqueValueUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Account Service
 */
@Service
public class AccountService {
    @Autowired
    private RegisterUserRepository registerUserRepository;

    @Autowired
    private UserOnBoardRepository userOnBoardRepository;

    /**
     * insert details for user registration
     *
     * @param registerRequestDTO
     * @return registerUserResponseDTO
     */

    public RegisterUserResponseDTO insertDetailsForRegistration(RegisterRequestDTO registerRequestDTO) {
        RegisterUserModel entity = registerUserRepository.save(makeRegisterEntity(registerRequestDTO));
        RegisterUserResponseDTO registerUserResponseDTO = makeRegisterUserResponseDTO(entity);
        return registerUserResponseDTO;
    }

    /**
     * insert details for user on boarding
     *
     * @param onBoardRequestDTO
     * @return onBoardResponseDTO
     */

    public OnBoardResponseDTO insertDetailsForOnBoarding(OnBoardRequestDTO onBoardRequestDTO) {
        UserOnBoardModel user = userOnBoardRepository.save(makeOnBoardingEntity(onBoardRequestDTO));
        OnBoardResponseDTO onBoardResponseDTO = makeOnBoardingResponseDTO(user);
        return onBoardResponseDTO;
    }

    private RegisterUserModel makeRegisterEntity(RegisterRequestDTO registerRequestDTO) {
        RegisterUserModel registerUserModel = new RegisterUserModel();
        BeanUtils.copyProperties(registerRequestDTO, registerUserModel);
        ArrayList<String> uniqueKeyList = UniqueValueUtil.generateUniqueId("REG", 1);
        registerUserModel.setUserId(uniqueKeyList.get(0));
        return registerUserModel;
    }

    private RegisterUserResponseDTO makeRegisterUserResponseDTO(RegisterUserModel entity) {
        RegisterUserResponseDTO registerUserResponseDTO = new RegisterUserResponseDTO();
        BeanUtils.copyProperties(entity, registerUserResponseDTO);
        registerUserResponseDTO.setRegisterUserId(entity.getId());
        return registerUserResponseDTO;
    }

    private OnBoardResponseDTO makeOnBoardingResponseDTO(UserOnBoardModel user) {
            OnBoardResponseDTO onBoardResponseDTO = new OnBoardResponseDTO();
            BeanUtils.copyProperties(user, onBoardResponseDTO);
            Optional<RegisterUserModel> registerUser = registerUserRepository.findById(user.getRegisterUserId());
            if(registerUser.isPresent()){
                onBoardResponseDTO.setRegisterUserModel(registerUser.get());
            }
            return onBoardResponseDTO;
    }

    private UserOnBoardModel makeOnBoardingEntity(OnBoardRequestDTO onBoardRequestDTO) {
        UserOnBoardModel userOnBoardModel = new UserOnBoardModel();
        BeanUtils.copyProperties(onBoardRequestDTO, userOnBoardModel);
        return userOnBoardModel;
    }
}
