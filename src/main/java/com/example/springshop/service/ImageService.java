package com.example.springshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springshop.dto.ProductDTO;
import com.example.springshop.entity.Image;
import com.example.springshop.entity.Product;
import com.example.springshop.mapper.ProductMapper;

@Service
public class ImageService {
	@Autowired
	private ProductMapper productMapper;

	public void addImageToProduct(ProductDTO productDTO, Image image) {
		Product product = productMapper.toProduct(productDTO);

		image.setProduct(product);
		product.getImages().add(image);
	}
}
