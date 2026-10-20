package com.shopsphere.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Product Entity representing the 'products' table in the database.
 *
 * Annotations breakdown:
 * - @Entity: Marks this class as a JPA entity managed by Hibernate.
 * - @Table: Specifies the database table name ('products') and unique constraints/indexes.
 */
@Entity
@Table(name = "products")
public class Product {

    /**
     * Primary key with AUTO_INCREMENT strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Product name cannot be null.
     */
    @Column(nullable = false, length = 150)
    private String name;

    /**
     * Detailed product description.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Product price stored as BigDecimal to avoid floating-point rounding errors in monetary values.
     * precision = 10 (total digits), scale = 2 (digits after decimal point, e.g. 99999999.99).
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Available stock quantity.
     */
    @Column(nullable = false)
    private Integer stockQuantity;

    /**
     * Unique Stock Keeping Unit (SKU) identifier for barcode / inventory tracking.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    /**
     * Product active status flag.
     */
    @Column(nullable = false)
    private Boolean active = true;

    /**
     * Audit timestamps automatically populated before persist and update.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Default no-argument constructor required by JPA/Hibernate spec
    public Product() {
    }

    // Parameterized constructor for easy instantiation
    public Product(String name, String description, BigDecimal price, Integer stockQuantity, String sku) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.sku = sku;
        this.active = true;
    }

    // JPA Entity Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
