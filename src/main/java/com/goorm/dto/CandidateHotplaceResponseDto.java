package com.goorm.dto;

import com.goorm.domain.Hotplace;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CandidateHotplaceResponseDto {
    private Integer id;
    private List<Hotplace> hotplaceList;
}
