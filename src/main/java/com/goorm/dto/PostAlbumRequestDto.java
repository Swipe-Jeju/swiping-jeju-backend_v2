package com.goorm.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostAlbumRequestDto {
    private List<Integer> mapList;
    private List<String> keywordList;
}