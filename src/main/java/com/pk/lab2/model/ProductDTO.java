package com.pk.lab2.model;

import com.pk.lab2.enums.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Product Data Transfer Object")
public class ProductDTO {

    @Schema(description = "Product name", example = "This is item name", minLength = 1)
    private String name;

    @Schema(description = "Product description", example = "This is description of item", minLength = 1)
    private String description;

    @Schema(description = "Product quantity", example = "100", minimum = "1", maximum = "Infinity")
    private Integer quantity;

    @Schema(description = "Product price", example = "34.62", minimum = "0.01", maximum = "Infinity")
    private Float price;

    @Schema(description = "Product weight", example = "534.21", minimum = "0.01", maximum = "Infinity")
    private Float weight;

    @Schema(description = "Product category", example = "ELECTRONICS")
    private ProductCategory productCategory;

}
