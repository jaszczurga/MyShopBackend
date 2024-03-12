package com.ecommerce.myshop.ws;


import com.ecommerce.myshop.Exceptions.NotFoundException;
import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.entity.Authentication.User;
import com.ecommerce.myshop.service.chat.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ChatService chatService;

    //write messageDto to selected user with id
    public void notifyUser( MessageDto messageDto) {
        chatService.addMessage( messageDto );

        messagingTemplate.convertAndSendToUser( messageDto.getReceiverId(), "/topic/messages-from-manager", messageDto );
    }

    public void notifyManager( MessageDto messageDto) {
        User user = userRepository.findByEmail( "admin@admin.com" ).orElseThrow( () -> new NotFoundException( "No Admin user found in Db" ) );
        messageDto.setReceiverId( user.getId().toString() );
        chatService.addMessage( messageDto );
        messagingTemplate.convertAndSendToUser( messageDto.getReceiverId(), "/topic/messages-from-customers", messageDto );
    }

}