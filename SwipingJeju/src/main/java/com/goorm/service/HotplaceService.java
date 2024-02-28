package com.goorm.service;

import com.goorm.domain.HotplaceRepository;
import com.goorm.dto.Hotplace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotplaceService {
    private final HotplaceRepository hotplaceRepository;

    @Autowired
    public HotplaceService(HotplaceRepository hotplaceRepository){
        this.hotplaceRepository = hotplaceRepository;
    }

    public List<Hotplace> getHotplaceList(){
        // 지역 넘버와 일치하는지 확인해야됨
        // 관심 키워드와 일치하는지 확인해야됨
        return hotplaceRepository.findAll();
    }
}
