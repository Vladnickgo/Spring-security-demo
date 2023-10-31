package com.epam.esm.springsecuritydemo.service;

import com.epam.esm.springsecuritydemo.dtos.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> fidByCertificateId(Integer certificateId);
}
