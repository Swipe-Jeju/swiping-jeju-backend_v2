package com.goorm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
    // automatically, create basic JPA CRUD by Jparepository
}