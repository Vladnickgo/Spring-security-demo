package com.epam.esm.springsecuritydemo.exceptions;

public class AuthorizedException extends RuntimeException {
    public AuthorizedException(String message) {
        super(message);
    }
}
