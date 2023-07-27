package com.epam.esm.springsecuritydemo.repository.impl;

import com.epam.esm.springsecuritydemo.entity.Role;
import com.epam.esm.springsecuritydemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private static final String FIND_ROLE_BY_USERNAME = "SELECT role_id,name " +
            "FROM users " +
            "         LEFT JOIN users_roles ur on users.id = ur.user_id " +
            "         LEFT JOIN roles r on r.id = ur.role_id " +
            "WHERE username = ? ";
    private static final String FIND_BY_NAME = "SELECT * FROM roles WHERE name = ?; ";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Role> findRolesByUsername(String username) {
        return jdbcTemplate.query(FIND_ROLE_BY_USERNAME,
                ps -> ps.setString(1, username),
                (rs, rowNum) -> {
                    Role role = new Role();
                    role.setId(rs.getInt("role_id"));
                    role.setName(rs.getString("name"));
                    return role;
                });
    }

    @Override
    public Optional<Role> findByName(String name) {
        try {
            Role role = jdbcTemplate.queryForObject(FIND_BY_NAME, (rs, rowNum) -> {
                Role roleMap = new Role();
                roleMap.setId(rs.getInt("id"));
                roleMap.setName(rs.getString("name"));
                return roleMap;
            }, name);
            return Optional.ofNullable(role);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
