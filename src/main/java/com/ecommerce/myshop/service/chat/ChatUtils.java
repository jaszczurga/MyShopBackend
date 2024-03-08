package com.ecommerce.myshop.service.chat;

import com.ecommerce.myshop.dataTranferObject.ConversationDto;
import com.ecommerce.myshop.dataTranferObject.UserDto;
import com.ecommerce.myshop.entity.Authentication.User;
import com.ecommerce.myshop.entity.chat.Conversation;
import com.ecommerce.myshop.entity.chat.Message;
import com.ecommerce.myshop.ws.MessageDto;
import org.springframework.stereotype.Service;

@Service
public class ChatUtils{

    public UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId().toString());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public ConversationDto convertToDto(Conversation conversation) {
        ConversationDto conversationDto = new ConversationDto();
        conversationDto.setConversationId(conversation.getConversationId());
        conversationDto.setUser1(convertUserToDto(conversation.getUser1()));
        conversationDto.setUser2(convertUserToDto(conversation.getUser2()));
        conversationDto.setMessages(conversation.getMessages().stream().map(this::convertMessageToDto).toList());
        return conversationDto;
    }

    public MessageDto convertMessageToDto(Message message) {
        MessageDto messageDto = new MessageDto();

        messageDto.setSenderId( message.getUserSender().getId().toString() );
        messageDto.setReceiverId( message.getUser().getId().toString());
        messageDto.setContent( message.getContent() );
        messageDto.setTimestamp( message.getTimestamp() );
        return messageDto;
    }

}
