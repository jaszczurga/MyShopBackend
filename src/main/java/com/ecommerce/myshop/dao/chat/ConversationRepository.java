package com.ecommerce.myshop.dao.chat;

import com.ecommerce.myshop.entity.chat.Conversation;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer>{


@Query("SELECT c FROM Conversation c WHERE (c.user1.id = :user1Id and c.user2.id = :user2Id) or (c.user1.id = :user2Id and c.user2.id = :user1Id)")
Conversation findByUser1IdAndUser2Id(Integer user1Id , Integer user2Id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Conversation c WHERE c.user1.id = :userId OR c.user2.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

}
