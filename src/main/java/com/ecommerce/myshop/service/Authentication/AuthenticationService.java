package com.ecommerce.myshop.service.Authentication;


import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.dataTranferObject.Authentication.AuthenticationRequest;
import com.ecommerce.myshop.dataTranferObject.Authentication.AuthenticationResponse;
import com.ecommerce.myshop.dataTranferObject.Authentication.RegisterRequest;
import com.ecommerce.myshop.dataTranferObject.Authentication.UserAuthoritiesDto;
import com.ecommerce.myshop.entity.Authentication.Role;
import com.ecommerce.myshop.entity.Authentication.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        var user = User.builder()
                .firstName( request.getFirstName() )
                .lastName( request.getLastName() )
                .email( request.getEmail() )
                .password( passwordEncoder.encode(request.getPassword()) )
                .role( Role.USER )
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }


    public UserAuthoritiesDto getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roles =  authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect( Collectors.joining(","));
        return UserAuthoritiesDto
                .builder()
                .rolesString( roles )
                .build();
    }


    public String getAuthenticatedUserEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
}

    public int getLoggedInUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + username));
            return user.getId();
        }

        throw new IllegalStateException("User not logged in");
    }



}