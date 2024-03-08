package com.ecommerce.myshop.ws;


import com.ecommerce.myshop.Exceptions.UserNotFoundException;
import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.entity.Authentication.User;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    //write messageDto to selected user with id
    public void notifyUser( MessageDto messageDto) {

        messagingTemplate.convertAndSendToUser( messageDto.getReceiverId(), "/topic/messages-from-manager", messageDto );
    }

    public void notifyManager( MessageDto messageDto) {
        User user = userRepository.findByEmail( "admin@admin.com" ).orElseThrow( () -> new UserNotFoundException( "No Admin user found in Db" ) );
        messageDto.setReceiverId( user.getId().toString() );

        messagingTemplate.convertAndSendToUser( messageDto.getReceiverId(), "/topic/messages-from-customers", messageDto );
    }

}