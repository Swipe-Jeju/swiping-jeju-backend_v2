package com.goorm.service;

import com.goorm.domain.HotplaceRepository;
import com.goorm.domain.Hotplace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class HotplaceService {
    private final HotplaceRepository hotplaceRepository;

    @Autowired
    public HotplaceService(HotplaceRepository hotplaceRepository){
        this.hotplaceRepository = hotplaceRepository;
    }

    public List<Hotplace> getHotplaceList(List<Integer> mapList, List<String> keywordList){
        // 지역 넘버와 일치하는지 확인해야됨
        // 관심 키워드와 일치하는지 확인해야됨
        List<Hotplace> hotPlaces = new ArrayList<>();
        for (Integer placeId : mapList) {
            List<Hotplace> hotPlacesByPlaceId = hotplaceRepository.findByRegion(placeId);
            for (Hotplace hotPlace : hotPlacesByPlaceId) {
                if (keywordList.contains(hotPlace.getKeywords())) { //
                    hotPlaces.add(hotPlace);
                }
            }
        }
        return hotPlaces;
    }
}
