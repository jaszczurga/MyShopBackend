package com.ecommerce.myshop.ws;

import com.ecommerce.myshop.dao.Authentication.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController{

    WSService wsService;

    @MessageMapping("/send-message-to-shop-manager")
    public void sendMessageToShopManager(Message message){

        wsService.notifyManager( message );
    }

    @MessageMapping("/send-message-to-customer")
    public void sendMessageToCustomer(Message message){

        wsService.notifyUser( message );
    }

}
