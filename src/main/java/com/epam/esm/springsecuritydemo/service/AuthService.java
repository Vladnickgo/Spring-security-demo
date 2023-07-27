package com.epam.esm.springsecuritydemo.service;

import com.epam.esm.springsecuritydemo.dtos.JwtRequest;
import com.epam.esm.springsecuritydemo.dtos.JwtResponse;
import com.epam.esm.springsecuritydemo.dtos.RegistrationUserDto;
import com.epam.esm.springsecuritydemo.dtos.ResponseUserDto;

public interface AuthService {
    JwtResponse createAuthToken(JwtRequest authRequest);

    ResponseUserDto createNewUser(RegistrationUserDto registrationUserDto);

}
