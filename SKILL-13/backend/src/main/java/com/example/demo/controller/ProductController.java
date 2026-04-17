package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("API is running successfully!");
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = Arrays.asList(
            new Product(1L, "Laptop", 75000.00, "High-performance laptop"),
            new Product(2L, "Mouse", 1500.00, "Wireless optical mouse"),
            new Product(3L, "Keyboard", 3000.00, "Mechanical keyboard"),
            new Product(4L, "Monitor", 25000.00, "27-inch 4K display"),
            new Product(5L, "Headphones", 5000.00, "Noise-cancelling headphones")
        );
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = new Product(id, "Sample Product", 1000.00, "Sample Description");
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setId(System.currentTimeMillis());
        return ResponseEntity.ok(product);
    }
}
