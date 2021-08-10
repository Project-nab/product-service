package com.icomerce.shopping.product.payload.request;

import com.icomerce.shopping.product.entities.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ AllArgsConstructor
public class ProductRequest implements Serializable {
    private String productCode;
    private String brandCode;
    private String catalogueCode;
    private String productName;
    private String thumnail;
    private Color color;
    private Double price;
    private Integer quantity;
}
