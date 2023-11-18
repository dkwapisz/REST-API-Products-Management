package com.pk.lab2.model;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class ProductHistory {

    private final LocalDateTime updateTimestamp;
    private final Map<String, Pair<Object, Object>> changedFieldsMap;

    public ProductHistory() {
        this.changedFieldsMap = new HashMap<>();
        this.updateTimestamp = LocalDateTime.now();
    }

    public ProductHistory withHistoryEntry(String objectType, Object objectBeforeUpdate, Object objectAfterUpdate) {
        if (!objectBeforeUpdate.getClass().equals(objectAfterUpdate.getClass())) {
            throw new RuntimeException("Invalid history entry types");
        }

        this.changedFieldsMap.put(objectType, Pair.of(objectBeforeUpdate, objectAfterUpdate));

        return this;
    }


}
