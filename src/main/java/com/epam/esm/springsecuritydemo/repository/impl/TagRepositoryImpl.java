package com.epam.esm.springsecuritydemo.repository.impl;

import com.epam.esm.springsecuritydemo.entity.Tag;
import com.epam.esm.springsecuritydemo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String FIND_ALL_BY_BY_CERTIFICATE_ID = "SELECT * " +
            "FROM tags " +
            "         LEFT JOIN certificate_tag ct ON tags.id = ct.tag_id " +
            "WHERE certificate_id = ?;";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAllByCertificateId(Integer certificateId) {
        return jdbcTemplate.query(FIND_ALL_BY_BY_CERTIFICATE_ID, new Object[]{certificateId}, (rs, rowNum) -> Tag.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build());
    }
}
