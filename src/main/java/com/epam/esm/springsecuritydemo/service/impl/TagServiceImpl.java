package com.epam.esm.springsecuritydemo.service.impl;

import com.epam.esm.springsecuritydemo.dtos.TagDto;
import com.epam.esm.springsecuritydemo.repository.impl.TagRepositoryImpl;
import com.epam.esm.springsecuritydemo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepositoryImpl tagRepository;

    @Autowired
    public TagServiceImpl(TagRepositoryImpl tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public List<TagDto> fidByCertificateId(Integer certificateId) {
        return tagRepository.findAllByCertificateId(certificateId).stream().map(t->TagDto.builder()
                .id(t.getId())
                .name(t.getName())
                .build()).collect(Collectors.toList());
    }
}
