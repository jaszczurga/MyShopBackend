package com.ecommerce.myshop.service.chat;

import com.ecommerce.myshop.Exceptions.NotFoundException;
import com.ecommerce.myshop.dao.chat.ConversationRepository;
import com.ecommerce.myshop.dataTranferObject.ConversationDto;
import com.ecommerce.myshop.dataTranferObject.ListDto;
import com.ecommerce.myshop.entity.chat.Conversation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ConversationRepository conversationRepository;
    @Override
  public ConversationDto getConversation(Integer conversationId) {
    Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
    if (conversation == null) {
        throw new NotFoundException( "Conversation not found with id: " + conversationId );
    }
    return convertToDto(conversation);
}

private ConversationDto convertToDto(Conversation conversation) {
        ConversationDto conversationDto = new ConversationDto();
        conversationDto.setConversationId(conversation.getConversationId());
        conversationDto.setUser1(conversation.getUser1());
        conversationDto.setUser2(conversation.getUser2());
    return new ConversationDto();
}

    @Override
    public ConversationDto getConversationByUsersId(Integer user1Id , Integer user2Id) {
        Conversation conversation = conversationRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (conversation == null) {
            throw new NotFoundException( "Conversation not found with user1Id: " + user1Id + " and user2Id: " + user2Id );
        }
        return convertToDto(conversation);
    }


}
