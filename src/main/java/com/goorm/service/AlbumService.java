package com.goorm.service;

import com.goorm.domain.AlbumRepository;
import com.goorm.domain.HotplaceRepository;
import com.goorm.domain.Album;
import com.goorm.domain.Hotplace;
import com.goorm.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@RequiredArgsConstructor
@Service
public class AlbumService {

    @Autowired
    private final AlbumRepository albumRepository;
    @Autowired
    private final HotplaceRepository hotplaceRepository;

    private ModelMapper modelMapper;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, HotplaceRepository hotplaceRepository) {
        this.albumRepository = albumRepository;
        this.hotplaceRepository = hotplaceRepository;
        this.modelMapper = new ModelMapper();
    }

    // 1. 앨범 카운트 반환
    @Transactional
    public Integer getAlbumCount(){
        long cnt = albumRepository.count();
        return Math.toIntExact(cnt);
    }

    // 2. 앨범 최초 생성
    public PostAlbumResponseDto postAlbum(PostAlbumRequestDto postAlbumRequestDto){
        PostAlbumResponseDto postAlbumResponseDto = new PostAlbumResponseDto();

        // 앨범 생성
        Album album = new Album();
        Album savedAlbum = albumRepository.save(album);
        Integer id = savedAlbum.getAlbum_id();
        postAlbumResponseDto.setId(id);
        // 추천 Hotplace 반환
        List<Hotplace> hotplaces = curateHotplaces(postAlbumRequestDto);
        postAlbumResponseDto.setHotPlaceList(hotplaces);

        return postAlbumResponseDto;
    }

    // 2. 선택지역 & 키워드에 따른 Hotplaces 반환
    public List<Hotplace> curateHotplaces(PostAlbumRequestDto postAlbumRequestDto){
        List<Hotplace> curatedHotplaces = new ArrayList<>();

        List<Integer> regions = postAlbumRequestDto.getMapList();
        List<String> keywords = postAlbumRequestDto.getKeywordList();

        for(Integer region : regions){
            List<Hotplace> hotplacesByRegion = hotplaceRepository.findByRegion(region);

            for(Hotplace hotplace : hotplacesByRegion) {
                for(String keyword : keywords) {
                    if(hotplace.getKeywords().contains(keyword)) {
                        curatedHotplaces.add(hotplace);
                        break;
                    }
                }
            }
        }

        // 10개만 짜르기 (랜덤)
        return curatedHotplaces;
    }

    // 3. 앨범 최종 저장
    @Transactional
    public void saveAlbum(PatchAlbumRequestDto patchAlbumRequestDto) {
        // Dto 해체
        Integer aid = patchAlbumRequestDto.getId();
        String title = patchAlbumRequestDto.getTitle();
        String content = patchAlbumRequestDto.getContent();
        List<Integer> hids = patchAlbumRequestDto.getLikeIdList();
        List<Hotplace> hotplaces = getHotplaces(hids);

        // Dto -> Entity -> save
        Optional<Album> optionalAlbum = albumRepository.findById(aid);  // to avoid NullPointerException

        if (optionalAlbum.isPresent()){
            Album album = optionalAlbum.get();

            album.setAlbum_title(title);
            album.setAlbum_content(content);
            album.setHotplaces(hotplaces);

            albumRepository.save(album);
        }else{
            throw new EntityNotFoundException("Album Id: " + aid + "not found");
        }

        // 최단거리 알고리즘 돌린 후 정렬한 HotplaceList 저장 필요
    }


    // 4. 최종앨범으로 반환할 album result
    public GetAlbumResultResponseDto getAlbum(Integer aid) {
        Optional<Album> albumOptional = albumRepository.findById(aid);
        GetAlbumResultResponseDto album = modelMapper.map(albumOptional, GetAlbumResultResponseDto.class);
        // ? need check HotplaceLists Setting

        return album;
    }

    // [Entity] Hotplaces List
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

}