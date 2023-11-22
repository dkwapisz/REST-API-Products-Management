package com.pk.lab2.model;

import com.pk.lab2.enums.ProductCategory;
import com.pk.lab2.utils.UpdatePair;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Data
    public static class ProductHistory {

        private LocalDateTime updateTimestamp;
        private Map<String, UpdatePair<Object>> changedFieldsMap;

        public ProductHistory() {
            this.changedFieldsMap = new HashMap<>();
            this.updateTimestamp = LocalDateTime.now();
        }

        public void addHistoryEntry(String objectType, Object objectBeforeUpdate, Object objectAfterUpdate) {
            if (!objectBeforeUpdate.getClass().equals(objectAfterUpdate.getClass())) {
                throw new RuntimeException("Invalid history entry types");
            }

            this.changedFieldsMap.put(objectType, UpdatePair.of(objectBeforeUpdate, objectAfterUpdate));
        }
    }
}
