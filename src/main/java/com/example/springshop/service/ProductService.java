package com.example.springshop.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springshop.dto.ProductDTO;
import com.example.springshop.entity.Bucket;
import com.example.springshop.entity.Image;
import com.example.springshop.entity.Product;
import com.example.springshop.entity.User;
import com.example.springshop.mapper.ImageMapper;
import com.example.springshop.mapper.ProductMapper;
import com.example.springshop.repository.ProductRepository;
import com.example.springshop.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ImageMapper imageMapper;

	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	private final BucketService bucketService;
	private final ImageService imageService;

	public ProductService(ProductRepository productRepository, UserRepository userRepository,
			BucketService bucketService, ImageService imageService) {
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.bucketService = bucketService;
		this.imageService = imageService;
	}

	public List<ProductDTO> listOfProducts() {
		return productMapper.fromProductList(productRepository.findAll());
	}

	public ProductDTO getProductById(Long id) {
		return productMapper.fromProduct(productRepository.findById(id).orElse(null));
	}

	public void saveProduct(ProductDTO product, MultipartFile file1, MultipartFile file2, MultipartFile file3)
			throws IOException {
		Image image1;
		Image image2;
		Image image3;
		if (file1.getSize() != 0) {
			image1 = imageMapper.fromMultipartToImage(file1);
			image1.setPreviewImage(true);
			imageService.addImageToProduct(product, image1);
		}
		if (file2.getSize() != 0) {
			image2 = imageMapper.fromMultipartToImage(file2);
			imageService.addImageToProduct(product, image2);
		}
		if (file3.getSize() != 0) {
			image3 = imageMapper.fromMultipartToImage(file3);
			imageService.addImageToProduct(product, image3);
		}
		log.info("Saving new Product. Title: {}", product.getTitle());
		Product productFromDb = productRepository.save(productMapper.toProduct(product));
		productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
		productRepository.save(productMapper.toProduct(product));
	}

	public List<ProductDTO> findProductById(Long id) {
		List<Product> res = new ArrayList<>();

		if (!productRepository.existsById(id)) {
			return null;
		}

		Optional<Product> product = productRepository.findById(id);
		product.ifPresent(res::add);

		return productMapper.fromProductList(res);

	}

	public boolean deleteProduct(Long id) {
		if (!productRepository.existsById(id)) {
			return false;
		}

		productRepository.deleteById(id);

		return true;
	}

	public boolean editProduct(ProductDTO productDTO) {
		String title = productDTO.getTitle();

		if (productRepository.findByTitle(title) == null) {
			return false;
		}

		Product product = Product.builder()
				.title(title)
				.description(productDTO.getDescription())
				.price(productDTO.getPrice())
				.build();

		productRepository.save(product);

		log.info("Edit new User with title : {}", title);

		return true;
	}

	@Transactional
	public void addToUserBucket(Long productId, String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new RuntimeException("User not found" + username);
		}

		Bucket bucket = user.getBucket();
		if (bucket == null) {
			Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
			user.setBucket(newBucket);
			userRepository.save(user);
		} else {
			bucketService.addProducts(bucket, Collections.singletonList(productId));
		}

	}
}
