package com.ecommerce.myshop.config.Authentication;

import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.entity.Authentication.Role;
import com.ecommerce.myshop.entity.Authentication.User;
import com.ecommerce.myshop.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private  final AuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final ChatService chatService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests((authorize)-> authorize
                        .requestMatchers(  "/api/v1/auth/**","/**")
                        .permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/action/**", "/api/orders/**")
//                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy( SessionCreationPolicy.STATELESS ))
                .authenticationProvider( authenticationProvider )
                .addFilterBefore( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class );

        return http.build();
    }

    @Bean
    CommandLineRunner initAdminUser(UserRepository userRepository) {
        return args -> {
            String email = "admin@admin.com";
            if (userRepository.findByEmail(email).isEmpty()) {
                User admin = new User();
                admin.setEmail(email);
                admin.setRole( Role.ADMIN);
                admin.setFirstName( "Admin" );
                admin.setLastName( "Admin" );
                admin.setPassword( passwordEncoder.encode("admin"));
                // set other properties like password, etc.
                userRepository.save(admin);
            }
            String email2 = "user@user.com";
            if (userRepository.findByEmail(email2).isEmpty()) {
                User user = new User();
                user.setEmail(email2);
                user.setRole( Role.USER);
                user.setFirstName( "User" );
                user.setLastName( "User" );
                user.setPassword( passwordEncoder.encode("user"));
                // set other properties like password, etc.
                userRepository.save(user);
                int userGeneratedId = userRepository.findByEmail(email2)
                        .orElseThrow(() -> new RuntimeException("User not found with email: " + email2))
                        .getId();
                chatService.createConversation(1, userGeneratedId);
            }
        };
    }
}