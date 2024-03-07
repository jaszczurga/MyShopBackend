package com.ecommerce.myshop.ws;

import com.ecommerce.myshop.service.Authentication.AuthenticationService;
import com.sun.security.auth.UserPrincipal;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class UserHandShakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = LoggerFactory.getLogger(UserHandShakeHandler.class);
    private final AuthenticationService authenticationService;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//        final String randomId = UUID.randomUUID().toString();
//        LOG.info("User with ID '{}' opened the page", randomId);
        String userIdSession = String.valueOf( authenticationService.getLoggedInUserId() );
        LOG.info( "User with ID '{}' opened the page", userIdSession);

        return new UserPrincipal(userIdSession);
    }
}
