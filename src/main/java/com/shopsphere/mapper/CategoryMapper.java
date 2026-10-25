package com.shopsphere.mapper;

import com.shopsphere.dto.CategoryCreateRequest;
import com.shopsphere.dto.CategoryResponse;
import com.shopsphere.dto.CategoryUpdateRequest;
import com.shopsphere.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryCreateRequest request) {
        if (request == null) {
            return null;
        }
        return new Category(request.name(), request.description());
    }

    public void updateEntityFromRequest(Category entity, CategoryUpdateRequest request) {
        if (entity == null || request == null) {
            return;
        }
        entity.setName(request.name());
        entity.setDescription(request.description());
        if (request.active() != null) {
            entity.setActive(request.active());
        }
    }

    public CategoryResponse toResponse(Category entity) {
        if (entity == null) {
            return null;
        }
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
