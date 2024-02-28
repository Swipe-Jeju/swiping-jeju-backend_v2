package com.goorm.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@Entity
public class Keywords {
    @Id
    private Long keyword_id;

    @Column(length =10, nullable =false)
    private String keyworld_title;

    @Builder
    public Keywords(Long keyword_id, String keyworld_title){
        this.keyword_id = keyword_id;
        this.keyworld_title = keyworld_title;
    }
}
