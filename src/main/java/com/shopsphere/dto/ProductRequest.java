package com.shopsphere.dto;

import java.math.BigDecimal;

/**
 * Immutable DTO representing incoming product creation and update requests.
 */
public record ProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String sku
) {
}
