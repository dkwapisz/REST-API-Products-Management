package com.pk.lab2.model;

import com.pk.lab2.enums.ProductCategory;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@Document("product")
public class Product {

    private String id;
    private String name;
    private String description;
    private Integer quantity;
    private Float price;
    private Float weight;
    private Boolean available;
    private ProductCategory productCategory;
    private LocalDateTime dateAdded;
    private LocalDateTime dateLastUpdate;
    private List<ProductHistory> productHistory;

}
