package com.goorm.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Album {
    //private Integer album_cnt;

    /*
    public Album(Integer album_cnt){
        this.album_cnt = album_cnt;
    }*/

    // Pk 지정
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer album_id;

    //private String album_title;    // ai생성
    @Column(length =50)
    private String album_title;
    @Column(length =5000)
    private String album_content;
    //@Column(length =255)
    //private String album_custommap;

    @ManyToMany
    @JoinTable(
            name = "AlbumHotplace",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "hotplace_id")
    )
    private List<Hotplace> hotplaces = new ArrayList<>();

    // BUild 하기
    @Builder
    public Album(Integer album_id, String album_title,String album_content, String album_custommap){
        this.album_id = album_id;
        this.album_title = album_title;
        this.album_content = album_content;
        //this.album_custommap = album_custommap;
    }
}
