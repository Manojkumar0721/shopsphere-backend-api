package com.shopsphere.mapper;

import com.shopsphere.dto.ProductCreateRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.dto.ProductUpdateRequest;
import com.shopsphere.entity.Category;
import com.shopsphere.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateRequest request, Category category) {
        if (request == null) {
            return null;
        }
        Product product = new Product(
                request.name(),
                request.description(),
                request.price(),
                request.stockQuantity(),
                request.sku()
        );
        product.setCategory(category);
        return product;
    }

    public void updateEntityFromRequest(Product entity, ProductUpdateRequest request, Category category) {
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
        entity.setCategory(category);
    }

    public ProductResponse toResponse(Product entity) {
        if (entity == null) {
            return null;
        }
        Long categoryId = (entity.getCategory() != null) ? entity.getCategory().getId() : null;
        String categoryName = (entity.getCategory() != null) ? entity.getCategory().getName() : null;

        return new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStockQuantity(),
                entity.getSku(),
                entity.getActive(),
                categoryId,
                categoryName,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
