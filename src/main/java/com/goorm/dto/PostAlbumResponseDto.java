package com.goorm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostAlbumResponseDto {
    private Integer id;
    private List<HotplaceDto> hotPlaceList;
}
