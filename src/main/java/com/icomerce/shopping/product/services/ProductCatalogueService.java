package com.icomerce.shopping.product.services;

import com.icomerce.shopping.product.entities.ProductCatalogue;

import java.util.List;

public interface ProductCatalogueService {
    ProductCatalogue create(ProductCatalogue productCatalogue);
    List<ProductCatalogue> findAll();
}
