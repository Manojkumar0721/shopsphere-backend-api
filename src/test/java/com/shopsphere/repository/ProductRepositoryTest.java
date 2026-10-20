package com.shopsphere.repository;

import com.shopsphere.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DataJpaTest slices the Spring context to test JPA repositories in complete isolation.
 */
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should save a product and retrieve it by ID")
    void shouldSaveAndRetrieveProduct() {
        // Arrange
        Product product = new Product(
                "Wireless Noise-Canceling Headphones",
                "Premium over-ear Bluetooth headphones with active noise cancellation",
                new BigDecimal("199.99"),
                50,
                "PROD-HEADPHONE-001"
        );

        // Act
        Product savedProduct = productRepository.save(product);

        // Assert
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getCreatedAt()).isNotNull();
        assertThat(savedProduct.getUpdatedAt()).isNotNull();

        Optional<Product> retrieved = productRepository.findById(savedProduct.getId());
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getName()).isEqualTo("Wireless Noise-Canceling Headphones");
        assertThat(retrieved.get().getPrice()).isEqualByComparingTo("199.99");
        assertThat(retrieved.get().getSku()).isEqualTo("PROD-HEADPHONE-001");
    }

    @Test
    @DisplayName("Should find product by SKU using derived query method")
    void shouldFindProductBySku() {
        // Arrange
        Product product = new Product(
                "Mechanical Keyboard",
                "RGB tactile mechanical gaming keyboard",
                new BigDecimal("89.50"),
                30,
                "PROD-KEYBOARD-RGB"
        );
        productRepository.save(product);

        // Act
        Optional<Product> found = productRepository.findBySku("PROD-KEYBOARD-RGB");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Mechanical Keyboard");
    }

    @Test
    @DisplayName("Should correctly verify SKU existence")
    void shouldCheckIfSkuExists() {
        // Arrange
        Product product = new Product(
                "USB-C Hub",
                "7-in-1 multi-port adapter",
                new BigDecimal("35.00"),
                100,
                "PROD-USBC-HUB"
        );
        productRepository.save(product);

        // Act & Assert
        assertThat(productRepository.existsBySku("PROD-USBC-HUB")).isTrue();
        assertThat(productRepository.existsBySku("NON-EXISTENT-SKU")).isFalse();
    }
}
