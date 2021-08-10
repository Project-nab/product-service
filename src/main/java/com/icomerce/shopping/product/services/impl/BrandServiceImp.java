package com.icomerce.shopping.product.services.impl;

import com.icomerce.shopping.product.entities.Brand;
import com.icomerce.shopping.product.repositories.BrandRepo;
import com.icomerce.shopping.product.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrandServiceImp implements BrandService {
    private final BrandRepo brandRepo;

    @Autowired
    public BrandServiceImp(BrandRepo brandRepo) {
        this.brandRepo = brandRepo;
    }

    @Override
    public Brand create(Brand brand) {
        brand.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        brand.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return brandRepo.save(brand);
    }

    @Override
    @Cacheable(value = "findAllBrand")
    public List<Brand> findAll() {
        return (List<Brand>) brandRepo.findAll();
    }
}
