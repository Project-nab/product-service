package com.icomerce.shopping.product.repositories.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

public interface QuerySpecification<T, Q> {
    Specification<T> build(Q query);
}
