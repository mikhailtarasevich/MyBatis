package com.mt.controller;

import com.mt.service.exception.DatabaseException;
import com.mt.service.exception.EntityNotFoundException;
import com.mt.service.exception.NotValidEntityException;
import com.mt.util.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void entityAlreadyExistException() {
        NotValidEntityException exception = new NotValidEntityException("Entity already exists");
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.entityAlreadyExistException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Entity already exists", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void entityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.entityNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Entity not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void notSuccessfulSaveException() {
        DatabaseException exception = new DatabaseException("Not successful save operation");
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.notSuccessfulSaveException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Not successful save operation", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void exception() {
        Exception exception = new Exception("Internal server error");
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.exception(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal server error", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

}
