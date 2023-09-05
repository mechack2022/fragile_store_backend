package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.model.Product;
import com.fragile.ecommercebackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
@GetMapping("/products")
public ResponseEntity<Page<Product>> findProductByCategoryHandler(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) List<String> color,
        @RequestParam(required = false) List<String> size,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice,
        @RequestParam(required = false) Integer minDiscount,
        @RequestParam(required = false) String sort,
        @RequestParam(required = false) String stock,
        @RequestParam(defaultValue = "0") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<Product> products = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);

    return new ResponseEntity<>(products, HttpStatus.OK);
}


    @GetMapping("/product/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable("productId") Long productId) throws ProductException {
        return new ResponseEntity<>(productService.findProductById(productId), HttpStatus.OK);
    }


}
