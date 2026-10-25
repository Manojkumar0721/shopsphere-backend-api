package com.shopsphere.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.dto.CategoryCreateRequest;
import com.shopsphere.dto.CategoryResponse;
import com.shopsphere.dto.CategoryUpdateRequest;
import com.shopsphere.exception.GlobalExceptionHandler;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(GlobalExceptionHandler.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    @DisplayName("POST /api/v1/categories - Should create category and return 201 Created")
    void shouldCreateCategory() throws Exception {
        CategoryCreateRequest request = new CategoryCreateRequest("Electronics", "Gadgets and tech");
        CategoryResponse response = new CategoryResponse(1L, "Electronics", "Gadgets and tech", true, LocalDateTime.now(), LocalDateTime.now());

        when(categoryService.createCategory(any(CategoryCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test
    @DisplayName("POST /api/v1/categories - Should return 400 Bad Request when name is blank")
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        CategoryCreateRequest invalidRequest = new CategoryCreateRequest("", "Some description");

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.name").exists());
    }

    @Test
    @DisplayName("GET /api/v1/categories - Should return all categories")
    void shouldGetAllCategories() throws Exception {
        CategoryResponse response = new CategoryResponse(1L, "Electronics", "Gadgets", true, LocalDateTime.now(), LocalDateTime.now());
        when(categoryService.getAllCategories()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Electronics"));
    }

    @Test
    @DisplayName("GET /api/v1/categories/{id} - Should return category by ID")
    void shouldGetCategoryById() throws Exception {
        CategoryResponse response = new CategoryResponse(1L, "Electronics", "Gadgets", true, LocalDateTime.now(), LocalDateTime.now());
        when(categoryService.getCategoryById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test
    @DisplayName("GET /api/v1/categories/{id} - Should return 404 when not found")
    void shouldReturn404WhenNotFound() throws Exception {
        when(categoryService.getCategoryById(999L)).thenThrow(new ResourceNotFoundException("Category not found with id: 999"));

        mockMvc.perform(get("/api/v1/categories/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("PUT /api/v1/categories/{id} - Should update category")
    void shouldUpdateCategory() throws Exception {
        CategoryUpdateRequest updateRequest = new CategoryUpdateRequest("Computers", "Hardware", true);
        CategoryResponse response = new CategoryResponse(1L, "Computers", "Hardware", true, LocalDateTime.now(), LocalDateTime.now());

        when(categoryService.updateCategory(eq(1L), any(CategoryUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Computers"));
    }

    @Test
    @DisplayName("DELETE /api/v1/categories/{id} - Should delete category")
    void shouldDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/api/v1/categories/1"))
                .andExpect(status().isNoContent());
    }
}
