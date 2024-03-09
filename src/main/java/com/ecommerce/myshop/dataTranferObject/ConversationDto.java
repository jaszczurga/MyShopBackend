package com.ecommerce.myshop.dataTranferObject;

import com.ecommerce.myshop.entity.Authentication.User;
import com.ecommerce.myshop.entity.chat.Message;
import com.ecommerce.myshop.ws.MessageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConversationDto{
    private Integer conversationId;
    private UserDto user1;
    private UserDto user2;
    private List<MessageDto> messages;
}
