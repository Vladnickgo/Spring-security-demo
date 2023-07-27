package com.epam.esm.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/authUser")
    public String getAuthUser() {
        return "Hello AuthUser!";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Hello Admin!";
    }

    @GetMapping("/free")
    public String getFree() {
        return "Hello World!";
    }
}
