package com.pk.lab2.controller;

import com.pk.lab2.model.Product;
import com.pk.lab2.model.ProductDTO;
import com.pk.lab2.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping(("/product"))
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        try {
            Product product = productService.getProductById(productId);
            if (nonNull(product)) {
                return ResponseEntity.status(HttpStatus.OK).body(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody ProductDTO productDTO) {
        try {
            Product product = productService.updateProduct(productId, productDTO);
            if (nonNull(product)) {
                return ResponseEntity.status(HttpStatus.OK).body(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        try {
            boolean deleted = productService.deleteProduct(productId);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyMap());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
