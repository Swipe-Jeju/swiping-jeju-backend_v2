package com.goorm.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor
@Entity
public class Hotplace {
    @Id
    private Integer hotplace_id;

    @Column(length =20, nullable =false)
    private Integer region;
    @Column(length =20, nullable =false)
    private String hotplace_title;
    @Column(nullable =false)
    private double hotplace_longtitude;
    @Column(nullable =false)
    private double hotplace_latitude;
    @Column(length =5000)
    private String hotplace_content;
    @Column(length =255)
    private String hotplace_img;

    @ManyToMany(mappedBy = "hotplaces")
    private List<Album> albums = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "KeywordHotplace",
            joinColumns = @JoinColumn(name = "hotplace_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<Keyword> keywords = new ArrayList<>();

    @Builder
    public Hotplace(Integer hotplace_id, Integer region, String hotplace_title, double hotplace_longtitude, double hotplace_latitude, String hotplace_content, String hotplace_img){
        this.hotplace_id = hotplace_id;
        this.region = region;
        this.hotplace_title = hotplace_title;
        this.hotplace_longtitude = hotplace_longtitude;
        this.hotplace_latitude = hotplace_latitude;
        this.hotplace_content = hotplace_content;
        this.hotplace_img = hotplace_img;
    }

}
