package com.example.springshop.controllers;

import java.io.IOException;
import java.util.List;

import com.example.springshop.dto.UserDTO;
import com.example.springshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.springshop.dto.ProductDTO;
import com.example.springshop.service.ProductService;

@Controller
public class AdminController {
    private final ProductService productService;
    private final UserService userService;

    public AdminController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    // products
    @GetMapping("/create_product")
    public String productCreatePage() {
        return "create_product";
    }

    @PostMapping("/create_product")
    public String productCreate(@RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                ProductDTO product) throws IOException {
        productService.saveProduct(product, file1, file2, file3);
        return "redirect:/";
    }

    @GetMapping("/edit_product/{id}")
    public String editProductPage(@PathVariable(value = "id") Long id, Model model) {
        List<ProductDTO> result = productService.findProductById(id);

        if (result == null) {
            return "redirect:/";
        }

        model.addAttribute("product", result);

        return "edit_product";
    }

    @PostMapping("/edit_product/{id}")
    public String editProduct(ProductDTO productDTO) {
        productService.editProduct(productDTO);
        return "redirect:/";
    }

    @GetMapping("/delete_product/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    // users
    @GetMapping("/edit_user/{id}")
    public String editStudentPage(@PathVariable(value = "id") Long id, Model model) {
        UserDTO userDTO = userService.findUserById(id);

        if (userDTO == null) {
            return "redirect:/users";
        }

        model.addAttribute("user", userDTO);
        model.addAttribute("id", id);

        return "edit_user";
    }

    @PostMapping("/edit_student/{id}")
    public String editStudent(UserDTO userDTO) {
        userService.editUser(userDTO);
        return "redirect:/users";
    }

    @GetMapping("delete_user/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
