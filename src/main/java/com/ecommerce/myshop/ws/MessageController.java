package com.ecommerce.myshop.ws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ecommerce.myshop.dao.Authentication.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController{
    private final Logger LOG = LoggerFactory.getLogger(UserHandShakeHandler.class);
    WSService wsService;

    @MessageMapping("/send-message-to-shop-manager")
    public void sendMessageToShopManager(Message message){

        wsService.notifyManager( message );
    }

    @MessageMapping("/send-message-to-customer")
    public void sendMessageToCustomer(Message message){
        LOG.info("Sending message to customer with id: {}", message.getContent());
        wsService.notifyUser( message );
    }

}
