package com.bankingservice.banking.exception;

import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.dto.response.MetaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bankingservice.banking.constants.Constants.REQUEST_ID;
import static com.bankingservice.banking.constants.Constants.RESPONSE_ID;


@ControllerAdvice
public class ApplicationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    /**
     * Handles insertion failed exception
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(InsertionFailedException.class)
    public ResponseEntity<BaseResponseDTO> handleInsertionFailedException(InsertionFailedException e){
        logger.error("InsertionFailedException: " + e.getStackTrace());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setMetaDTO(new MetaDTO(e.getErrorCode().getCode(), e.getErrorCode().getErrorMessage(), MDC.get(REQUEST_ID), MDC.get(RESPONSE_ID)));
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    /**
     * Handles id not found exception
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> handleUserIdNotFoundException(UserIdNotFoundException e){
        logger.error("UserIdNotFoundException: " +  e.getStackTrace());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setMetaDTO(new MetaDTO(e.getErrorCode().getCode(), e.getErrorCode().getErrorMessage(), MDC.get(REQUEST_ID), MDC.get(RESPONSE_ID)));
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles card not found exception
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> handleCardNotFoundException(CardNotFoundException e){
        logger.error("CardNotFoundException: " +  e.getStackTrace());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setMetaDTO(new MetaDTO(e.getErrorCode().getCode(), e.getErrorCode().getErrorMessage(), MDC.get(REQUEST_ID), MDC.get(RESPONSE_ID)));
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles user not found exception
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> handleUserNotFoundException(UserNotFoundException e){
        logger.error("UserNotFoundException: " +  e.getStackTrace());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setMetaDTO(new MetaDTO(e.getErrorCode().getCode(), e.getErrorCode().getErrorMessage(), MDC.get(REQUEST_ID), MDC.get(RESPONSE_ID)));
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles invalid otp exception
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<BaseResponseDTO> handleInvalidOtpException(InvalidOtpException e){
        logger.error("InvalidOtpException: " +  e.getStackTrace());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setMetaDTO(new MetaDTO(e.getErrorCode().getCode(), e.getErrorCode().getErrorMessage(), MDC.get(REQUEST_ID), MDC.get(RESPONSE_ID)));
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles expired otp exception
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<BaseResponseDTO> handleOtpExpiredException(OtpExpiredException e){
        logger.error("OtpExpiredException: " +  e.getStackTrace());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setMetaDTO(new MetaDTO(e.getErrorCode().getCode(), e.getErrorCode().getErrorMessage(), MDC.get(REQUEST_ID), MDC.get(RESPONSE_ID)));
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles service call exception
     *
     * @param e
     * @return response entity
     */
    @ExceptionHandler(ServiceCallException.class)
    public ResponseEntity<BaseResponseDTO> handleServiceCallException(ServiceCallException e){
        logger.error("ServiceCallException: " +  e.getStackTrace());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setMetaDTO(new MetaDTO(e.getErrorCode().getCode(), e.getErrorCode().getErrorMessage(), MDC.get(REQUEST_ID), MDC.get(RESPONSE_ID)));
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.BAD_REQUEST);
    }

}
