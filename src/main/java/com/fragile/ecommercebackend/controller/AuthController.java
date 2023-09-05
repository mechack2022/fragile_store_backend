package com.fragile.ecommercebackend.controller;


import com.fragile.ecommercebackend.config.JwtProvider;
import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.Cart;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.repository.UserRepository;
import com.fragile.ecommercebackend.request.LoginRequest;
import com.fragile.ecommercebackend.response.AuthResponse;
import com.fragile.ecommercebackend.service.CartService;
import com.fragile.ecommercebackend.service.CustomUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserServiceImpl customUserServiceImpl;

    private final CartService cartService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();

        User foundUser = userRepository.findByEmail(email);
        if (foundUser != null) {
            throw new UserException("Email already exist, choose another email");
        }
        // create a new user;
        User newUser = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        userRepository.save(newUser);
//        create user cart
        Cart createdCart = cartService.createCart(newUser);
        log.info("user cart : {}", createdCart);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = AuthResponse.builder()
                .jwt(token)
                .message("User created successfully.")
                .build();
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticateUser(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = AuthResponse.builder()
                .jwt(token)
                .message("User login successfully.")
                .build();
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    private Authentication authenticateUser(String email, String password) {
            UserDetails userDetails = customUserServiceImpl.loadUserByUsername(email);

            if (userDetails == null) {
                throw new BadCredentialsException("Invalid username provided ");
            }

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid password provided");
            }

            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }

}
