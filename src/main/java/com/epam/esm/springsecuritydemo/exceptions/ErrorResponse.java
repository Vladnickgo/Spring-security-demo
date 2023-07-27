package com.epam.esm.springsecuritydemo.exceptions;

public class ErrorResponse {
    private final String message;
    private final Integer errorCole;

    public ErrorResponse(String message, Integer errorCole) {
        this.message = message;
        this.errorCole = errorCole;
    }

    public String getMessage() {
        return message;
    }

    public Integer getErrorCole() {
        return errorCole;
    }
}
