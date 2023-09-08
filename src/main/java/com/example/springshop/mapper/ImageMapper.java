package com.example.springshop.mapper;

import java.io.IOException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.springshop.entity.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "originalFileName", ignore = true)
	@Mapping(target = "previewImage", ignore = true)
	@Mapping(target = "product", ignore = true)
	Image fromMultipartToImage(MultipartFile file) throws IOException;
}
