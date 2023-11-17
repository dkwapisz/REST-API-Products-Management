package com.pk.lab2.model;

import java.time.LocalDateTime;
import java.util.Map;

public record ProductHistory(LocalDateTime changeTimestamp, Map<String, ChangedFields> changedFields) {
    record ChangedFields(Object fieldBeforeUpdate, Object fieldAfterUpdate) {}
}
