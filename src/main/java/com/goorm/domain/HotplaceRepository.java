package com.goorm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HotplaceRepository extends JpaRepository<Hotplace, Integer>{
    // automatically, create basic JPA CRUD by Jparepository
    List<Hotplace> findByRegion(Integer regionId);

    @Modifying
    @Query("UPDATE Hotplace e SET e.hotplace_like = e.hotplace_like +1 WHERE e.hotplace_id = :id")
    void incrementLike(Integer id);

    @Modifying
    @Query("UPDATE Hotplace e SET e.hotplace_view = e.hotplace_view +1 WHERE e.hotplace_id = :id")
    void incrementView(Integer id);

    @Modifying
    @Query("SELECT h FROM Hotplace h WHERE h.hotplace_view!=0 AND h.hotplace_view!=h.hotplace_like")
    List<Hotplace> findDislikeNeed();
}