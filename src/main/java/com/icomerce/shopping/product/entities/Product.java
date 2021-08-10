package com.icomerce.shopping.product.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "product_code")
    private String code;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "thumnail")
    private String thumnail;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "brand_code", nullable = false)
    @JsonBackReference
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "product_catalogue_code", nullable = false)
    @JsonBackReference
    private ProductCatalogue productCatalogue;

    @Column(name = "color")
    private Color color;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;
}
