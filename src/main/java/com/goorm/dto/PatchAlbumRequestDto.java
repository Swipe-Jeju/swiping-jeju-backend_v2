package com.goorm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatchAlbumRequestDto {
    private Integer id;
    private String title;
    private String content;
    private List<Integer> likeIdList;
}
