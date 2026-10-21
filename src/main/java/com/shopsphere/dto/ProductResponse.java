package com.shopsphere.dto;

import com.shopsphere.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Immutable DTO representing product data returned to clients.
 * Protects database schema from direct public exposure.
 */
public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String sku,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    /**
     * Factory mapping method: converts a JPA entity to an immutable ProductResponse DTO.
     */
    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getSku(),
                product.getActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
