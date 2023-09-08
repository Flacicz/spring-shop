package com.example.springshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springshop.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
