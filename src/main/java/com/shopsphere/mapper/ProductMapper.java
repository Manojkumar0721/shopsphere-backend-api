package com.shopsphere.mapper;

import com.shopsphere.dto.ProductCreateRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.dto.ProductUpdateRequest;
import com.shopsphere.entity.Product;
import org.springframework.stereotype.Component;

/**
 * Dedicated mapper component for converting between Product entities and DTOs.
 * Keeps mapping logic centralized, testable, and separate from business rules.
 */
@Component
public class ProductMapper {

    /**
     * Converts a ProductCreateRequest into a new unpersisted Product entity.
     */
    public Product toEntity(ProductCreateRequest request) {
        if (request == null) {
            return null;
        }
        return new Product(
                request.name(),
                request.description(),
                request.price(),
                request.stockQuantity(),
                request.sku()
        );
    }

    /**
     * Updates an existing managed Product entity with fields from a ProductUpdateRequest.
     */
    public void updateEntityFromRequest(Product entity, ProductUpdateRequest request) {
        if (entity == null || request == null) {
            return;
        }
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        entity.setStockQuantity(request.stockQuantity());
        entity.setSku(request.sku());
        if (request.active() != null) {
            entity.setActive(request.active());
        }
    }

    /**
     * Converts a persisted Product entity into an immutable ProductResponse DTO.
     */
    public ProductResponse toResponse(Product entity) {
        if (entity == null) {
            return null;
        }
        return new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStockQuantity(),
                entity.getSku(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
