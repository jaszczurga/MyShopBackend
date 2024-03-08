package com.ecommerce.myshop.ws;
import com.ecommerce.myshop.service.chat.ChatService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class MessageController{
    private final Logger LOG = LoggerFactory.getLogger(UserHandShakeHandler.class);
    final WSService wsService;
   // final ChatService chatService;

    @MessageMapping("/send-messageDto-to-shop-manager")
    public void sendMessageToShopManager(MessageDto messageDto){
        //chatService.addMessage( messageDto );
        wsService.notifyManager( messageDto );
    }

    @MessageMapping("/send-messageDto-to-customer")
    public void sendMessageToCustomer(MessageDto messageDto){
        //chatService.addMessage( messageDto );
        LOG.info("Sending messageDto to customer with id: {}", messageDto.getReceiverId());
        wsService.notifyUser( messageDto );
    }

}
