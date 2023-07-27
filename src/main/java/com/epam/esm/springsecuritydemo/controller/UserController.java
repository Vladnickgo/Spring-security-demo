package com.epam.esm.springsecuritydemo.controller;

import com.epam.esm.springsecuritydemo.dtos.ResponseUserDto;
import com.epam.esm.springsecuritydemo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> findById(@PathVariable Integer id) {
        ResponseUserDto userDto = userService.findById(id);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        Link link = Link.of(uriString);
        userDto.add(link);
        return ResponseEntity.ok().body(userDto);
    }
}
