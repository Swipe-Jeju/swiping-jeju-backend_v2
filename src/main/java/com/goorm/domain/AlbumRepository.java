package com.goorm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 인터페이스 생성해 jpa repository 상속하면 기본 crud메소드 자동 생성
@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
