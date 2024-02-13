package com.ecommerce.myshop.service.Authentication;


import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.dataTranferObject.Authentication.AuthenticationRequest;
import com.ecommerce.myshop.dataTranferObject.Authentication.AuthenticationResponse;
import com.ecommerce.myshop.dataTranferObject.Authentication.RegisterRequest;
import com.ecommerce.myshop.entity.Authentication.Role;
import com.ecommerce.myshop.entity.Authentication.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}