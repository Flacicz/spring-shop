package com.example.springshop.controllers;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.springshop.entity.Image;
import com.example.springshop.repository.ImageRepository;

@RestController
public class ImageController {
	@Autowired
	private ImageRepository imageRepository;

	@GetMapping("/images/{id}")
	private ResponseEntity<?> getIMageById(@PathVariable("id") Long id) {
		Image image = imageRepository.findById(id).orElse(null);
		return ResponseEntity.ok()
				.header("fileName", image.getOriginalFileName())
				.contentType(MediaType.valueOf(image.getContentType()))
				.contentLength(image.getSize())
				.body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
	}
}
