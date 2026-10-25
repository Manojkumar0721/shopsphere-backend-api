package com.shopsphere.service;

import com.shopsphere.dto.CategoryCreateRequest;
import com.shopsphere.dto.CategoryResponse;
import com.shopsphere.dto.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryCreateRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryUpdateRequest request);

    void deleteCategory(Long id);
}
