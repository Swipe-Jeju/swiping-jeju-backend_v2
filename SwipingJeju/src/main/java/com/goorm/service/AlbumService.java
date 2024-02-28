package com.goorm.service;


import com.goorm.domain.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
    }

    public Long getAlbumCount(){
        return albumRepository.count();
    }
}
