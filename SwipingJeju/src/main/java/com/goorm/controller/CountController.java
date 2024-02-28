package com.goorm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountController {

    @Autowired
    @GetMapping("/api/v1/cnt")
    public ResponseEntity<Integer> countAlbum(){
        int cnt = 1;
        return ResponseEntity.ok().body(cnt);
    }
}
