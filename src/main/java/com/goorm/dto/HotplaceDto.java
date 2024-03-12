package com.goorm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class HotplaceDto {
    private Integer id;
    private String title;
    private String description;
    private double lat;
    private double lng;
    private String img;
    private List<String> keywords;
}
