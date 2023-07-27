package com.epam.esm.springsecuritydemo.service.impl;

import com.epam.esm.springsecuritydemo.dtos.ResponseUserDto;
import com.epam.esm.springsecuritydemo.dtos.UserDto;
import com.epam.esm.springsecuritydemo.entity.Role;
import com.epam.esm.springsecuritydemo.entity.User;
import com.epam.esm.springsecuritydemo.exceptions.NotFoundException;
import com.epam.esm.springsecuritydemo.repository.impl.UserRepositoryImpl;
import com.epam.esm.springsecuritydemo.service.UserService;
import com.epam.esm.springsecuritydemo.service.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepositoryImpl userRepository;
    private final RoleServiceImpl roleService;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository, RoleServiceImpl roleService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseUserDto findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " not found"));
        return ResponseUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        List<Role> rolesList = roleService.findRolesByUsername(username);
        Set<Role> roleSet = new HashSet<>(rolesList);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(user -> User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleSet)
                .password(user.getPassword())
                .build());
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = userMapper.mapDtoToEntity(userDto);
        Set<Role> roles = Set.of(roleService.findByName("USER"));
        user.setRoles(roles);
        userRepository.save(user);
        User lastAdded = userRepository.findLastAdded().orElseThrow(() -> new NotFoundException("User not found"));
        lastAdded.setRoles(roles);
        roles.forEach(role -> {
            userRepository.saveUserRole(lastAdded.getId(), role.getId());
        });
        return userMapper.mapEntityToDto(lastAdded);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet())
        );
    }
}
