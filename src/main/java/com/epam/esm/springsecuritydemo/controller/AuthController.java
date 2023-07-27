package com.epam.esm.springsecuritydemo.controller;

import com.epam.esm.springsecuritydemo.dtos.JwtRequest;
import com.epam.esm.springsecuritydemo.dtos.JwtResponse;
import com.epam.esm.springsecuritydemo.dtos.RegistrationUserDto;
import com.epam.esm.springsecuritydemo.dtos.ResponseUserDto;
import com.epam.esm.springsecuritydemo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {
        JwtResponse authToken = authService.createAuthToken(authRequest);
        return ResponseEntity.ok().body(authToken);
    }
    @PostMapping("/registration")
    public ResponseEntity<ResponseUserDto> createNewUser(@RequestBody RegistrationUserDto registrationUserDto){
        ResponseUserDto newUser = authService.createNewUser(registrationUserDto);
        return ResponseEntity.ok().body(newUser);
    }

}
