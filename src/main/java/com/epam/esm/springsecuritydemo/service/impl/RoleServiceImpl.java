package com.epam.esm.springsecuritydemo.service.impl;

import com.epam.esm.springsecuritydemo.entity.Role;
import com.epam.esm.springsecuritydemo.exceptions.NotFoundException;
import com.epam.esm.springsecuritydemo.repository.impl.RoleRepositoryImpl;
import com.epam.esm.springsecuritydemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepositoryImpl roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepositoryImpl roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findRolesByUsername(String username) {
        return roleRepository.findRolesByUsername(username);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Role with name=" + name + " not found"));
    }

}
