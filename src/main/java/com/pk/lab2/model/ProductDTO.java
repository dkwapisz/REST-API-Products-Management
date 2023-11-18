package com.pk.lab2.model;

import com.pk.lab2.enums.ProductCategory;
import lombok.Data;

@Data
public class ProductDTO {

    private String name;
    private String description;
    private Integer quantity;
    private Float price;
    private Float weight;
    private Boolean available;
    private ProductCategory productCategory;

}
