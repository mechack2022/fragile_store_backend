package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.model.Product;
import com.fragile.ecommercebackend.request.CreateProductRequest;
import com.fragile.ecommercebackend.response.ApiResponse;
import com.fragile.ecommercebackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/controller")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProductHander(@RequestBody CreateProductRequest createProductRequest) {
        Product createdProduct = productService.createProduct(createProductRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable("productId") Long productId) throws ProductException {
        String message = productService.deleteProduct(productId);
        ApiResponse apiResponse = new ApiResponse(message, true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProductHandler() {
        List<Product> products = productService.findAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productRequest, @PathVariable("productId") Long productId) throws ProductException {
        Product updatedProduct = productService.updateProduct(productId, productRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req) {
        for (CreateProductRequest product : req) {
            productService.createProduct(product);
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product created Successful");
        apiResponse.setStatus(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
