package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.model.Category;
import com.fragile.ecommercebackend.model.Product;
import com.fragile.ecommercebackend.repository.CategoryRepository;
import com.fragile.ecommercebackend.repository.ProductRepository;
import com.fragile.ecommercebackend.repository.UserRepository;
import com.fragile.ecommercebackend.request.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImpl implements ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest createProductRequest) {
//        Category topLevel = categoryRepository.findByName(createProductRequest.getTopLevelCategory());

//        if (topLevel == null) {
//            Category topLevelCategory = new Category();
//            topLevelCategory.setName(createProductRequest.getTopLevelCategory());
//            topLevelCategory.setLevel(1);
//
//            topLevel = categoryRepository.save(topLevelCategory);
//        }
//        Category secondLevel = categoryRepository.findByNameAndParent(createProductRequest.getSecondLevelCategory(), topLevel.getName());
//        if (secondLevel == null) {
//            Category secondLevelCategory = new Category();
//            secondLevelCategory.setName(createProductRequest.getSecondLevelCategory());
//            secondLevelCategory.setParentCategory(topLevel);
//            secondLevelCategory.setLevel(2);
//
//            secondLevel = categoryRepository.save(secondLevelCategory);
//        }
//        Category thirdLevel = categoryRepository.findByNameAndParent(createProductRequest.getThirdLevelCategory(), secondLevel.getName());
//        if (thirdLevel == null) {
//            Category thirdLevelCategory = new Category();
//            thirdLevelCategory.setName(createProductRequest.getThirdLevelCategory());
//            thirdLevelCategory.setParentCategory(secondLevel);
//            thirdLevelCategory.setLevel(3);
//
//            thirdLevel = categoryRepository.save(thirdLevelCategory);
//        }

        Category topLevel = categoryRepository.findByNameIgnoreCase(createProductRequest.getTopLevelCategory());

        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(createProductRequest.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            log.info("This is the top level1 category : {} ", topLevelCategory );
            topLevel = categoryRepository.save(topLevelCategory);
        }

// Fetch the second-level category by name and parent category name
        Category secondLevel = categoryRepository.findByNameAndParent(createProductRequest.getSecondLevelCategory(), topLevel.getName());

        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(createProductRequest.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }

// Fetch the third-level category by name and parent category name
        Category thirdLevel = categoryRepository.findByNameAndParent(createProductRequest.getThirdLevelCategory(), secondLevel.getName());

        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(createProductRequest.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

// ... rest of your code


        Product product = Product.
                builder()
                .color(createProductRequest.getColor())
                .createdAt(LocalDateTime.now())
                .title(createProductRequest.getTitle())
                .brand(createProductRequest.getBrand())
                .category(thirdLevel)
                .description(createProductRequest.getDescription())
                .imageUrl(createProductRequest.getImageUrl())
                .quantity(createProductRequest.getQuantity())
                .price(createProductRequest.getPrice())
                .discountPercent(createProductRequest.getDiscountPercent())
                .discountedPrice(createProductRequest.getDiscountedPrice())
                .size(createProductRequest.getSize())
                .quantity(createProductRequest.getQuantity())
                .build();
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = productRepository.findById(productId).get();
        product.getSize().clear();
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        Product foundProduct = productRepository.findById(productId).get();
        if (foundProduct.getQuantity() != 0) {
            foundProduct.setQuantity(product.getQuantity());
        }
        return productRepository.save(foundProduct);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return product.get();
        }
        throw new ProductException("Product nto found with the id -" + productId);
    }

    @Override
    public List<Product> findProductByCategory(String category) throws ProductException {
        return null;
    }

//    @Override
//    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer miniPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
////        List<Product> productList = productRepository.findProductsByCategoryAndPriceRangeAndDiscount(category, maxPrice, miniPrice, minDiscount, sort);
//        List<Product> productList = productRepository.findProductsByCategoryAndPriceRangeAndDiscount(category, miniPrice, maxPrice, minDiscount, sort);
//
//        if (!colors.isEmpty()) {
//            productList = productList.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
//        }
//        if (stock != null) {
//            if (stock.equals("in_Stock")) {
//                productList = productList.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
//            } else if (stock.equals("out_stcok")) {
//                productList = productList.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
//            }
//        }
//
//        int startIndex = (int) pageable.getOffset();
//        int endIndex = Math.min(startIndex + pageable.getPageSize(), productList.size());
//
//        List<Product> pageContent = productList.subList(startIndex, endIndex);
//        Page<Product> filterProducts = new PageImpl<>(pageContent, pageable, productList.size());
//        return filterProducts;
//    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer miniPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> productList = productRepository.findProductsByCategoryAndPriceRangeAndDiscount(category, miniPrice, maxPrice, minDiscount, sort);

        if (colors != null && !colors.isEmpty()) {
            productList = productList.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }

        if (stock != null) {
            if (stock.equals("in_Stock")) {
                productList = productList.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_stock")) {
                productList = productList.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            } 
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), productList.size());

        List<Product> pageContent = productList.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, pageable, productList.size());
    }


}
