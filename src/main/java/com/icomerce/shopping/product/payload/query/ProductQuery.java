package com.icomerce.shopping.product.payload.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuery implements Serializable {
    private String categoryCode;
    private String brandCode;
    private String color;
    private Double priceMin;
    private Double priceMax;
}
