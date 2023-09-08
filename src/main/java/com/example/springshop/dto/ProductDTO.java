package com.example.springshop.dto;

import java.math.BigDecimal;
import java.util.List;

import com.example.springshop.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
	private String title;
	private String description;
	private BigDecimal price;
	private List<Image> images;
	private Long previewImageId;
}
