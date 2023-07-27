package com.epam.esm.springsecuritydemo.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class ResponseUserDto extends RepresentationModel<ResponseUserDto> {
    private Integer id;
    private String username;
    private String email;
}
