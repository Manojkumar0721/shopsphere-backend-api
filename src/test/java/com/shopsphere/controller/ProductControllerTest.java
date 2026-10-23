package com.shopsphere.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.dto.ProductCreateRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.dto.ProductUpdateRequest;
import com.shopsphere.exception.DuplicateResourceException;
import com.shopsphere.exception.GlobalExceptionHandler;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller integration tests including request validation and centralized exception handling.
 */
@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    @DisplayName("POST /api/v1/products - Should create product and return HTTP 201 Created")
    void shouldCreateProduct() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest(
                "Gaming Monitor",
                "27-inch 144Hz 1ms IPS gaming display",
                new BigDecimal("249.99"),
                25,
                "PROD-MONITOR-27"
        );

        ProductResponse response = new ProductResponse(
                1L,
                "Gaming Monitor",
                "27-inch 144Hz 1ms IPS gaming display",
                new BigDecimal("249.99"),
                25,
                "PROD-MONITOR-27",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(productService.createProduct(any(ProductCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Gaming Monitor"))
                .andExpect(jsonPath("$.sku").value("PROD-MONITOR-27"))
                .andExpect(jsonPath("$.price").value(249.99));
    }

    @Test
    @DisplayName("POST /api/v1/products - Should return HTTP 400 Bad Request when validation fails")
    void shouldReturnBadRequestWhenValidationFails() throws Exception {
        // Invalid request: blank name, negative price, negative stock, blank sku
        ProductCreateRequest invalidRequest = new ProductCreateRequest(
                "",
                "Description",
                new BigDecimal("-10.00"),
                -5,
                ""
        );

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors.name").exists())
                .andExpect(jsonPath("$.validationErrors.price").exists())
                .andExpect(jsonPath("$.validationErrors.stockQuantity").exists())
                .andExpect(jsonPath("$.validationErrors.sku").exists());
    }

    @Test
    @DisplayName("POST /api/v1/products - Should return HTTP 409 Conflict when SKU already exists")
    void shouldReturnConflictWhenSkuAlreadyExists() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest(
                "Gaming Monitor",
                "Description",
                new BigDecimal("249.99"),
                10,
                "PROD-DUPLICATE-SKU"
        );

        when(productService.createProduct(any(ProductCreateRequest.class)))
                .thenThrow(new DuplicateResourceException("Product with SKU 'PROD-DUPLICATE-SKU' already exists"));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Product with SKU 'PROD-DUPLICATE-SKU' already exists"));
    }

    @Test
    @DisplayName("GET /api/v1/products - Should return all products with HTTP 200 OK")
    void shouldGetAllProducts() throws Exception {
        ProductResponse response = new ProductResponse(
                1L,
                "Gaming Monitor",
                "27-inch 144Hz 1ms IPS gaming display",
                new BigDecimal("249.99"),
                25,
                "PROD-MONITOR-27",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(productService.getAllProducts()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Gaming Monitor"));
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - Should return product by ID with HTTP 200 OK")
    void shouldGetProductById() throws Exception {
        ProductResponse response = new ProductResponse(
                1L,
                "Gaming Monitor",
                "27-inch 144Hz 1ms IPS gaming display",
                new BigDecimal("249.99"),
                25,
                "PROD-MONITOR-27",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(productService.getProductById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Gaming Monitor"));
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - Should return HTTP 404 Not Found when product does not exist")
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        when(productService.getProductById(999L))
                .thenThrow(new ResourceNotFoundException("Product not found with id: 999"));

        mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Product not found with id: 999"));
    }

    @Test
    @DisplayName("PUT /api/v1/products/{id} - Should update product and return HTTP 200 OK")
    void shouldUpdateProduct() throws Exception {
        ProductUpdateRequest updateRequest = new ProductUpdateRequest(
                "Updated Monitor",
                "Updated description",
                new BigDecimal("269.99"),
                20,
                "PROD-MONITOR-27",
                true
        );

        ProductResponse updatedResponse = new ProductResponse(
                1L,
                "Updated Monitor",
                "Updated description",
                new BigDecimal("269.99"),
                20,
                "PROD-MONITOR-27",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(productService.updateProduct(eq(1L), any(ProductUpdateRequest.class))).thenReturn(updatedResponse);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Monitor"))
                .andExpect(jsonPath("$.price").value(269.99));
    }

    @Test
    @DisplayName("DELETE /api/v1/products/{id} - Should delete product and return HTTP 204 No Content")
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());
    }
}
