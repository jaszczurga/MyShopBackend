package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.dataTranferObject.ConversationDto;
import com.ecommerce.myshop.service.chat.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@AllArgsConstructor
@RestController
@RequestMapping ("/api/users")
public class ChatController{

    private final ChatService chatService;
    private final UserRepository userRepository;

    //get conversation for choosen user we can filter just by one user because we use conversation between manager ans users
    @GetMapping("/conversationByUserId")
    public ResponseEntity<ConversationDto> getConversationByUserId(@RequestParam int userId) {
        //get id of admin@admin.com
        int adminId = userRepository.findByEmail("admin@admin.com").orElseThrow().getId();

        ConversationDto conversationDto = chatService.getConversationByUsersId(adminId, userId);
        return ResponseEntity.ok(conversationDto);
    }

    //get conversation by conversation id
    @GetMapping("/conversationById")
    public ResponseEntity<ConversationDto> getConversationById(@RequestParam int conversationId) {
        ConversationDto conversationDto = chatService.getConversation(conversationId);
        return ResponseEntity.ok(conversationDto);
    }

}
