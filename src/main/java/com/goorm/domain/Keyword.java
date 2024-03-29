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
public class Keyword {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer keyword_id;

    @Column(length =20, nullable=false)
    private String keyword_title;

    @ManyToMany
    @JoinTable(
            name = "KeywordHotplace",
            joinColumns = @JoinColumn(name="keyword_id"),
            inverseJoinColumns = @JoinColumn(name="hotplace_id")
    )
    private List<Hotplace> hotplaces = new ArrayList<>();

    @Builder
    public Keyword(Integer keyword_id, String keyword_title){
        this.keyword_id = keyword_id;
        this.keyword_title = keyword_title;
    }
}