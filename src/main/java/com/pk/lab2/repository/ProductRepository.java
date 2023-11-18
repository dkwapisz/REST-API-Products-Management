package com.pk.lab2.repository;

import com.pk.lab2.model.Product;
import com.pk.lab2.model.ProductSummaryDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query(value = "{}", fields = "{ 'id': 1, 'name' : 1, 'quantity' : 1, 'price' : 1, 'available' : 1}")
    List<ProductSummaryDTO> findAllProductSummaries();
}
