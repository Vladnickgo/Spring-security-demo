package com.epam.esm.springsecuritydemo.controller;

import com.epam.esm.springsecuritydemo.dtos.GiftCertificateDto;
import com.epam.esm.springsecuritydemo.dtos.GiftCertificateWithDataDto;
import com.epam.esm.springsecuritydemo.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateServiceImpl giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("")
    public ResponseEntity<PagedModel<GiftCertificateDto>> findAll(Pageable pageable) {
        Page<GiftCertificateDto> certificates = giftCertificateService.findAll(pageable);
        List<Link> certificateLinks = certificates.stream()
                .map(t -> linkTo(methodOn(GiftCertificateController.class).findById(t.getId())).withSelfRel())
                .toList();
        return getPagedModelResponseEntity(pageable, certificates, certificateLinks);
    }

    @GetMapping("/data")
    public ResponseEntity<PagedModel<EntityModel<GiftCertificateWithDataDto>>> findAllWithData(Pageable pageable) {
        Page<GiftCertificateWithDataDto> certificates = giftCertificateService.findAllWithData(pageable);

        List<EntityModel<GiftCertificateWithDataDto>> entityModels = certificates.getContent().stream()
                .map(t -> EntityModel.of(t, Link.of(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/certificates/" + t.getId())))
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(certificates.getSize(), certificates.getNumber(), certificates.getTotalElements(), certificates.getTotalPages());

        PagedModel<EntityModel<GiftCertificateWithDataDto>> pagedModel = PagedModel.of(entityModels, metadata);

        if (certificates.hasPrevious()) {
            String prevLink = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", certificates.getNumber() - 1)
                    .replaceQueryParam("size", certificates.getSize())
                    .toUriString();
            pagedModel.add(Link.of(prevLink, "prev"));
        }
        long totalPages = pagedModel.getMetadata().getTotalPages();
        for (int i = 0; i < totalPages; i++) {
            String pageLink = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", i + 1)
                    .replaceQueryParam("size", certificates.getSize())
                    .toUriString();
            pagedModel.add(Link.of(pageLink, "pageLink"));
        }
        if (certificates.hasNext()) {
            String nextLink = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", certificates.getNumber() + 1)
                    .replaceQueryParam("size", certificates.getSize())
                    .toUriString();
            pagedModel.add(Link.of(nextLink, "next"));
        }
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<GiftCertificateDto>> findById(@PathVariable Integer id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.findById(id);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .toUriString();
        Link selfLink = Link.of(uriString);
        EntityModel<GiftCertificateDto> entityModel = EntityModel.of(giftCertificateDto, selfLink);
        return ResponseEntity.ok(entityModel);
    }

    private ResponseEntity<PagedModel<GiftCertificateDto>> getPagedModelResponseEntity(@PageableDefault(page = 1, sort = "name") Pageable pageable, Page<GiftCertificateDto> all, List<Link> certificateLinks) {
        Link selfLink = linkTo(methodOn(GiftCertificateController.class).findAll(pageable)).withSelfRel();
        PagedModel<GiftCertificateDto> pagedModel = PagedModel.of(all.getContent(), new PagedModel.PageMetadata(all.getSize(), all.getNumber(), all.getTotalElements()), certificateLinks);
        pagedModel.add(selfLink);
        if (all.hasPrevious()) {
            String prevLink = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", all.getNumber() - 1)
                    .replaceQueryParam("size", all.getSize())
                    .toUriString();
            pagedModel.add(Link.of(prevLink, "prev"));
        }
        if (all.hasNext()) {
            String nextLink = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", all.getNumber() + 1)
                    .replaceQueryParam("size", all.getSize())
                    .toUriString();
            pagedModel.add(Link.of(nextLink, "next"));
        }
        return ResponseEntity.ok(pagedModel);
    }
}
