package com.goorm.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@Entity
public class Hotplace {
    @Id
    private Long place_id;

    @Column(length =500, nullable =false)
    private String place_title;
    @Column(length =10, nullable =false)
    private double longtitude;
    @Column(length =10, nullable =false)
    private double latitude;


    @Builder
    public Hotplace(Long place_id, String place_title, double longtitude, double latitude){
        this.place_id = place_id;
        this.place_title = place_title;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

}
