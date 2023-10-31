package com.epam.esm.springsecuritydemo.service;

import com.epam.esm.springsecuritydemo.dtos.GiftCertificateDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GiftCertificateService {
    GiftCertificateDto save(GiftCertificateDto giftCertificateDto);

    Page<GiftCertificateDto> findAll(Pageable pageable);

    GiftCertificateDto findById(int certificateId);

      Page<GiftCertificateDto> findBySeveralTags(String name, Pageable pageable);

    @Transactional
    GiftCertificateDto update(Integer id, GiftCertificateDto giftCertificateDto);
}
