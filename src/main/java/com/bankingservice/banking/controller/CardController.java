package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.dto.request.SetPinRequestDTO;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.exception.*;
import com.bankingservice.banking.services.AccountService;
import com.bankingservice.banking.services.CardService;
import com.bankingservice.banking.utils.CreateMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Controller for card apis
 *
 * @author Rohit Sangwan
 */
@RestController
@RequestMapping("/card/v1")
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private CardService cardService;

    /**
     * entry point controller to generate the card
     *
     * @param cardRequestDTO
     * @return ResponseEntity
     * @throws InsertionFailedException
     * @throws UserIdNotFoundException
     */
    @PostMapping("/new-card")
    public ResponseEntity<BaseResponseDTO> generateCard(@RequestBody CardRequestDTO cardRequestDTO)
            throws InsertionFailedException, UserIdNotFoundException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData(cardService.generateCardDetails(cardRequestDTO));
        baseResponseDTO.setMetaDTO(CreateMetaData.createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    /**
     * entry point controller to set the pin
     *
     * @param setPinRequestDTO
     * @return ResponseEntity
     * @throws UserIdNotFoundException
     */
    @PostMapping("/pin")
    public ResponseEntity<BaseResponseDTO> setPin(@RequestBody SetPinRequestDTO setPinRequestDTO)
            throws CardNotFoundException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData(cardService.setPin(setPinRequestDTO));
        baseResponseDTO.setMetaDTO(CreateMetaData.createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    /**
     * entry point controller to fetch card details
     *
     * @param cardRequestDTO
     * @return ResponseEntity
     */
    @GetMapping("/card-info")
    public ResponseEntity<BaseResponseDTO> getCardInfo(@RequestBody CardRequestDTO cardRequestDTO)
            throws UserNotFoundException, CardNotFoundException, InvalidOtpException, OtpExpiredException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO<>();
        baseResponseDTO.setData(cardService.getCardDetails(cardRequestDTO));
        baseResponseDTO.setMetaDTO(CreateMetaData.createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }
}
