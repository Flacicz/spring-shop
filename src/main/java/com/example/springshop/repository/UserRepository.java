package com.example.springshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springshop.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
	User findByUsername(String username);
}
