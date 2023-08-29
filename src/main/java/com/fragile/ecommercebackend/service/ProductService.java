package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.model.Product;
import com.fragile.ecommercebackend.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Product createProduct(CreateProductRequest createProductRequest);

    List<Product> findAllProduct();
    String deleteProduct(Long productId) throws ProductException;
    Product updateProduct(Long productId, Product product) throws  ProductException;
    Product findProductById(Long productId) throws ProductException;
    List<Product> findProductByCategory(String category) throws ProductException;
    Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer
            miniPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer  pageSize);
}
