package com.goorm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatchAlbumRequestDto {
    //(@RequestBody Integer albumID, @RequestBody List<Integer> likeList, @RequestBody String albumTitle, @RequestBody String albumContent)
    private Integer albumID;
    private List<Integer> likeList;
    private String albumTitle;
    private String albumContent;
    private List<Integer> mapList;
    private List<String> keywordList;
}
