package com.icomerce.shopping.product.services.impl;

import com.icomerce.shopping.product.entities.*;
import com.icomerce.shopping.product.exception.NegativeProductQuantityException;
import com.icomerce.shopping.product.exception.ProductNotFoundException;
import com.icomerce.shopping.product.payload.request.ProductRequest;
import com.icomerce.shopping.product.repositories.BrandRepo;
import com.icomerce.shopping.product.repositories.ProductCatalogueRepo;
import com.icomerce.shopping.product.repositories.ProductRepo;
import com.icomerce.shopping.product.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ProductServiceTest {
    @Autowired
    ProductService productService;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    ProductCatalogueRepo productCatalogueRepo;
    @Autowired
    ProductRepo productRepo;

    @Before
    public void setup() {
        // Brand name ADIDAS
        Brand brand = new Brand("ADIDAS", "ADIDAS", "GERMANY", null, null, null);
        Brand createdBrand = brandRepo.save(brand);
        // Product catalogue FASHION
        ProductCatalogue productCatalogue = new ProductCatalogue("ADIDAS_01", "FASHION", CatalogueType.FASHION,
                null, null, null);
        ProductCatalogue createdCatalogue = productCatalogueRepo.save(productCatalogue);

        // T-shirt, color BLUE, price 80USD, brand ADIDAS, category FASHION
        Product product = new Product("ADIDAS_TSHIRT_01", "T-shirt", "", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                createdBrand, createdCatalogue, Color.BLUE, 80D, 100);
        productRepo.save(product);
        // T-shirt, color YELLOW, price 50USD, brand ADIDAS, category FASHION
        product = new Product("ADIDAS_TSHIRT_02", "T-shirt", "", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                createdBrand, createdCatalogue, Color.YELLOW, 50D, 100);
        productRepo.save(product);

        // Brand name APPLE
        brand = new Brand("APPLE", "APPLE", "GERMANY", null, null, null);
        createdBrand = brandRepo.save(brand);

        // Product catalogue TECHNOLOGY
        productCatalogue = new ProductCatalogue("APPLE_01", "TECHNOLOGY", CatalogueType.TECHNOLOGY,
                null, null, null);
        createdCatalogue = productCatalogueRepo.save(productCatalogue);

        // Iphone, color BLACK, Price 1000USD, brand APPLE, category TECHNOLOGY
        product = new Product("IPHONEX_01", "iPhone", "", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                createdBrand, createdCatalogue, Color.BLACK, 1000D, 100);
        productRepo.save(product);
    }

    @After
    public void tearDown() {
        productRepo.deleteAll();
        brandRepo.deleteAll();
        productCatalogueRepo.deleteAll();
    }

    @Test
    public void whenCreateProduct_thenReturnProduct() {
        // When
        ProductRequest productRequest = new ProductRequest("IPAD_PROD_01", "APPLE",
                "APPLE_01", "iPad", null, Color.BLACK, 800D, 100);
        Product product = productService.create(productRequest);
        // Then
        assertEquals("IPAD_PROD_01", product.getCode());
        assertEquals("APPLE", product.getBrand().getCode());
    }

    @Test
    public void whenFilterAll_thenReturnAllProduct() {
        // When
        Page<Product> products = productService.findProduct(null, null, null, null,
                null, 0, 10);
        // Then
        assertEquals(3, products.getTotalElements());
    }

    @Test
    public void whenFilterColor_thenReturnProductInColor() {
        // When
        Page<Product> products = productService.findProduct(null, null, "YELLOW", null,
                null, 0, 10);
        // Then
        assertEquals(1, products.getTotalElements());
    }

    @Test
    public void whenFilterPrice_thenReturnProductInPriceRange() {
        // When
        Page<Product> products = productService.findProduct(null, null, null,
                50D, 60D, 0, 10);
        // Then
        assertEquals(1, products.getTotalElements());
    }

    @Test
    public void whenFilterBrand_thenReturnProductInBrand() {
        // When
        Page<Product> products = productService.findProduct(null, "APPLE", null, null,
                null, 0, 10);
        // Then
        assertEquals(1, products.getTotalElements());
    }

    @Test
    public void whenFilterCatalogueAndPrice_thenReturnProductInCatalogueAndPrice() {
        // when
        Page<Product> products = productService.findProduct("ADIDAS_01", null, null, 50D,
                60D, 0, 10);
        // Then
        assertEquals(1, products.getTotalElements());
    }

    @Test
    public void whenGetProductDetail_thenReturnProduct() {
        // When
        Product product = productService.findByCode("ADIDAS_TSHIRT_02");

        //Then
        assertEquals("ADIDAS_TSHIRT_02", product.getCode());
        assertEquals(50D, product.getPrice());
        assertEquals(100, product.getQuantity());
        assertEquals(Color.YELLOW, product.getColor());
    }

    @Test(expected = NegativeProductQuantityException.class)
    public void whenUpdateProductQuantityGreater_thenThrowException() throws NegativeProductQuantityException,
            ProductNotFoundException {
        productService.updateProductQuantity("ADIDAS_TSHIRT_02", 10000);
    }

    @Test(expected = NegativeProductQuantityException.class)
    public void whenUpdateNegativeQuantity_thenThrowException() throws NegativeProductQuantityException,
            ProductNotFoundException {
        productService.updateProductQuantity("ADIDAS_TSHIRT_02", -1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void whenUpdateInvalidProductCode_thenThrowException() throws NegativeProductQuantityException,
            ProductNotFoundException {
        productService.updateProductQuantity("WRONG_PRODUCT_CODE", 1);
    }
}
