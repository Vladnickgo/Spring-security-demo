package com.epam.esm.springsecuritydemo.repository;

import com.epam.esm.springsecuritydemo.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<Role> findRolesByUsername(String username);

    Optional<Role> findByName(String name);
}
