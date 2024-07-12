package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.dataTranferObject.Authentication.*;
import com.ecommerce.myshop.service.Authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    //get currently logged in user id
    @GetMapping("/currentUserId")
    public ResponseEntity<UserIdRequest> getCurrentUserId() {
        UserIdRequest userIdRequest = new UserIdRequest(authenticationService.getLoggedInUserId());
        return ResponseEntity.ok(userIdRequest);
    }

    @GetMapping("/roles")
    public ResponseEntity<UserAuthoritiesDto> getRoles() {
        return ResponseEntity.ok(authenticationService.getRoles());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
       return ResponseEntity.ok(authenticationService.deleteUser(email));
    }
}