package com.bankingservice.banking.exception;

import com.bankingservice.banking.dto.response.BaseResponseDTO;
import com.bankingservice.banking.dto.response.MetaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
        logger.error("InsertionFailedException has occurred!");
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setMetaDTO(new MetaDTO(e.getErrorCode().getCode(), e.getErrorCode().getErrorMessage(), "responseId", "requestId", e.getErrorCode().getDisplayMessage()));
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
        logger.error("UserIdNotFoundException has occurred!");
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setMetaDTO(new MetaDTO(e.getErrorCode().getCode(), e.getErrorCode().getErrorMessage(), "responseId", "requestId", e.getErrorCode().getDisplayMessage()));
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.NOT_FOUND);
    }
}
