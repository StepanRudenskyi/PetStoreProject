package org.example.petstore.controller;

import org.example.petstore.model.Product;
import org.example.petstore.model.ProductCategory;
import org.example.petstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getLandingPage(Model model) {
        List<ProductCategory> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        return "landing";
    }

    @GetMapping("/products")
    public String getProductsByCategory(@RequestParam("categoryId") int categoryId, Model model) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);

        // add success message if exists
        if (model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", model.asMap().get("successMessage"));
        }

        return "productsByCategory";
    }

}
