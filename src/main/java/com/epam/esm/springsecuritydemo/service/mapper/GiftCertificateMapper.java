package com.epam.esm.springsecuritydemo.service.mapper;

import com.epam.esm.springsecuritydemo.dtos.GiftCertificateDto;
import com.epam.esm.springsecuritydemo.entity.GiftCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {

    @Mapping(source = "createDate", target = "createDate", dateFormat = "dd/MM/yyyy")
    @Mapping(source = "lastUpdateDate", target = "lastUpdateDate", dateFormat = "dd/MM/yyyy")
    GiftCertificate mapDtoToEntity(GiftCertificateDto giftCertificateDto);

    @Mapping(source = "createDate", target = "createDate", dateFormat = "dd/MM/yyyy")
    @Mapping(source = "lastUpdateDate", target = "lastUpdateDate", dateFormat = "dd/MM/yyyy")
    GiftCertificateDto mapEntityToDto(GiftCertificate giftCertificate);
}
