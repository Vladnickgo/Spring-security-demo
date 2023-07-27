package com.epam.esm.springsecuritydemo.service.impl;

import com.epam.esm.springsecuritydemo.dtos.*;
import com.epam.esm.springsecuritydemo.exceptions.AuthorizedException;
import com.epam.esm.springsecuritydemo.exceptions.RegistrationException;
import com.epam.esm.springsecuritydemo.service.AuthService;
import com.epam.esm.springsecuritydemo.utils.JwtTokenUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(UserServiceImpl userService, JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public JwtResponse createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthorizedException("Incorrect username or password");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @Transactional
    @Override
    public ResponseUserDto createNewUser(RegistrationUserDto registrationUserDto) {
        String username = registrationUserDto.getUsername();
        if (userService.findByUsername(username).isPresent()) {
            throw new RegistrationException("User with name " + username + " already exists");
        }
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new RegistrationException("The password doesn't match the confirmation password");
        }
        UserDto userDto = UserDto.builder()
                .username(registrationUserDto.getUsername())
                .email(registrationUserDto.getEmail())
                .password(bCryptPasswordEncoder.encode(registrationUserDto.getPassword()))
                .build();
        UserDto savedUserDto = userService.save(userDto);
        return ResponseUserDto.builder()
                .id(savedUserDto.getId())
                .username(savedUserDto.getUsername())
                .email(savedUserDto.getEmail())
                .build();
    }
}
