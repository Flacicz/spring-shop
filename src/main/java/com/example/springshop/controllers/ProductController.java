package com.example.springshop.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.springshop.repository.ProductRepository;
import com.example.springshop.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("products", productRepository.findAll());
		return "main";
	}

	@GetMapping("/products/{id}")
	public String productInfo(@PathVariable(value = "id") Long id, Model model) {
		if (!productRepository.existsById(id)) {
			return "redirect:/";
		}

		model.addAttribute("product", productService.findProductById(id));

		return "product_info";
	}

	@GetMapping("/products/{id}/bucket")
	public String addBucket(@PathVariable Long id, Principal principal) {
		if (principal == null) {
			return "redirect:/";
		}

		productService.addToUserBucket(id, principal.getName());
		return "redirect:/";
	}
}
