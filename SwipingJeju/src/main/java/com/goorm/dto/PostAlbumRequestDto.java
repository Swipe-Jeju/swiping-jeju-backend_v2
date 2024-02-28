package com.goorm.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Getter
@Setter
public class PostAlbumRequestDto {
    private List<Integer> mapList;
    private List<String> keywordList;

}
