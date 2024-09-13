package com.productService.controller;

import com.productService.dto.ProductRequest;
import com.productService.dto.ProductResponse;
import com.productService.entity.Product;
import com.productService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody  ProductRequest productRequest){

        productService.createProduct(productRequest);
    }
// getting all the items
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts(){
     return productService.getAllProducts();
    }
}
