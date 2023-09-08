package com.example.springshop.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springshop.dto.BucketDTO;
import com.example.springshop.service.BucketService;

@Controller
public class BucketController {
	@Autowired
	private BucketService bucketService;

	@GetMapping("/bucket")
	public String aboutBucket(Model model, Principal principal) {
		if (principal == null) {
			model.addAttribute("bucket", new BucketDTO());
		} else {
			BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
			model.addAttribute("bucket", bucketDTO);
		}

		return "bucket";
	}
}
