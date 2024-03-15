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
    @GeneratedValue (strategy=GenerationType.AUTO)
    private Integer hotplace_id;

    @Column(nullable=false)
    private Integer region;

    @Column(length=20, nullable=false)
    private String hotplace_title;

    @Column(nullable=false)
    private double hotplace_latitude;

    @Column(nullable=false)
    private double hotplace_longitude;

    @Column(length=5000)
    private String hotplace_content;

    @Column(length=255)
    private String hotplace_img;

    @Column
    private Integer hotplace_view;

    @Column
    private Integer hotplace_like;

    @Column
    private Integer hotplace_dislike;

    @ManyToMany(mappedBy="hotplaces")
    private List<Album> albums = new ArrayList<>();

    @ManyToMany(mappedBy="hotplaces")
    private List<Keyword> keywords = new ArrayList<>();

    @Builder
    public Hotplace(Integer hotplace_id, Integer region, String hotplace_title, double hotplace_latitude,
                    double hotplace_longitude, String hotplace_content, String hotplace_img, Integer hotplace_view,
                    Integer hotplace_like, Integer hotplace_dislike){
        this.hotplace_id = hotplace_id;
        this.region = region;
        this.hotplace_title = hotplace_title;
        this.hotplace_latitude = hotplace_latitude;
        this.hotplace_longitude = hotplace_longitude;
        this.hotplace_content = hotplace_content;
        this.hotplace_img = hotplace_img;
        this.hotplace_view = hotplace_view;
        this.hotplace_like = hotplace_like;
        this.hotplace_dislike = hotplace_dislike;
    }
}
