package com.icomerce.shopping.product.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icomerce.shopping.product.entities.Brand;
import com.icomerce.shopping.product.entities.Product;
import com.icomerce.shopping.product.entities.ProductCatalogue;
import com.icomerce.shopping.product.exception.NegativeProductQuantityException;
import com.icomerce.shopping.product.exception.ProductNotFoundException;
import com.icomerce.shopping.product.payload.query.ProductQuery;
import com.icomerce.shopping.product.payload.request.ProductRequest;
import com.icomerce.shopping.product.repositories.BrandRepo;
import com.icomerce.shopping.product.repositories.ProductCatalogueRepo;
import com.icomerce.shopping.product.repositories.ProductRepo;
import com.icomerce.shopping.product.repositories.specification.ProductSpecification;
import com.icomerce.shopping.product.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImp implements ProductService {

    private final ProductRepo productRepo;
    private final BrandRepo brandRepo;
    private final ProductCatalogueRepo productCatalogueRepo;
    private final ProductSpecification productSpecification;

    @Autowired
    public ProductServiceImp(ProductRepo productRepo,
                             BrandRepo brandRepo,
                             ProductCatalogueRepo productCatalogueRepo,
                             ProductSpecification productSpecification) {
        this.productRepo = productRepo;
        this.brandRepo = brandRepo;
        this.productSpecification = productSpecification;
        this.productCatalogueRepo = productCatalogueRepo;
    }

    @Override
    public Product create(ProductRequest productRequest) {
        String brandId = productRequest.getBrandCode();
        Brand brandOptional = brandRepo.findById(brandId).orElse(null);
        ProductCatalogue productCatalogueOptional = productCatalogueRepo.findById(productRequest.getCatalogueCode()).orElse(null);

        if(brandOptional == null || productCatalogueOptional == null) {
            return null;
        }

        Product product = new Product();
        product.setCode(productRequest.getProductCode());
        product.setProductName(productRequest.getProductName());
        product.setBrand(brandOptional);
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setPrice(productRequest.getPrice());
        product.setColor(productRequest.getColor());
        product.setProductCatalogue(productCatalogueOptional);
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setThumnail(productRequest.getThumnail());
        product.setQuantity(productRequest.getQuantity());
        return productRepo.save(product);
    }

    @Override
    @CachePut(value = "findProduct")
    public Page<Product> findProduct(String category, String brand, String color, Double priceMin, Double priceMax, int offset, int limit) {
        ProductQuery productQuery = new ProductQuery(category, brand, color, priceMin, priceMax);
        Page<Product> pages = productRepo.findAll(productSpecification.build(productQuery), PageRequest.of(offset, limit));
        return pages;
    }

    @Override
    @Cacheable(value = "findProductDetail")
    public Product findByCode(String productId) {
        log.info("Find product by Id {}", productId);
        Optional<Product> productOptional = productRepo.findById(productId);
        return productOptional.orElse(null);
    }

    @Override
    @Transactional
    @CacheEvict(value = "findProductDetail", allEntries = true)
    public Product updateProductQuantity(String productCode, int quantity) throws ProductNotFoundException,
            NegativeProductQuantityException {
        if(quantity < 0) {
            throw new NegativeProductQuantityException("Update negative quantity exception " + productCode);
        }
        Optional<Product> product = productRepo.findByCode(productCode);
        if(product.isPresent()) {
            int newQuantity = product.get().getQuantity() - quantity;
            if(newQuantity < 0) {
                throw new NegativeProductQuantityException("Product code have negative quantity " + productCode);
            }
            product.get().setQuantity(newQuantity);
            return productRepo.save(product.get());
        }
        throw new ProductNotFoundException("Product code not found " + productCode);
    }
}
