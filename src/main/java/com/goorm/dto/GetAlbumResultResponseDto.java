package com.goorm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetAlbumResultResponseDto {
    private Integer id;
    private String title;
    private String content;
    private List<HotplaceDto> hotPlaceList;
}
