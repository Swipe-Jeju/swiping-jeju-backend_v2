package com.goorm.service;

import com.goorm.domain.AlbumRepository;
import com.goorm.domain.HotplaceRepository;
import com.goorm.domain.Album;
import com.goorm.domain.Hotplace;
import com.goorm.dto.GetAlbumResultResponseDto;
import com.goorm.dto.HotplaceDto;
import com.goorm.dto.PatchAlbumRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@RequiredArgsConstructor
@Service
public class AlbumService {

    @Autowired
    private final AlbumRepository albumRepository;
    @Autowired
    private final HotplaceRepository hotplaceRepository;

    private ModelMapper modelMapper;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, HotplaceRepository hotplaceRepository) {
        this.albumRepository = albumRepository;
        this.hotplaceRepository = hotplaceRepository;
        this.modelMapper = new ModelMapper();
    }

    @Transactional
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
        Integer album_id = patchAlbumRequestDto.getId();
        List<Integer> hotplaceIds = patchAlbumRequestDto.getLikeIdList();
        String album_title = patchAlbumRequestDto.getTitle();
        String album_content = patchAlbumRequestDto.getContent();

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


    // 4. 최종앨범으로 반환할 album result
    public GetAlbumResultResponseDto getAlbum(Integer aid) {
        // 앨범 세팅
        Optional<Album> albumOptional = albumRepository.findById(aid);
        GetAlbumResultResponseDto album = modelMapper.map(albumOptional, GetAlbumResultResponseDto.class);

        // 핫플리스트 세팅
        List<Integer> hids = new ArrayList<>();
        // ?? hids 를 가져올 부분 필요!
        List<HotplaceDto> hotplaces = getHotplaceList(hids);

        album.setHotPlaceList(hotplaces);

        return album;
    }

    // 4. 최종앨범에 포함된 hotplace list
    public List<HotplaceDto> getHotplaceList(List<Integer> hids){
        List<HotplaceDto> hotplaces = new ArrayList<>();

        for(Integer hid : hids) {
            Optional<Hotplace> hotplaceOptional = hotplaceRepository.findById(hid);
                //.orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id))
            hotplaceOptional.ifPresent( hotplace -> hotplaces.add(modelMapper.map(hotplace, HotplaceDto.class)));
        }

        return hotplaces;//.orElse(null);
    }
}