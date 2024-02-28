package com.goorm.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class Album {
    private Long album_cnt;

    public Album(Long album_cnt){
        this.album_cnt = album_cnt;
    }

    // Pk 지정
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long album_id;

    //private String album_title;    // ai생성
    @Column(length =500, nullable =false)
    private String place_id;

    @Column(length =50)
    private String album_title;
    @Column(length =5000)
    private String album_content;
    @Column(length =255)
    private String album_custommap;

    @ManyToMany
    @JoinTable(
            name = "album_place",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id")
    )
    private List<Hotplace> hotplaces = new ArrayList<>();

    // BUild 하기
    @Builder
    public Album(Long album_id, String place_id, String album_title,String album_content, String album_custommap){
        this.album_id = album_id;
        this.place_id = place_id;
        this.album_title = album_title;
        this.album_content = album_content;
        this.album_custommap = album_custommap;
    }
}
