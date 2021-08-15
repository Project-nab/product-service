package com.icomerce.shopping.product.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCatalogue implements Serializable {
    @Id
    @Column(name = "catalogue_code")
    private String code;

    @Column(name = "catalogue_name")
    private String catalogueName;

    @Column(name = "catalogue_type")
    private CatalogueType catalogueType;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "productCatalogue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Product> products;
}
