package com.goorm.controller;


import com.goorm.dto.Album;
import com.goorm.dto.Hotplace;
import com.goorm.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService){
        this.albumService = albumService;
    }
    @GetMapping("/cnt")
    public ResponseEntity<Long> getAlbumCount(){
        Long cnt = albumService.getAlbumCount();
        // db 에서 앨범 총갯수 세기
        return ResponseEntity.ok().body(cnt);
    }

    // 취향 등록
    @PostMapping("/collection/apply")
    public ResponseEntity<String> tastePost() {  //List<Hotplace>
        // 앨범 하나 생성후
        // 받은 취향 싹 다 등록하기 (관심지역 / 자연어 취향)
        String taste = "취향";
        System.out.println("취향");
        return ResponseEntity.ok().body(taste);
    }

    // 선택지 반영
    @PostMapping ("/collection/create")
    public ResponseEntity<String> albumPost(){

        // 최단거리 알고리즘 돌린 후정렬한 리스트 반환
        // 그 리스트 db 에 등록
        String album = "";
        return ResponseEntity.ok(album);
    }

    //총 앨범 반환
    @GetMapping("/collection")
    public ResponseEntity<String> albumResult(@RequestParam("uid") int id){ // Album
        // 앨범 모든 정보 다 토해내기
        String album = "";
        // 이 여행자는 어떤 스타일인지 ai 돌려서 반환
        return ResponseEntity.ok().body(album);
    }

}
