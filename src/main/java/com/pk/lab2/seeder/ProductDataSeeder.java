package com.pk.lab2.seeder;

import com.pk.lab2.enums.ProductCategory;
import com.pk.lab2.model.Product;
import com.pk.lab2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductDataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        productRepository.deleteAll();
        Product product1 = Product.builder()
                .name("Samsung S20")
                .description("It's a smartphone")
                .quantity(10)
                .price(20.0F)
                .weight(2.5F)
                .available(true)
                .productCategory(ProductCategory.ELECTRONICS)
                .dateAdded(LocalDateTime.now())
                .dateLastUpdate(LocalDateTime.now())
                .productHistory(new ArrayList<>())
                .build();

        Product product2 = Product.builder()
                .name("Toys321")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                        "eiusmod tempor incididunt ut labore et dolore magna aliqua.")
                .quantity(5)
                .price(30.0F)
                .weight(1.8F)
                .available(true)
                .productCategory(ProductCategory.TOYS)
                .dateAdded(LocalDateTime.now())
                .dateLastUpdate(LocalDateTime.now())
                .productHistory(new ArrayList<>())
                .build();

        Product product3 = Product.builder()
                .name("Drilling Machine")
                .description("Drilling Machine 231321312321")
                .quantity(0)
                .price(25.0F)
                .weight(3.0F)
                .available(false)
                .productCategory(ProductCategory.INDUSTRIAL)
                .dateAdded(LocalDateTime.now())
                .dateLastUpdate(LocalDateTime.now())
                .productHistory(new ArrayList<>())
                .build();

        Product product4 = Product.builder()
                .name("Grill")
                .description("GrillGrillGrill")
                .quantity(8)
                .price(35.0F)
                .weight(2.2F)
                .available(true)
                .productCategory(ProductCategory.GARDEN)
                .dateAdded(LocalDateTime.now())
                .dateLastUpdate(LocalDateTime.now())
                .productHistory(new ArrayList<>())
                .build();

        Product product5 = Product.builder()
                .name("Polish Coins 1920")
                .description("Polish Coins 1920Polish Coins 1920Polish Coins 1920")
                .quantity(12)
                .price(28.0F)
                .weight(2.8F)
                .available(true)
                .productCategory(ProductCategory.COLLECTIBLES)
                .dateAdded(LocalDateTime.now())
                .dateLastUpdate(LocalDateTime.now())
                .productHistory(new ArrayList<>())
                .build();

        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5);
        productRepository.saveAll(products);

    }
}
