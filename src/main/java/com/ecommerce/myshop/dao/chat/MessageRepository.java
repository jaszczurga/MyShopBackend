package com.ecommerce.myshop.dao.chat;

import com.ecommerce.myshop.entity.chat.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Query("SELECT m FROM Message m WHERE m.conversation.conversationId = :conversationId")
    Page<Message> findByConversationId(Integer conversationId, Pageable pageable);
}
