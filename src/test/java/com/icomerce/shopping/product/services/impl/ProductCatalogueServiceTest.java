package com.icomerce.shopping.product.services.impl;

import com.icomerce.shopping.product.entities.CatalogueType;
import com.icomerce.shopping.product.entities.ProductCatalogue;
import com.icomerce.shopping.product.repositories.ProductCatalogueRepo;
import com.icomerce.shopping.product.services.ProductCatalogueService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductCatalogueServiceTest {
    @Autowired
    private ProductCatalogueRepo productCatalogueRepo;

    @Autowired
    private ProductCatalogueService productCatalogueService;

    @Before
    public void setup() {
        ProductCatalogue productCatalogue = new ProductCatalogue("KID_FASHION", "FASHION", CatalogueType.FASHION,
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), null);
        productCatalogueRepo.save(productCatalogue);
    }

    @After
    public void tearDown() {
        productCatalogueRepo.deleteAll();
    }

    @Test
    public void whenCreate_thenReturnCatalogue() {
        // When
        ProductCatalogue productCatalogue = new ProductCatalogue("MEN_FASHION", "FASHION", CatalogueType.FASHION,
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), null);
        ProductCatalogue createdProductCatalogue = productCatalogueService.create(productCatalogue);

        // Then
        assertEquals("MEN_FASHION", createdProductCatalogue.getCode());
        assertEquals(CatalogueType.FASHION, createdProductCatalogue.getCatalogueType());
    }

    @Test
    public void whenFindAll_thenReturnAll() {
        // When
        List<ProductCatalogue> productCatalogues = productCatalogueService.findAll();

        // Then
        assertEquals(1, productCatalogues.size());
    }
}
