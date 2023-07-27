package com.epam.esm.springsecuritydemo.repository.impl;

import com.epam.esm.springsecuritydemo.entity.User;
import com.epam.esm.springsecuritydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ? ";
    private static final String FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ? ";
    private static final String SAVE = "INSERT INTO users(email, username, password) " +
            "VALUES (?, ?, ?) ";
    public static final String FIND_LAST_ADDED = "SELECT * FROM users " +
            "WHERE id = (SELECT max(id) FROM users) ";
    private static final String SAVE_USER_ROLE = "INSERT INTO users_roles VALUES (?, ?) ";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return findUserByParameter(FIND_BY_ID, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return findUserByParameter(FIND_BY_USERNAME, username);
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update(SAVE, ps -> {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
        });

    }

    @Override
    public Optional<User> findLastAdded() {
        try {
            User user = jdbcTemplate.queryForObject(FIND_LAST_ADDED, (rs, rowNum) -> User.builder()
                    .id(rs.getInt("id"))
                    .email(rs.getString("email"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .build());
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void saveUserRole(Integer userId, Integer roleId) {
        jdbcTemplate.update(SAVE_USER_ROLE, ps -> {
            ps.setInt(1, userId);
            ps.setInt(2, roleId);
        });
    }


    private <T> Optional<User> findUserByParameter(String query, T parameter) {
        try {
            User user = jdbcTemplate.queryForObject(query, (rs, rowNum) -> User.builder()
                    .id(rs.getInt("id"))
                    .email(rs.getString("email"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .build(), parameter);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
