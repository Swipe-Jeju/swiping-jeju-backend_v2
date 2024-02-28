package com.goorm.domain;

import com.goorm.dto.Album;
import com.goorm.dto.Hotplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotplaceRepository extends JpaRepository<Hotplace, Integer>{
}
