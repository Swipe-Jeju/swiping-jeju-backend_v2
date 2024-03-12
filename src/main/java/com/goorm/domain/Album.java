package com.goorm.domain;

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

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Integer album_id;

    @Column(length=50)
    private String album_title;

    @Column(length=5000)
    private String album_content;

    @ManyToMany
    @JoinTable(
            name = "AlbumHotplace",
            joinColumns = @JoinColumn(name="album_id"),
            inverseJoinColumns = @JoinColumn(name="hotplace_id")
    )
    private List<Hotplace> hotplaces = new ArrayList<>();

    @Builder
    public Album(Integer album_id, String album_title, String album_content){
        this.album_id = album_id;
        this.album_title = album_title;
        this.album_content = album_content;
    }
}