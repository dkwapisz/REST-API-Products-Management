package com.pk.lab2.model;

import com.pk.lab2.enums.ProductCategory;
import lombok.Data;

@Data
public class ProductDTO {

    private String name;
    private String description;
    private int quantity;
    private float price;
    private float weight;
    private boolean available;
    private ProductCategory productCategory;

}
