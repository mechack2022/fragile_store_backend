package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.CartItemExeption;
import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.Cart;
import com.fragile.ecommercebackend.model.CartItem;
import com.fragile.ecommercebackend.model.Product;
import com.fragile.ecommercebackend.repository.CartRepository;
import com.fragile.ecommercebackend.repository.UserRepository;
import com.fragile.ecommercebackend.request.AddItemToCatReq;
import com.fragile.ecommercebackend.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartItemService cartItemService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

//    @Override
//    public String addCartItem(Long userId, AddItemToCatReq addItemToCartRequest) throws ProductException {
//        Cart cart = cartRepository.findCartByUserId(userId);
//        Product product = productService.findProductById(addItemToCartRequest.getProductId());
//        log.info("productId : {} and userId {}", product.getId(),  userRepository.findById(userId).get().getId() );
//        //check if item to add is present
//        CartItem isPresent = cartItemService.isCartItemExist(cart, product, addItemToCartRequest.getSize(), userId);
//        if (isPresent == null) {
//            CartItem newCartItem = new CartItem();
//            newCartItem.setCart(cart);
//            newCartItem.setUserId(userId);
//            newCartItem.setProduct(product);
//            newCartItem.setQuantity(addItemToCartRequest.getQuantity());
//            newCartItem.setSize(addItemToCartRequest.getSize());
//
//            int itemPrice = product.getDiscountedPrice() * addItemToCartRequest.getQuantity();
//            newCartItem.setPrice(itemPrice);
//
//            CartItem createdCartItem = cartItemService.createCartItem(newCartItem);
//
//            cart.getCartItems().add(createdCartItem);
//        }
//        return "Item added to cart successfully.";
//    }

    @Override
    public String addCartItem(Long userId, AddItemToCatReq addItemToCartRequest) throws ProductException {
        Cart cart = cartRepository.findCartByUserId(userId);
        Product product = productService.findProductById(addItemToCartRequest.getProductId());

        if (cart != null && product != null) {
            // Check if the item is already in the cart.
            log.info("I got inside the product not null, which the id is {} ", product.getId());
            CartItem existingCartItem = cartItemService.findByCartAndProductAndSizeAndUser(cart, product, addItemToCartRequest.getSize(), userId);

            if (existingCartItem != null) {
                log.info("this is the existing product info: {} ", existingCartItem);
                // Update quantity and price if the item already exists in the cart.
                existingCartItem.setQuantity(existingCartItem.getQuantity() + addItemToCartRequest.getQuantity());
                int itemPrice = product.getDiscountedPrice() * existingCartItem.getQuantity();
                existingCartItem.setPrice(itemPrice);
            } else {
                // Create a new cart item if it doesn't exist in the cart.
                CartItem newCartItem = new CartItem();
                log.info("This is the userId : {}", userId);
                newCartItem.setCart(cart);
                newCartItem.setUserId(userId);
                newCartItem.setProduct(product);
                log.info("This is the userId : {}, cart : {}, product: {} ", newCartItem.getUserId(), cart, product);
                newCartItem.setQuantity(addItemToCartRequest.getQuantity());
                newCartItem.setDiscountedPrice(product.getDiscountedPrice() * addItemToCartRequest.getQuantity());
                newCartItem.setSize(addItemToCartRequest.getSize());

                int itemPrice = product.getDiscountedPrice() * addItemToCartRequest.getQuantity();
                newCartItem.setPrice(itemPrice);

                CartItem createdCartItem = cartItemService.createCartItem(newCartItem);
                log.info("this is the new cart Item created {} ", createdCartItem);
                cart.getCartItems().add(createdCartItem);
            }

            // Update cart totals.
            updateCartTotals(cart);

            // Save the updated cart and cart items.
            cartRepository.save(cart);

            return "Item added to cart successfully.";
        } else {
            throw new ProductException("User or product not found.");
        }
    }


    //    @Override
//    public Cart findUserCart(Long userId) {
//        Cart cart = cartRepository.findCartByUserId(userId);
//
//        int totalPrice = 0;
//        int totalDiscountedPrice = 0;
//        int totalItem = 0;
//
//        for (CartItem cartItem : cart.getCartItems()) {
//            totalPrice = totalItem + cartItem.getPrice();
//            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
//            totalItem = totalItem + cartItem.getQuantity();
//        }
//        cart.setTotalPrice((double) totalPrice);
//        cart.setTotalDiscountedPrice(totalDiscountedPrice);
//        cart.setTotalItem(totalItem);
//        cart.setDiscount(totalPrice - totalDiscountedPrice);
//
//        return cart;
//    }
    @Override
    public Cart findUserCart(Long userId) {


        Cart cart = cartRepository.findCartByUserId(userId);

        if (cart != null) {
            int totalPrice = cart.getCartItems().stream().mapToInt(CartItem::getPrice).sum();
            int totalDiscountedPrice = cart.getCartItems().stream().mapToInt(CartItem::getDiscountedPrice).sum();
            int totalItem = cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();

            cart.setTotalPrice((double) totalPrice);
            cart.setTotalDiscountedPrice(totalDiscountedPrice);
            cart.setTotalItem(totalItem);
            cart.setDiscount(totalPrice - totalDiscountedPrice);

            return cart;
        } else {
            throw new UserException("User with the id does not exist " + userId);
        }
    }


    private void updateCartTotals(Cart cart) {
        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalPrice((double) totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice - totalDiscountedPrice);
    }


}
