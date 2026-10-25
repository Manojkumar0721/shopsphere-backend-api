package com.shopsphere.repository;

import com.shopsphere.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ProductRepository interface provides full CRUD operations and query derivation.
 *
 * JpaRepository provides out of the box:
 * - save(entity)
 * - findById(id)
 * - findAll()
 * - deleteById(id)
 * - pagination and sorting methods
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Derived query method: Spring Data JPA automatically inspects the method name
     * and generates: "SELECT p FROM Product p WHERE p.sku = :sku"
     */
    Optional<Product> findBySku(String sku);

    /**
     * Checks if a product with the given SKU already exists.
     * Generates: "SELECT COUNT(p) > 0 FROM Product p WHERE p.sku = :sku"
     */
    boolean existsBySku(String sku);

    /**
     * Find all products belonging to a given category ID.
     */
    java.util.List<Product> findByCategoryId(Long categoryId);
}
