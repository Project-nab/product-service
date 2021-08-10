package com.icomerce.shopping.product.repositories.specification.imp;

import com.icomerce.shopping.product.entities.Color;
import com.icomerce.shopping.product.entities.Product;
import com.icomerce.shopping.product.payload.query.ProductQuery;
import com.icomerce.shopping.product.repositories.specification.ProductSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProductSpecificationImp implements ProductSpecification {
    @Override
    public Specification<Product> build(ProductQuery request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(request.getCategoryCode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("productCatalogue").get("code"), request.getCategoryCode()));
            }

            if(request.getBrandCode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand").get("code"), request.getBrandCode()));
            }

            if(request.getColor() != null && !request.getColor().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("color"), Color.valueOf(request.getColor()).ordinal()));
            }

            if(request.getPriceMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getPriceMin()));
            }

            if(request.getPriceMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getPriceMax()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("updatedAt")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
