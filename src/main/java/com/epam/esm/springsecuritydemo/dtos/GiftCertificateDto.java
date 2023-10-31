package com.epam.esm.springsecuritydemo.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class GiftCertificateDto {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    private LocalDate createDate;
    private LocalDate lastUpdateDate;
}
