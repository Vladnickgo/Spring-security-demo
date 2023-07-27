package com.epam.esm.springsecuritydemo.service;

import com.epam.esm.springsecuritydemo.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findRolesByUsername(String username);

    Role findByName(String name);
}
