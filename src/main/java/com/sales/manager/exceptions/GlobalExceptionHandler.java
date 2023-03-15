package com.sales.manager.exceptions;

import com.sales.manager.dto.ErrorMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DuplicateRecordException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();
        errorMessageDto.setMessage(ex.getMessage());
        return handleExceptionInternal(ex, errorMessageDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();
        errorMessageDto.setMessage(ex.getMessage());
        errorMessageDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorMessageDto.setCode("NOT_FOUND");
        return handleExceptionInternal(ex, errorMessageDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {BadRequestException.class, HttpClientErrorException.class})
    protected ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();
        errorMessageDto.setMessage(ex.getMessage());
        errorMessageDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorMessageDto.setCode("BAD_REQUEST");
        return handleExceptionInternal(ex, errorMessageDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }
}
