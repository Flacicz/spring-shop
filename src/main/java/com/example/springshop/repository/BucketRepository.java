package com.example.springshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springshop.entity.Bucket;

public interface BucketRepository extends JpaRepository<Bucket, Long> {

}
