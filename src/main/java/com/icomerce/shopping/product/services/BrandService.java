package com.icomerce.shopping.product.services;

import com.icomerce.shopping.product.entities.Brand;

import java.util.List;

public interface BrandService {
    Brand create(Brand brand);
    List<Brand> findAll();
}
