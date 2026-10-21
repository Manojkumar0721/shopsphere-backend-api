package com.shopsphere.service.impl;

import com.shopsphere.dto.ProductRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.entity.Product;
import com.shopsphere.exception.DuplicateResourceException;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.repository.ProductRepository;
import com.shopsphere.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation containing core business logic for Product domain.
 * Enforces transaction management (@Transactional) and constructor dependency injection.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Constructor injection: preferred over @Autowired field injection
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        // Business Rule: SKU must be globally unique
        if (productRepository.existsBySku(request.sku())) {
            throw new DuplicateResourceException("Product with SKU '" + request.sku() + "' already exists");
        }

        Product product = new Product(
                request.name(),
                request.description(),
                request.price(),
                request.stockQuantity(),
                request.sku()
        );

        Product savedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ProductResponse.fromEntity(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Business Rule: If updating SKU, ensure the new SKU does not conflict with another product
        if (!existingProduct.getSku().equals(request.sku()) && productRepository.existsBySku(request.sku())) {
            throw new DuplicateResourceException("Product with SKU '" + request.sku() + "' already exists");
        }

        existingProduct.setName(request.name());
        existingProduct.setDescription(request.description());
        existingProduct.setPrice(request.price());
        existingProduct.setStockQuantity(request.stockQuantity());
        existingProduct.setSku(request.sku());

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductResponse.fromEntity(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
