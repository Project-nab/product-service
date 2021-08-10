package com.icomerce.shopping.product.controllers;

import com.icomerce.shopping.product.entities.Brand;
import com.icomerce.shopping.product.payload.response.BaseResponse;
import com.icomerce.shopping.product.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @RequestMapping(value = "/brands", method = RequestMethod.POST)
    public BaseResponse create(@RequestBody Brand brand) {
        Brand createdBrand = brandService.create(brand);
        return new BaseResponse(HttpServletResponse.SC_OK, "Create brand success", createdBrand);
    }

    @RequestMapping(value = "/brands", method = RequestMethod.GET)
    public BaseResponse findAll() {
        List<Brand> brands = brandService.findAll();
        return new BaseResponse(HttpServletResponse.SC_OK, "Get brand list success", brands);
    }
}
