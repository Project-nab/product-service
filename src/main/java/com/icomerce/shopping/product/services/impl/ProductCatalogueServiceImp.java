package com.icomerce.shopping.product.services.impl;

import com.icomerce.shopping.product.entities.ProductCatalogue;
import com.icomerce.shopping.product.repositories.ProductCatalogueRepo;
import com.icomerce.shopping.product.services.ProductCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductCatalogueServiceImp implements ProductCatalogueService {
    private final ProductCatalogueRepo productCatalogueRepo;

    @Autowired
    public ProductCatalogueServiceImp(ProductCatalogueRepo productCatalogueRepo) {
        this.productCatalogueRepo = productCatalogueRepo;
    }

    @Override
    public ProductCatalogue create(ProductCatalogue productCatalogue) {
        productCatalogue.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productCatalogue.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return productCatalogueRepo.save(productCatalogue);
    }

    @Override
    @Cacheable(value = "findAllProductCatalogue")
    public List<ProductCatalogue> findAll() {
        return (List<ProductCatalogue>) productCatalogueRepo.findAll();
    }
}
