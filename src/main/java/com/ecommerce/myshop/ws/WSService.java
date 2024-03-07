package com.ecommerce.myshop.ws;


import com.ecommerce.myshop.Exceptions.UserNotFoundException;
import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.entity.Authentication.User;
import com.ecommerce.myshop.service.Authentication.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    //write message to selected user with id
    public void notifyUser( Message message) {

        messagingTemplate.convertAndSendToUser(message.getReceiverId(), "/topic/messages-from-manager",message);
    }

    public void notifyManager( Message message) {
        User user = userRepository.findByEmail( "admin@admin.com" ).orElseThrow( () -> new UserNotFoundException( "No Admin user found in Db" ) );
        message.setReceiverId( user.getId().toString() );

        messagingTemplate.convertAndSendToUser(message.getReceiverId(), "/topic/messages-from-customers",message);
    }

}