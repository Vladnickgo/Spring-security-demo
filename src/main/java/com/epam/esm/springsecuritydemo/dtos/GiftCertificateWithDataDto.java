package com.epam.esm.springsecuritydemo.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class GiftCertificateWithDataDto {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    private LocalDate createDate;
    private LocalDate lastUpdateDate;
    private List<TagDto> tagDtoList;
}
