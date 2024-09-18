package org.example.petstore.controller;

import org.example.petstore.model.Product;
import org.example.petstore.model.ProductCategory;
import org.example.petstore.service.InventoryService;
import org.example.petstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    private final ProductService productService;
    private final InventoryService inventoryService;

    @Autowired
    public ProductController(ProductService productService, InventoryService inventoryService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
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

        // check quantity
        Map<Integer, Integer> productStockMap = new HashMap<>();

        for (Product product : products) {
            int stock = inventoryService.getStockByProduct(product.getId());
            productStockMap.put(product.getId(), stock);
        }

        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("productStockMap", productStockMap);

        // add success message if exists
        if (model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", model.asMap().get("successMessage"));
        }

        return "productsByCategory";
    }

}
