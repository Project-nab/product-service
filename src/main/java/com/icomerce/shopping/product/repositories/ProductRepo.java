package com.icomerce.shopping.product.repositories;

import com.icomerce.shopping.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, String>, JpaSpecificationExecutor<Product> {
    Page<Product> findAll(Specification<Product> productSpecification, Pageable pageable);
}
