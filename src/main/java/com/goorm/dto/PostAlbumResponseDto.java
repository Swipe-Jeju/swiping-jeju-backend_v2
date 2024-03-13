package com.goorm.dto;

import com.goorm.domain.Hotplace;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostAlbumResponseDto {
    private Integer id;
    private List<Hotplace> hotPlaceList;    // entity?dto?
}
