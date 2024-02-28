package com.goorm.domain;

import com.goorm.dto.Album;
import org.springframework.data.jpa.repository.JpaRepository;

// 인터페이스 생성해 jpa repository 상속하면 기본 crud메소드 자동 생성
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
