package com.icomerce.shopping.product.controllers;

import com.icomerce.shopping.product.entities.ProductCatalogue;
import com.icomerce.shopping.product.payload.response.BaseResponse;
import com.icomerce.shopping.product.services.ProductCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ProductCatalogueController {
    private final ProductCatalogueService productCatalogueService;

    @Autowired
    public ProductCatalogueController(ProductCatalogueService productCatalogueService) {
        this.productCatalogueService = productCatalogueService;
    }

    @RequestMapping(value = "/product-catalogues", method = RequestMethod.POST)
    public BaseResponse create(@RequestBody ProductCatalogue productCatalogue) {
        ProductCatalogue createdProductCatalogue = productCatalogueService.create(productCatalogue);
        return new BaseResponse(HttpServletResponse.SC_OK, "Create catalogue success", createdProductCatalogue);
    }

    @RequestMapping(value = "/product-catalogues", method = RequestMethod.GET)
    public BaseResponse findAll() {
        List<ProductCatalogue> productCatalogues = productCatalogueService.findAll();
        return new BaseResponse(HttpServletResponse.SC_OK, "Get catalogue success", productCatalogues);
    }
}
