package com.productService.service;

import com.productService.dto.ProductRequest;
import com.productService.dto.ProductResponse;
import com.productService.entity.Product;
import com.productService.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private  final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){

        Product product=Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is created ",product.getId());
    }

    public List<ProductResponse>  getAllProducts(){

        List<Product> products = productRepository.findAll();

        //creating own object type :- mapToProductResponse  to return as list .
       return products.stream().map(this::mapToProductResponse).toList();
    }

    private  ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
