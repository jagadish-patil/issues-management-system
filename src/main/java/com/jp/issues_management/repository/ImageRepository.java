package com.jp.issues_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jp.issues_management.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
