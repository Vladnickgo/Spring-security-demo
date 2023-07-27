package com.epam.esm.springsecuritydemo.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.epam.esm.springsecuritydemo.exceptions.ErrorCode.*;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(Exception exception) {
        return new ErrorResponse(exception.getMessage(), NOT_FOUND_ERROR_CODE);
    }

    @ExceptionHandler(AuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleAuthorized(Exception exception) {
        return new ErrorResponse(exception.getMessage(), UNAUTHORIZED_ERROR_CODE);
    }
    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleRegistrationException(Exception exception){
        return new ErrorResponse(exception.getMessage(),REGISTRATION_ERROR_CODE);
    }
}
