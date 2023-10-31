package com.epam.esm.springsecuritydemo.controller;

import com.epam.esm.springsecuritydemo.dtos.TagDto;
import com.epam.esm.springsecuritydemo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/tag")
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("certificate/{id}")
    public ResponseEntity<List<TagDto>> findByCertificateId(@PathVariable Integer id) {
        List<TagDto> tagList = tagService.fidByCertificateId(id);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();

        return ResponseEntity.ok().body(tagList);
    }
}
