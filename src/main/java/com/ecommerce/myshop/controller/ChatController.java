package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.Exceptions.NotFoundException;
import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.dataTranferObject.ConversationDto;
import com.ecommerce.myshop.service.Authentication.AuthenticationService;
import com.ecommerce.myshop.service.chat.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RestController
@RequestMapping ("/api/users")
public class ChatController{

    private final ChatService chatService;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    //conversation is created during registration

    //get conversation for choosen user we can filter just by one user because we use conversation between manager ans users
    @GetMapping("/conversationByUserId")
    public ResponseEntity<ConversationDto> getConversationByUserId(@RequestParam int userId,
                                                                   @RequestParam(required = false,defaultValue = "0") int pageNumberOfMessages,
                                                                   @RequestParam(required = false,defaultValue = "0") int pageSizeOfMessages
    ) {
        //get id of admin@admin.com
        int adminId = userRepository.findByEmail("admin@admin.com").orElseThrow(
                () -> new NotFoundException("No Admin in the system")
        ).getId();

        ConversationDto conversationDto = chatService.getConversationByUsersId(adminId, userId, pageNumberOfMessages, pageSizeOfMessages);
        return ResponseEntity.ok(conversationDto);
    }

    //get conversation by conversation id
    @GetMapping("/conversationById")
    public ResponseEntity<ConversationDto> getConversationById(@RequestParam int conversationId,
                                                               @RequestParam(required = false,defaultValue = "0") int pageNumberOfMessages,
                                                               @RequestParam(required = false,defaultValue = "0") int pageSizeOfMessages
    ) {
        ConversationDto conversationDto = chatService.getConversation(conversationId, pageNumberOfMessages, pageSizeOfMessages);
        return ResponseEntity.ok(conversationDto);
    }




}
