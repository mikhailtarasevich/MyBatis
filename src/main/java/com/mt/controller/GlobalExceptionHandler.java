package com.mt.controller;

import com.mt.service.exception.DatabaseException;
import com.mt.service.exception.NotValidEntityException;
import com.mt.service.exception.EntityNotFoundException;
import com.mt.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> entityAlreadyExistException(NotValidEntityException e) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .withMessage(e.getMessage())
                .withTimestamp(LocalDateTime.now())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException e) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .withMessage(e.getMessage())
                .withTimestamp(LocalDateTime.now())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notSuccessfulSaveException(DatabaseException e) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .withMessage(e.getMessage())
                .withTimestamp(LocalDateTime.now())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exception(Exception e) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .withMessage(e.getMessage())
                .withTimestamp(LocalDateTime.now())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
