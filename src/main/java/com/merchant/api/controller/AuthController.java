package com.merchant.api.controller;

import com.merchant.api.dto.MerchantRegistrationRequest;
import com.merchant.api.model.Merchant;
import com.merchant.api.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(
        summary = "Register a new merchant",
        description = "Register a new merchant and return JWT token"
    )
    public ResponseEntity<String> register(@Valid @RequestBody MerchantRegistrationRequest request) {
        Merchant merchant = new Merchant();
        merchant.setBusinessName(request.getBusinessName());
        merchant.setEmail(request.getEmail());
        merchant.setPassword(request.getPassword());
        merchant.setPhoneNumber(request.getPhoneNumber());
        merchant.setAddress(request.getAddress());
        merchant.setBusinessType(request.getBusinessType());
        merchant.setRegistrationNumber(request.getRegistrationNumber());
        merchant.setTaxId(request.getTaxId());
        merchant.setActive(true);
        
        String token = authService.register(merchant);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    @Operation(
        summary = "Authenticate a merchant",
        description = "Authenticate a merchant with email and password"
    )
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request.email(), request.password()));
    }

    public record LoginRequest(String email, String password) {}
} 