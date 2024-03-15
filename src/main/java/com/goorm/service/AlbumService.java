package com.goorm.service;

import com.goorm.domain.*;
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
import java.util.stream.Collectors;


//@RequiredArgsConstructor
@Service
public class AlbumService {

    @Autowired
    private final AlbumRepository albumRepository;
    @Autowired
    private final HotplaceRepository hotplaceRepository;

    private final HotplaceService hotplaceService;
    private final ModelMapper modelMapper;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, HotplaceRepository hotplaceRepository, HotplaceService hotplaceService, ModelMapper modelMapper) {
        this.albumRepository = albumRepository;
        this.hotplaceRepository = hotplaceRepository;
        this.hotplaceService = hotplaceService;
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Album.class, GetAlbumResultResponseDto.class)
                .addMapping(Album::getAlbum_id, GetAlbumResultResponseDto::setId)
                .addMapping(Album::getAlbum_title, GetAlbumResultResponseDto::setTitle)
                .addMapping(Album::getAlbum_content, GetAlbumResultResponseDto::setContent)
                .addMapping(Album::getHotplaces, GetAlbumResultResponseDto::setHotPlaceList);
        modelMapper.createTypeMap(Hotplace.class, HotplaceDto.class)
                .addMapping(Hotplace::getHotplace_id, HotplaceDto::setId)
                .addMapping(Hotplace::getHotplace_title, HotplaceDto::setTitle)
                .addMapping(Hotplace::getHotplace_content, HotplaceDto::setDescription)
                .addMapping(Hotplace::getHotplace_latitude, HotplaceDto::setLat)
                .addMapping(Hotplace::getHotplace_longitude, HotplaceDto::setLng)
                .addMapping(Hotplace::getHotplace_img, HotplaceDto::setImg)
                .addMapping(Hotplace::getHotplace_view, HotplaceDto::setView)
                .addMapping(Hotplace::getHotplace_like, HotplaceDto::setLike)
                .addMapping(Hotplace::getHotplace_dislike, HotplaceDto::setDislike);
    }

    // 1. 앨범 카운트 반환
    public Integer getAlbumCount(){
        long cnt = albumRepository.count();
        return Math.toIntExact(cnt);
    }

    // 2. 앨범 최초 생성
    @Transactional
    public PostAlbumResponseDto postAlbum(PostAlbumRequestDto postAlbumRequestDto){
        PostAlbumResponseDto postAlbumResponseDto = new PostAlbumResponseDto();

        // 앨범 생성
        Album album = new Album();
        Album savedAlbum = albumRepository.save(album);
        Integer id = savedAlbum.getAlbum_id();
        postAlbumResponseDto.setId(id);

        // 추천 Hotplace 반환
        List<Hotplace> hotplaces = hotplaceService.curateHotplaces(postAlbumRequestDto);
        List<HotplaceDto> hotplaceDtos = new ArrayList<>();
        for(Hotplace hotplace : hotplaces){
            HotplaceDto hDto = modelMapper.map(hotplace, HotplaceDto.class);
            // keyword 3개만 뽑아 매핑
            List<String> keyword3 = hotplace.getKeywords().stream()
                    .map(Keyword::getKeyword_title)
                    .limit(3)
                    .collect(Collectors.toList());
            hDto.setKeywords(keyword3);
            hotplaceDtos.add(hDto);
            // Hotplace View count
            hotplaceRepository.incrementView(hotplace.getHotplace_id());
        }

        postAlbumResponseDto.setHotPlaceList(hotplaceDtos);
        // keywords 3r개 + 좋아요/ 싫어요 개수 필요
        return postAlbumResponseDto;
    }

    // 3. 앨범 최종 저장
    @Transactional
    public void saveAlbum(PatchAlbumRequestDto patchAlbumRequestDto) {
        // Dto 해체
        Integer aid = patchAlbumRequestDto.getId();
        String title = patchAlbumRequestDto.getTitle();
        String content = patchAlbumRequestDto.getContent();
        List<Integer> hids = patchAlbumRequestDto.getLikeIdList();
        List<Hotplace> hotplaces = hotplaceService.getHotplaces(hids);

        // Dto -> Entity -> save
        Optional<Album> optionalAlbum = albumRepository.findById(aid);  // to avoid NullPointerException

        if (optionalAlbum.isPresent()){
            Album album = optionalAlbum.get();
            // set Album
            album.setAlbum_title(title);
            album.setAlbum_content(content);
            album.setHotplaces(hotplaces);
            albumRepository.save(album);

            // count Hotplace like
            for(Integer hid : hids) {
                hotplaceRepository.incrementLike(hid);
            }
            // update dislike
            hotplaceService.countDislike();
        }else{
            throw new EntityNotFoundException("Album Id: " + aid + "not found");
        }

        // 최단거리 알고리즘 돌린 후 정렬한 HotplaceList 저장 필요
    }


    // 4. 최종앨범으로 반환할 album result
    public GetAlbumResultResponseDto getAlbum(Integer aid) {
        Optional<Album> albumOptional = albumRepository.findById(aid);
        if(albumOptional.isPresent()){
            GetAlbumResultResponseDto album = modelMapper.map(albumOptional.get(), GetAlbumResultResponseDto.class);
            for(HotplaceDto hotplaceDto : album.getHotPlaceList()){
                hotplaceDto.setKeywords(null);
            }
            return album;
        } else {
            throw new EntityNotFoundException("Album Id: " + aid + "not found");
        }
    }

}