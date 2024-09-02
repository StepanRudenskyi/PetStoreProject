package org.example.petstore.controller;

import org.example.petstore.model.ProductCategory;
import org.example.petstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String getLandingPage(Model model) {
        List<ProductCategory> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }
}
