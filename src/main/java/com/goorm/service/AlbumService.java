package com.goorm.service;


import com.goorm.domain.AlbumRepository;
import com.goorm.domain.HotplaceRepository;
import com.goorm.domain.Album;
import com.goorm.domain.Hotplace;
import com.goorm.dto.PatchAlbumRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final HotplaceRepository hotplaceRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, HotplaceRepository hotplaceRepository){   //HotplaceRepository hotplaceRepository
        this.albumRepository = albumRepository;
        this.hotplaceRepository = hotplaceRepository;
    }

    public Integer getAlbumCount(){
        long cnt = albumRepository.count();
        return Math.toIntExact(cnt);
    }

    public Integer postAlbum(){
        Album album = new Album();
        Album savedAlbum = albumRepository.save(album);
        return savedAlbum.getAlbum_id();
    }



    public void postAlbumHotplaces(PatchAlbumRequestDto patchAlbumRequestDto){
          // 해당하는 앨범에,
        Integer album_id = patchAlbumRequestDto.getAlbumID();
        List<Integer> hotplaceIds = patchAlbumRequestDto.getMapList();
        String album_title = patchAlbumRequestDto.getAlbumTitle();
        String album_content = patchAlbumRequestDto.getAlbumContent();

        Optional<Album> optionalAlbum = albumRepository.findById(album_id);

        if(optionalAlbum.isPresent()){
            Album album = optionalAlbum.get();

            album.setAlbum_title(album_title);
            album.setAlbum_content(album_content);

            // 최단거리 경로로 재설정 한 후에 반환해야됨

            // 받은 모든 핫플에 대해,
            for(Integer hotplaceId :hotplaceIds){
                Optional<Hotplace> optionalHotplace = hotplaceRepository.findById(hotplaceId);
                if(optionalHotplace.isPresent()){
                    Hotplace hotplace = optionalHotplace.get();

                    // 메인로직  - hotplace list 추가
                    album.getHotplaces().add(hotplace);

                }else{
                    // 예외처리
                }

            }
        } else {
            // 예외처리
        }
    }


    public Album getAlbum(Integer album_id){
        Optional<Album> albumOptional = albumRepository.findById(album_id);
        return albumOptional.orElse(null);
    }
}
