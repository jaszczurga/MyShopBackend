package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.dataTranferObject.Authentication.AuthenticationRequest;
import com.ecommerce.myshop.dataTranferObject.Authentication.AuthenticationResponse;
import com.ecommerce.myshop.dataTranferObject.Authentication.RegisterRequest;
import com.ecommerce.myshop.dataTranferObject.Authentication.UserAuthoritiesDto;
import com.ecommerce.myshop.service.Authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/roles")
    public ResponseEntity<UserAuthoritiesDto> getRoles() {
        return ResponseEntity.ok(authenticationService.getRoles());
    }
}