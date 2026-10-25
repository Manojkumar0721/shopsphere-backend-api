package com.shopsphere.mapper;

import com.shopsphere.dto.ProductCreateRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.dto.ProductUpdateRequest;
import com.shopsphere.entity.Category;
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
    @DisplayName("Should correctly map ProductCreateRequest to Product entity with Category")
    void shouldMapCreateRequestToEntity() {
        Category category = new Category("Peripherals", "PC accessories");
        category.setId(10L);

        ProductCreateRequest request = new ProductCreateRequest(
                "Mechanical Keyboard",
                "RGB tactile switches",
                new BigDecimal("79.99"),
                45,
                "PROD-KEYBOARD-RGB",
                10L
        );

        Product entity = productMapper.toEntity(request, category);

        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo("Mechanical Keyboard");
        assertThat(entity.getDescription()).isEqualTo("RGB tactile switches");
        assertThat(entity.getPrice()).isEqualByComparingTo("79.99");
        assertThat(entity.getStockQuantity()).isEqualTo(45);
        assertThat(entity.getSku()).isEqualTo("PROD-KEYBOARD-RGB");
        assertThat(entity.getActive()).isTrue();
        assertThat(entity.getCategory()).isEqualTo(category);
    }

    @Test
    @DisplayName("Should correctly update existing Product entity from ProductUpdateRequest")
    void shouldUpdateEntityFromRequest() {
        Category category = new Category("Keyboards", "Typing accessories");
        category.setId(20L);

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
                false,
                20L
        );

        productMapper.updateEntityFromRequest(existing, updateRequest, category);

        assertThat(existing.getName()).isEqualTo("Updated Name");
        assertThat(existing.getDescription()).isEqualTo("Updated Desc");
        assertThat(existing.getPrice()).isEqualByComparingTo("15.50");
        assertThat(existing.getStockQuantity()).isEqualTo(20);
        assertThat(existing.getSku()).isEqualTo("PROD-UPDATED");
        assertThat(existing.getActive()).isFalse();
        assertThat(existing.getCategory()).isEqualTo(category);
    }

    @Test
    @DisplayName("Should correctly map Product entity with Category to ProductResponse DTO")
    void shouldMapEntityToResponse() {
        Category category = new Category("Cables", "Wiring accessories");
        category.setId(5L);

        Product entity = new Product(
                "USB-C Cable",
                "Braided fast-charging cable",
                new BigDecimal("9.99"),
                100,
                "PROD-CABLE-01"
        );
        entity.setId(100L);
        entity.setCategory(category);

        ProductResponse response = productMapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(100L);
        assertThat(response.name()).isEqualTo("USB-C Cable");
        assertThat(response.sku()).isEqualTo("PROD-CABLE-01");
        assertThat(response.price()).isEqualByComparingTo("9.99");
        assertThat(response.stockQuantity()).isEqualTo(100);
        assertThat(response.active()).isTrue();
        assertThat(response.categoryId()).isEqualTo(5L);
        assertThat(response.categoryName()).isEqualTo("Cables");
    }
}
