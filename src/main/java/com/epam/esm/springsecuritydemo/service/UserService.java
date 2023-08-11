package com.epam.esm.springsecuritydemo.service;

import com.epam.esm.springsecuritydemo.dtos.ResponseUserDto;
import com.epam.esm.springsecuritydemo.dtos.UserDto;
import com.epam.esm.springsecuritydemo.entity.User;

import java.util.Optional;

public interface UserService {
    ResponseUserDto findById(Integer id);
    Optional<User> findByUsername(String username);

    ResponseUserDto save(UserDto userDto);

}
