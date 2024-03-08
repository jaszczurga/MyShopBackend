package com.ecommerce.myshop.entity.chat;


import com.ecommerce.myshop.entity.Authentication.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "conversations")
@Data
public class Conversation{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer conversationId;


    @ManyToOne
    @JoinColumn (name = "user1_id", referencedColumnName = "id")
    private User user1;


    @ManyToOne
    @JoinColumn (name = "user2_id", referencedColumnName = "id")
    private User user2;

    @OneToMany (mappedBy = "conversation",fetch = FetchType.LAZY)
    private List<Message> messages =new ArrayList<>();

    public void addMessage(Message message){
        messages.add(message);
        message.setConversation(this);
    }
}
