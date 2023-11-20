package com.pk.lab2.service;

import com.pk.lab2.model.Product;
import com.pk.lab2.model.ProductDTO;
import com.pk.lab2.model.ProductSummaryDTO;
import com.pk.lab2.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
                .productHistory(new ArrayList<>())
                .build();

        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(String productId, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(productId);
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

        Product.ProductHistory productHistory = new Product.ProductHistory();

        updated |= updateField("name", productDTO::getName, existingProduct::getName,
                existingProduct::setName, productHistory);
        updated |= updateField("description", productDTO::getDescription, existingProduct::getDescription,
                existingProduct::setDescription, productHistory);
        updated |= updateField("quantity", productDTO::getQuantity, existingProduct::getQuantity,
                existingProduct::setQuantity, productHistory);
        updated |= updateField("price", productDTO::getPrice, existingProduct::getPrice,
                existingProduct::setPrice, productHistory);
        updated |= updateField("weight", productDTO::getWeight, existingProduct::getWeight,
                existingProduct::setWeight, productHistory);
        updated |= updateField("availability", productDTO::getAvailable, existingProduct::getAvailable,
                existingProduct::setAvailable, productHistory);
        updated |= updateField("productCategory", productDTO::getProductCategory,
                existingProduct::getProductCategory, existingProduct::setProductCategory, productHistory);

        if (!productHistory.getChangedFieldsMap().isEmpty()) {
            existingProduct.getProductHistory().add(productHistory);
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

    private <T> boolean updateField(String fieldName, Supplier<T> supplierNewValue, Supplier<T> supplierCurrentValue,
                                    Consumer<T> setter, Product.ProductHistory productHistory) {
        T newValue = supplierNewValue.get();

        if (newValue != null) {
            T currentValue = supplierCurrentValue.get();
            if (!Objects.equals(currentValue, newValue)) {
                setter.accept(newValue);
                productHistory.addHistoryEntry(fieldName, currentValue, newValue);
                return true;
            }
        }

        return false;
    }
}
