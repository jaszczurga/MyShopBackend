package com.ecommerce.myshop.service.chat;

import com.ecommerce.myshop.dataTranferObject.ConversationDto;
import com.ecommerce.myshop.ws.MessageDto;

public interface ChatService{
    void createConversation(Integer user1Id, Integer user2Id);
//    void deleteConversation(Integer conversationId);
//    void sendMessage(Integer conversationId, Integer senderId, String message);
     ConversationDto getConversation(Integer conversationId,int pageNumberOfMessages, int pageSizeOfMessages);

    ConversationDto getConversationByUsersId(Integer user1Id , Integer user2Id,int pageNumberOfMessages, int pageSizeOfMessages);

    void addMessage(MessageDto message);
//    ListDto getConversations(Integer userId);
//    ListDto getMessages(Integer conversationId);

}
