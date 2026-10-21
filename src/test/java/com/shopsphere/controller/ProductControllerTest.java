package com.shopsphere.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.dto.ProductRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
 * Controller integration tests using @WebMvcTest.
 * Slices the Spring context to test ProductController endpoints, HTTP status codes, and JSON serialization.
 */
@WebMvcTest(ProductController.class)
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
        ProductRequest request = new ProductRequest(
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

        when(productService.createProduct(any(ProductRequest.class))).thenReturn(response);

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
    @DisplayName("PUT /api/v1/products/{id} - Should update product and return HTTP 200 OK")
    void shouldUpdateProduct() throws Exception {
        ProductRequest updateRequest = new ProductRequest(
                "Updated Monitor",
                "Updated description",
                new BigDecimal("269.99"),
                20,
                "PROD-MONITOR-27"
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

        when(productService.updateProduct(eq(1L), any(ProductRequest.class))).thenReturn(updatedResponse);

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
