package com.goorm.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GetAlbumResultResponseDto {
    private Integer id;
    private String title;
    private String content;
    private List<HotplaceDto> hotPlaceList;
}
