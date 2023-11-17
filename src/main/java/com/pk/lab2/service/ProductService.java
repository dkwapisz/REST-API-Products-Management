package com.pk.lab2.service;

import com.pk.lab2.model.Product;
import com.pk.lab2.model.ProductDTO;
import com.pk.lab2.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        // TODO Need to change - return only the most important details about products when calling getAll
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    public ResponseEntity<?> getProductById(String productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(product.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot found product with given ID: " + productId);
        }
    }

    @Transactional
    public ResponseEntity<Product> createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .quantity(productDTO.getQuantity())
                .price(productDTO.getPrice())
                .weight(productDTO.getWeight())
                .available(productDTO.isAvailable())
                .productCategory(productDTO.getProductCategory())
                .dateAdded(LocalDateTime.now())
                .dateLastUpdate(LocalDateTime.now())
                .productHistory(Collections.emptyList())
                .build();

        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Transactional
    public ResponseEntity<?> updateProduct(String productId, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(productId);

        // TODO Whole logic for updating + history saving

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot found product with given ID: " + productId);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteProduct(String productId) {
        boolean productExists = productRepository.existsById(productId);

        if (productExists) {
            productRepository.deleteById(productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product with ID: " + productId + " has been removed");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot found product with given ID: " + productId);
        }
    }
}
