package com.pk.lab2.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSummaryDTO {
    private String id;
    private String name;
    private Integer quantity;
    private Float price;
    private Boolean available;
}
