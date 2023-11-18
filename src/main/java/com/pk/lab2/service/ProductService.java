package com.pk.lab2.service;

import com.pk.lab2.model.Product;
import com.pk.lab2.model.ProductDTO;
import com.pk.lab2.model.ProductHistory;
import com.pk.lab2.model.ProductSummaryDTO;
import com.pk.lab2.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // TODO Data Validation
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductSummaryDTO> getAllProducts() {
        return productRepository.findAllProductSummaries();
    }

    public Product getProductById(String productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        return productOptional.orElse(null);
    }

    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .quantity(productDTO.getQuantity())
                .price(productDTO.getPrice())
                .weight(productDTO.getWeight())
                .available(productDTO.getAvailable())
                .productCategory(productDTO.getProductCategory())
                .dateAdded(LocalDateTime.now())
                .dateLastUpdate(LocalDateTime.now())
                .productHistory(Collections.emptyList())
                .build();

        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(String productId, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(productId);
        // TODO Fix -> data duplication, only one time update works
        return productOptional.map(existingProduct -> {
            if (updateProductWithHistory(productDTO, existingProduct)) {
                existingProduct.setDateLastUpdate(LocalDateTime.now());
                return productRepository.save(existingProduct);
            }
            return null;
        }).orElse(null);
    }

    private boolean updateProductWithHistory(ProductDTO productDTO, Product existingProduct) {
        boolean updated = false;

        ProductHistory productHistory = new ProductHistory();

        if (productDTO.getName() != null) {
            existingProduct.setName(productDTO.getName());
            existingProduct.getProductHistory().add(productHistory.withHistoryEntry("name",
                    existingProduct.getName(), productDTO.getName()));
            updated = true;
        }
        if (productDTO.getDescription() != null) {
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.getProductHistory().add(productHistory.withHistoryEntry("description",
                    existingProduct.getDescription(), productDTO.getDescription()));
            updated = true;
        }
        if (productDTO.getQuantity() != null) {
            existingProduct.setQuantity(productDTO.getQuantity());
            existingProduct.getProductHistory().add(productHistory.withHistoryEntry("quantity",
                    existingProduct.getQuantity(), productDTO.getQuantity()));
            updated = true;
        }
        if (productDTO.getPrice() != null) {
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.getProductHistory().add(productHistory.withHistoryEntry("price",
                            existingProduct.getPrice(), productDTO.getPrice()));
            updated = true;
        }
        if (productDTO.getWeight() != null) {
            existingProduct.setWeight(productDTO.getWeight());
            existingProduct.getProductHistory().add(productHistory.withHistoryEntry("weight",
                    existingProduct.getWeight(), productDTO.getWeight()));
            updated = true;
        }
        if (productDTO.getAvailable() != null) {
            existingProduct.setAvailable(productDTO.getAvailable());
            existingProduct.getProductHistory().add(productHistory.withHistoryEntry("availability",
                    existingProduct.getAvailable(), productDTO.getAvailable()));
            updated = true;
        }
        if (productDTO.getProductCategory() != null) {
            existingProduct.setProductCategory(productDTO.getProductCategory());
            existingProduct.getProductHistory().add(productHistory.withHistoryEntry("productCategory",
                    existingProduct.getProductCategory(), productDTO.getProductCategory()));
            updated = true;
        }

        return updated;
    }

    @Transactional
    public boolean deleteProduct(String productId) {
        return productRepository.findById(productId)
                .map(product -> {
                    productRepository.deleteById(productId);
                    return true;
                })
                .orElse(false);
    }

}
