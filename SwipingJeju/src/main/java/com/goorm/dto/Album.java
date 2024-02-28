package com.goorm.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@Entity
public class Album {
    // Pk 지정
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long album_id;

    //private String album_title;    // ai생성
    @Column(length =500, nullable =false)
    private String place_id;
    //private List<Integer> hotplace_id;
    //private List<String> keywords;

    // BUild 하기
    @Builder
    public Album(Long album_id, String place_id){
        this.album_id = album_id;
        this.place_id = place_id;
    }
}
