package com.ecommerce.myshop.service.chat;

import com.ecommerce.myshop.Exceptions.NotFoundException;
import com.ecommerce.myshop.dao.Authentication.UserRepository;
import com.ecommerce.myshop.dao.chat.ConversationRepository;
import com.ecommerce.myshop.dataTranferObject.ConversationDto;
import com.ecommerce.myshop.entity.chat.Conversation;
import com.ecommerce.myshop.entity.chat.Message;
import com.ecommerce.myshop.ws.MessageDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ChatUtils chatUtils;

    @Override
    public void createConversation(Integer user1Id , Integer user2Id) {
        Conversation conversation = new Conversation();

        conversation.setUser1( userRepository.findById(user1Id).orElseThrow(
                () -> new NotFoundException("User not found with id: " + user1Id)
        ) );
        conversation.setUser2( userRepository.findById(user2Id).orElseThrow(
                () -> new NotFoundException("User not found with id: " + user2Id)
        ) );
        conversationRepository.save(conversation);
    }

    @Override
  public ConversationDto getConversation(Integer conversationId) {
    Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
    if (conversation == null) {
        throw new NotFoundException( "Conversation not found with id: " + conversationId );
    }
    return chatUtils.convertToDto(conversation);
}



    @Override
    public ConversationDto getConversationByUsersId(Integer user1Id , Integer user2Id) {
        Conversation conversation = conversationRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (conversation == null) {
            throw new NotFoundException( "Conversation not found with user1Id: " + user1Id + " and user2Id: " + user2Id );
        }
        return chatUtils.convertToDto(conversation);
    }

    @Override
public void addMessage(MessageDto messageDto) {
    Conversation conversation = conversationRepository.findByUser1IdAndUser2Id(
        Integer.parseInt( messageDto.getSenderId()),
        Integer.parseInt( messageDto.getReceiverId())
    );

    if (conversation == null) {
        throw new NotFoundException( "Conversation not found with user1Id: " + messageDto.getSenderId() + " and user2Id: " + messageDto.getReceiverId() );
    }
        Message message = new Message();
        message.setContent( messageDto.getContent() );
        message.setUser( userRepository.findById( Integer.parseInt( messageDto.getReceiverId() ) ).orElseThrow(
                () -> new NotFoundException("User not found with id: " + messageDto.getSenderId())
        ) );
        message.setUserSender( userRepository.findById( Integer.parseInt( messageDto.getSenderId() ) ).orElseThrow(
                () -> new NotFoundException("User not found with id: " + messageDto.getSenderId())
        ) );
        conversation.addMessage(message);

    conversationRepository.save(conversation);
}


}
