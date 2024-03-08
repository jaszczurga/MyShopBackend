package com.ecommerce.myshop.dataTranferObject;

import com.ecommerce.myshop.entity.Authentication.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConversationDto{
    private Integer conversationId;
    private User user1;
    private User user2;
}
