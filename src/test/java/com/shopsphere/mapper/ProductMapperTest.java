package com.shopsphere.mapper;

import com.shopsphere.dto.ProductCreateRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.dto.ProductUpdateRequest;
import com.shopsphere.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    @DisplayName("Should correctly map ProductCreateRequest to Product entity")
    void shouldMapCreateRequestToEntity() {
        ProductCreateRequest request = new ProductCreateRequest(
                "Mechanical Keyboard",
                "RGB tactile switches",
                new BigDecimal("79.99"),
                45,
                "PROD-KEYBOARD-RGB"
        );

        Product entity = productMapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo("Mechanical Keyboard");
        assertThat(entity.getDescription()).isEqualTo("RGB tactile switches");
        assertThat(entity.getPrice()).isEqualByComparingTo("79.99");
        assertThat(entity.getStockQuantity()).isEqualTo(45);
        assertThat(entity.getSku()).isEqualTo("PROD-KEYBOARD-RGB");
        assertThat(entity.getActive()).isTrue();
    }

    @Test
    @DisplayName("Should correctly update existing Product entity from ProductUpdateRequest")
    void shouldUpdateEntityFromRequest() {
        Product existing = new Product(
                "Original Name",
                "Original Desc",
                new BigDecimal("10.00"),
                10,
                "PROD-ORIGINAL"
        );

        ProductUpdateRequest updateRequest = new ProductUpdateRequest(
                "Updated Name",
                "Updated Desc",
                new BigDecimal("15.50"),
                20,
                "PROD-UPDATED",
                false
        );

        productMapper.updateEntityFromRequest(existing, updateRequest);

        assertThat(existing.getName()).isEqualTo("Updated Name");
        assertThat(existing.getDescription()).isEqualTo("Updated Desc");
        assertThat(existing.getPrice()).isEqualByComparingTo("15.50");
        assertThat(existing.getStockQuantity()).isEqualTo(20);
        assertThat(existing.getSku()).isEqualTo("PROD-UPDATED");
        assertThat(existing.getActive()).isFalse();
    }

    @Test
    @DisplayName("Should correctly map Product entity to ProductResponse DTO")
    void shouldMapEntityToResponse() {
        Product entity = new Product(
                "USB-C Cable",
                "Braided fast-charging cable",
                new BigDecimal("9.99"),
                100,
                "PROD-CABLE-01"
        );
        entity.setId(5L);

        ProductResponse response = productMapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(5L);
        assertThat(response.name()).isEqualTo("USB-C Cable");
        assertThat(response.sku()).isEqualTo("PROD-CABLE-01");
        assertThat(response.price()).isEqualByComparingTo("9.99");
        assertThat(response.stockQuantity()).isEqualTo(100);
        assertThat(response.active()).isTrue();
    }
}
