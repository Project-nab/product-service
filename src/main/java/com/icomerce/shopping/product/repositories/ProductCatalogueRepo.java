package com.icomerce.shopping.product.repositories;

import com.icomerce.shopping.product.entities.ProductCatalogue;
import org.springframework.data.repository.CrudRepository;

public interface ProductCatalogueRepo extends CrudRepository<ProductCatalogue, String> {
}
