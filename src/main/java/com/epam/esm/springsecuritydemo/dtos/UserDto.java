package com.epam.esm.springsecuritydemo.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String password;
}
