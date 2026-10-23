package com.shopsphere.service;

import com.shopsphere.dto.ProductCreateRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.dto.ProductUpdateRequest;

import java.util.List;

/**
 * Service interface declaring business operations for Product management.
 */
public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductUpdateRequest request);

    void deleteProduct(Long id);
}
