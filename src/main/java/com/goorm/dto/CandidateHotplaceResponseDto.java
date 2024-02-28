package com.goorm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CandidateHotplaceResponseDto {
    private Integer id;
    private List<Hotplace> hotplaceList;
}
