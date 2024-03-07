package com.ecommerce.myshop.ws;

import com.ecommerce.myshop.service.Authentication.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin(origins = "*")
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final AuthenticationService authenticationService;

    public WebSocketConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/ws");
    }

    @CrossOrigin(origins = "*")
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/socket.io")
                .setHandshakeHandler(new UserHandShakeHandler(authenticationService))
                .setAllowedOriginPatterns( "*" )
                .withSockJS();
    }
}
