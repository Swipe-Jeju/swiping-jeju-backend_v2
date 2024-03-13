package com.goorm.controller;

import com.goorm.dto.*;
import com.goorm.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@RequiredArgsConstructor
@RestController
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService){
        this.albumService = albumService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("True");
    }

    // 1. 앨범 총 갯수 반환
    @GetMapping("/cnt")
    public ResponseEntity<String> getAlbumCount() {
        Integer cnt = albumService.getAlbumCount();
        // db 에서 앨범 총갯수 세기
        String jsonResponse = "{\"cnt\": " + cnt + "}";
        return ResponseEntity.ok().body(jsonResponse);
    }

    // 2. 취향 등록 & 후보 핫플리스트 반환
    @PostMapping("/album/apply")
    public ResponseEntity<PostAlbumResponseDto> albumCreate(@RequestBody PostAlbumRequestDto postAlbumRequestDto) {
        PostAlbumResponseDto postAlbumResponseDto = albumService.postAlbum(postAlbumRequestDto);

        return ResponseEntity.ok().body(postAlbumResponseDto);
    }

    // 3. 선택 핫플리스트 반영 & 최종 앨범 만들기
    @PostMapping ("/album/create")
    public ResponseEntity<Void> albumPost(@RequestBody PatchAlbumRequestDto patchAlbumRequestDto) {
        albumService.saveAlbum(patchAlbumRequestDto);
        return ResponseEntity.ok().build();
    }

    // 4. 앨범 최종 결과 반환
    @GetMapping("/album/result")
    public ResponseEntity<GetAlbumResultResponseDto> albumResult(@RequestParam("id") Integer id) {
        GetAlbumResultResponseDto getAlbumResultResponseDto = albumService.getAlbum(id);
        return ResponseEntity.ok().body(getAlbumResultResponseDto);
    }

}