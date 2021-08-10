package com.icomerce.shopping.product.repositories;

import com.icomerce.shopping.product.entities.Brand;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepo extends CrudRepository<Brand, String> {
}
