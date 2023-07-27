package com.epam.esm.springsecuritydemo.service.mapper;

import com.epam.esm.springsecuritydemo.dtos.UserDto;
import com.epam.esm.springsecuritydemo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface UserMapper {
    User mapDtoToEntity(UserDto userDto);
    UserDto mapEntityToDto(User user);

}
