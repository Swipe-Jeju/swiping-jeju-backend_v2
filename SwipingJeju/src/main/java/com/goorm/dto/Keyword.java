package com.goorm.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    private Integer keyword_id;

    @Column(length =20, nullable =false)
    private String keyworld_title;

    @ManyToMany(mappedBy = "keywords")
    private List<Hotplace> hotplaces = new ArrayList<>();

    @Builder
    public Keyword(Integer keyword_id, String keyworld_title){
        this.keyword_id = keyword_id;
        this.keyworld_title = keyworld_title;
    }
}
