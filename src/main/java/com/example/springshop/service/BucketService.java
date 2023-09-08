package com.example.springshop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springshop.dto.BucketDTO;
import com.example.springshop.dto.BucketDetailsDTO;
import com.example.springshop.entity.Bucket;
import com.example.springshop.entity.Product;
import com.example.springshop.entity.User;
import com.example.springshop.repository.BucketRepository;
import com.example.springshop.repository.ProductRepository;
import com.example.springshop.repository.UserRepository;

@Service
public class BucketService {
	private final BucketRepository bucketRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public BucketService(BucketRepository bucketRepository, ProductRepository productRepository,
			UserRepository userRepository) {
		this.bucketRepository = bucketRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public Bucket createBucket(User user, List<Long> productIds) {
		Bucket bucket = new Bucket();
		bucket.setUser(user);
		List<Product> productList = getProductsRefById(productIds);
		bucket.setProducts(productList);
		return bucket;
	}

	private List<Product> getProductsRefById(List<Long> productIds) {
		return productIds.stream().map(productRepository::getReferenceById).collect(Collectors.toList());
	}

	public void addProducts(Bucket bucket, List<Long> productIds) {
		List<Product> products = bucket.getProducts();
		List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
		newProductList.addAll(getProductsRefById(productIds));
		bucket.setProducts(newProductList);
		bucketRepository.save(bucket);
	}

	public BucketDTO getBucketByUser(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null || user.getBucket() == null) {
			return new BucketDTO();
		}

		BucketDTO bucketDTO = new BucketDTO();
		Map<Long, BucketDetailsDTO> mapByProductId = new HashMap<>();

		List<Product> products = user.getBucket().getProducts();
		for (Product product : products) {
			BucketDetailsDTO detail = mapByProductId.get(product.getId());
			if (detail == null) {
				mapByProductId.put(product.getId(), new BucketDetailsDTO(product));
			} else {
				detail.setAmount(detail.getAmount().add(new BigDecimal("1.0")));
				detail.setSum(detail.getSum() + Double.parseDouble(product.getPrice().toString()));
			}
		}

		bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
		bucketDTO.aggregate();

		return bucketDTO;
	}
}
