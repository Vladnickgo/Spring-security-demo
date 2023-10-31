package com.epam.esm.springsecuritydemo.repository;

import com.epam.esm.springsecuritydemo.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface GiftCertificateRepository extends CrudDao<GiftCertificate, Integer>{
    Page<GiftCertificate> findBySeveralTags(Set<String> namesSet, Pageable pageable);

    Integer findLastAddedId();

    Page<GiftCertificate> findAll(Pageable pageable);
    Optional<GiftCertificate> findById(Integer id);
}
