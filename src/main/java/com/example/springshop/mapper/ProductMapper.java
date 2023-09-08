package com.example.springshop.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springshop.dto.ProductDTO;
import com.example.springshop.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "dateOfCreated", ignore = true)
	Product toProduct(ProductDTO productDTO);

	@InheritInverseConfiguration
	ProductDTO fromProduct(Product product);

	List<Product> toProductList(List<ProductDTO> productDTOs);

	List<ProductDTO> fromProductList(List<Product> products);
}
