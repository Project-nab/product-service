package com.icomerce.shopping.product.services;

import com.icomerce.shopping.product.entities.Product;
import com.icomerce.shopping.product.exception.NegativeProductQuantityException;
import com.icomerce.shopping.product.exception.ProductNotFoundException;
import com.icomerce.shopping.product.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product create(ProductRequest productRequest);
    Page<Product> findProduct(String category, String brand, String color, Double priceMin, Double priceMax, int offset, int limit);
    Product findByCode(String productId);
    Product updateProductQuantity(String productCode, int quantity) throws ProductNotFoundException, NegativeProductQuantityException;
}
