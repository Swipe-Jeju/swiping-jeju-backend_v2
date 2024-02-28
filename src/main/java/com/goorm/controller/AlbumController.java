package com.goorm.controller;


import com.goorm.dto.*;
import com.goorm.service.AlbumService;
import com.goorm.service.HotplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AlbumController {
    private final AlbumService albumService;
    private final HotplaceService hotplaceService;

    @Autowired
    public AlbumController(AlbumService albumService, HotplaceService hotplaceService){
        this.albumService = albumService;
        this.hotplaceService = hotplaceService;
    }
    @GetMapping("/cnt")
    public ResponseEntity<String> getAlbumCount(){
        Integer cnt = albumService.getAlbumCount();
        // db 에서 앨범 총갯수 세기
        String jsonResponse = "{\"cnt\": " + cnt + "}";
        return ResponseEntity.ok().body(jsonResponse);
    }

    // 취향 등록
    @PostMapping("/collection/apply")
    public ResponseEntity<CandidateHotplaceResponseDto> tastePost(@RequestBody PostAlbumRequestDto postAlbumRequestDto) {
        // 앨범 하나 생성후
        Integer id = albumService.postAlbum();

        // 조건으로 필터링
        List<Integer> mapList = postAlbumRequestDto.getMapList();
        List<String> keywordList = postAlbumRequestDto.getKeywordList();
        // 활용해서 hotplacelist 생성하기
        List<Hotplace> hotPlaces = hotplaceService.getHotplaceList(mapList, keywordList);
        /*
        // dummy
        List<Hotplace> hotplaceList = new ArrayList<>();
        Hotplace hotplace1 = new Hotplace(1, 12345, "Seongsan Ilchulbong", 33.458, 126.940, "Seongsan Ilchulbong is an iconic volcanic tuff cone located on Jeju Island, South Korea.", "seongsan-ilchulbong.jpg");
        hotplaceList.add(hotplace1);*/

        // json으로 합치기
        CandidateHotplaceResponseDto responseDTO = new CandidateHotplaceResponseDto();
        responseDTO.setId(id);
        responseDTO.setHotplaceList(hotPlaces);

        return ResponseEntity.ok().body(responseDTO);
    }

    // 선택지 반영 / 총 앨범 만들기
    @PostMapping ("/collection/create")
    public ResponseEntity<Void> albumPost(@RequestBody PatchAlbumRequestDto patchAlbumRequestDto){
       // 최단거리 알고리즘 돌린 후 정렬한 리스트 반환

        // 정리된 리스트를 post (db 에 등록)
        albumService.postAlbumHotplaces(patchAlbumRequestDto);

        return ResponseEntity.ok().build();
    }

    //총 앨범 반환
    @GetMapping("/collection")
    public ResponseEntity<Album> albumResult(@RequestParam("uid") Integer id){
        // 앨범 모든 정보 다 토해내기
        Album album = albumService.getAlbum(id);
        // 최단거리 알고리즘 적용해서 재정렬
        return ResponseEntity.ok().body(album);
    }

}
