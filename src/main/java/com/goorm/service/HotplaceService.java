package com.goorm.service;

import com.goorm.domain.HotplaceRepository;
import com.goorm.domain.Hotplace;
import com.goorm.domain.Keyword;
import com.goorm.dto.PostAlbumRequestDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class HotplaceService {
    private final HotplaceRepository hotplaceRepository;

    @Autowired
    public HotplaceService(HotplaceRepository hotplaceRepository){
        this.hotplaceRepository = hotplaceRepository;
    }

    // 2. [Entity] 선택지역 & 키워드에 따른 Hotplaces 반환
    public List<Hotplace> curateHotplaces(PostAlbumRequestDto postAlbumRequestDto){
        List<Hotplace> curatedHotplaces = new ArrayList<>();

        List<Integer> regions = postAlbumRequestDto.getMapList();
        List<String> keywords = postAlbumRequestDto.getKeywordList();

        for(Integer region : regions){
            List<Hotplace> hotplacesByRegion = hotplaceRepository.findByRegion(region);

            for(Hotplace hotplace : hotplacesByRegion) {
                for(Keyword keywordObj : hotplace.getKeywords()) {
                    String keywordTitle = keywordObj.getKeyword_title();
                    if(keywords.contains(keywordTitle)) {
                        curatedHotplaces.add(hotplace);
                        break;
                    }
                }
            }
        }

        // 10개만 랜덤선택
        Collections.shuffle(curatedHotplaces);
        List<Hotplace> hotplaces = new ArrayList<>();
        for(int i =0; i<Math.min(10, curatedHotplaces.size()); i++) {
            hotplaces.add(curatedHotplaces.get(i));
        }

        return hotplaces;
    }

    // 4. [Entity] Hotplaces List
    public List<Hotplace> getHotplaces(List<Integer> hids){
        List<Hotplace> hotplaces = new ArrayList<>();

        for(Integer hid : hids) {
            Optional<Hotplace> hotplaceOptional = hotplaceRepository.findById(hid);
            if (hotplaceOptional.isPresent()) {
                Hotplace hotplace = hotplaceOptional.get();
                hotplaces.add(hotplace);
            } else {
                throw new EntityNotFoundException("Hotplace Id: " + hid + "not found");
            }
        }

        return hotplaces;
    }

    // sync view-like-dislike
    public void countDislike(){
        List<Hotplace> hotplaceToUpdate = hotplaceRepository.findDislikeNeed();
        for(Hotplace hotplace : hotplaceToUpdate) {
            int view = hotplace.getHotplace_view();
            int like = hotplace.getHotplace_like();
            hotplace.setHotplace_dislike(Math.max(0, view-like));
            hotplace.setHotplace_like(Math.min(view, like));
        }
    }

}
