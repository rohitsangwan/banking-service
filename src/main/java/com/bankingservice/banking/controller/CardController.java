package com.bankingservice.banking.controller;

import com.bankingservice.banking.dto.request.CardRequestDTO;
import com.bankingservice.banking.dto.request.SetPinRequestDTO;
import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.exception.CardNotFoundException;
import com.bankingservice.banking.exception.InsertionFailedException;
import com.bankingservice.banking.exception.UserIdNotFoundException;
import com.bankingservice.banking.exception.UserNotFoundException;
import com.bankingservice.banking.services.AccountService;
import com.bankingservice.banking.services.CardService;
import com.bankingservice.banking.utils.CreateMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BaseResponseDTO> generateCard(@RequestBody CardRequestDTO cardRequestDTO) throws InsertionFailedException, UserIdNotFoundException {
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
    public ResponseEntity<BaseResponseDTO> setPin(@RequestBody SetPinRequestDTO setPinRequestDTO) throws CardNotFoundException {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setData(cardService.setPin(setPinRequestDTO));
        baseResponseDTO.setMetaDTO(CreateMetaData.createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    /**
     * entry point controller to fetch card details
     *
     * @param userId
     * @return ResponseEntity
     */
    @GetMapping("/card-info/{userId}")
    public ResponseEntity<BaseResponseDTO> getCardInfo(@PathVariable("userId") String userId) throws UserNotFoundException, CardNotFoundException {
        logger.info("Controller UserID: {}", userId);
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO<>();
        baseResponseDTO.setData(cardService.getCardDetails(userId));
        baseResponseDTO.setMetaDTO(CreateMetaData.createSuccessMetaData());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }
}
