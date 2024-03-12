package com.goorm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HotplaceRepository extends JpaRepository<Hotplace, Integer>{
    // automatically, create basic JPA CRUD by Jparepository
    List<Hotplace> findByRegion(Integer regionId);
}