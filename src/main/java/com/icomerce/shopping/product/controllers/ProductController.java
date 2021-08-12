package com.icomerce.shopping.product.controllers;

import com.icomerce.shopping.product.entities.Product;
import com.icomerce.shopping.product.payload.request.ProductRequest;
import com.icomerce.shopping.product.payload.response.BaseResponse;
import com.icomerce.shopping.product.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final HttpServletResponse response;

    @Autowired
    public ProductController(ProductService productService,
                             HttpServletResponse response) {
        this.productService = productService;
        this.response = response;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public BaseResponse createProduct(@RequestBody ProductRequest productRequest) {
        logger.info("Creat product - process started");
        Product product = productService.create(productRequest);
        if(product != null) {
            logger.info("Create product - process failed");
            response.setStatus(HttpServletResponse.SC_CREATED);
            return new BaseResponse(HttpServletResponse.SC_CREATED, "Create product success", product);
        } else {
            logger.info("Create product - process success");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new BaseResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Create product error", null);
        }
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public BaseResponse findProduct(@RequestParam(value = "categoryCode", required = false) String categoryId,
                                    @RequestParam(value = "brandCode", required = false) String brandId,
                                    @RequestParam(value = "colorType", required = false) String color,
                                    @RequestParam(value = "priceMin", required = false) Double priceMin,
                                    @RequestParam(value = "priceMax", required = false) Double priceMax,
                                    @RequestParam(value = "offset", defaultValue = "0") int offet,
                                    @RequestParam(value = "limit", defaultValue = "10") int limit,
                                    Principal principal,
                                    HttpSession session) {
        logger.info("Query with user name {}, session ID {}", principal.getName(), session.getId());
        Page<Product> products = productService.findProduct(categoryId, brandId, color, priceMin, priceMax, offet, limit);
        return new BaseResponse(HttpServletResponse.SC_OK, "Get product successful", products);
    }

    @RequestMapping(value = "/products/{productCode}", method = RequestMethod.GET)
    public BaseResponse findProductByCode(@PathVariable(value = "productCode") String productId) {
        Product product = productService.findByCode(productId);
        if(product != null) {
            return new BaseResponse(HttpServletResponse.SC_OK, "Success", product);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "Invalid product id", product);
        }
    }
}
