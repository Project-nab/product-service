package com.icomerce.shopping.product.services.impl;

import com.icomerce.shopping.product.entities.Brand;
import com.icomerce.shopping.product.repositories.BrandRepo;
import com.icomerce.shopping.product.services.BrandService;
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
public class BrandServiceTest {
    @Autowired
    public BrandRepo brandRepo;

    @Autowired
    public BrandService brandService;

    @Before
    public void setup() {
        Brand brand = new Brand("ADIDAS", "ADIDAS", "GERMANY", null, null, null);
        brandRepo.save(brand);
    }

    @After
    public void tearDown() {
        brandRepo.deleteAll();
    }

    @Test
    public void whenCreate_thenReturnBrand() {
        // When
        Brand brand = new Brand("APPLE", "APPLE", "USA", Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()), null);
        Brand createdBrand = brandService.create(brand);

        // Then
        assertEquals("APPLE", createdBrand.getCode());
        assertEquals("USA", createdBrand.getAddress());
    }

    @Test
    public void whenFindAll_thenReturnAll() {
        // When
        List<Brand> brands = brandService.findAll();

        // Then
        assertEquals(1, brands.size());
    }
}
