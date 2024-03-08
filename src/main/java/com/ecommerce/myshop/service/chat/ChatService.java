package com.ecommerce.myshop.service.chat;

import com.ecommerce.myshop.dataTranferObject.ConversationDto;
import com.ecommerce.myshop.dataTranferObject.ListDto;
import com.ecommerce.myshop.entity.chat.Conversation;

public interface ChatService{
//    void createConversation(Integer user1Id, Integer user2Id);
//    void deleteConversation(Integer conversationId);
//    void sendMessage(Integer conversationId, Integer senderId, String message);
    ConversationDto getConversation(Integer conversationId);
    ConversationDto getConversationByUsersId(Integer user1Id, Integer user2Id);
//    ListDto getConversations(Integer userId);
//    ListDto getMessages(Integer conversationId);

}
