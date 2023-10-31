package com.epam.esm.springsecuritydemo.repository;

import com.epam.esm.springsecuritydemo.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository {
    List<Tag> findAllByCertificateId(Integer certificateId);
}
