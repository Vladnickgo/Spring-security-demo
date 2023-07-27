package com.epam.esm.springsecuritydemo.repository;

import com.epam.esm.springsecuritydemo.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Integer id);

    Optional<User> findByUsername(String username);

    void save(User user);

    Optional<User> findLastAdded();

    void saveUserRole(Integer userId,Integer roleId);

}
