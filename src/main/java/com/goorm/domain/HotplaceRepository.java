package com.goorm.domain;

import com.goorm.dto.Album;
import com.goorm.dto.Hotplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotplaceRepository extends JpaRepository<Hotplace, Integer>{
    List<Hotplace> findByRegion(Integer placeId);
}
